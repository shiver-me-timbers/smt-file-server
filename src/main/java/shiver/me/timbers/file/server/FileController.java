package shiver.me.timbers.file.server;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import shiver.me.timbers.file.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyEditorSupport;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.Map;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.PARTIAL_CONTENT;
import static org.springframework.http.HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.HEAD;
import static shiver.me.timbers.file.server.GlobalControllerAdvice.buildError;
import static shiver.me.timbers.file.server.Requests.FILE;

/**
 * Controller for mapping the file and directory requests.
 *
 * @author Karl Bennett
 */
@RestController
@RequestMapping("/file")
public class FileController {

    private static final int DEFAULT_BUFFER_SIZE = 10240;

    private static final String RANGE = "Range";
    private static final String CONTENT_RANGE = "Content-Range";

    @InitBinder
    public void initBinder(final HttpServletRequest request, WebDataBinder binder) throws IOException {

        binder.registerCustomEditor(Ranges.class, new PropertyEditorSupport() {

            @Override
            public void setAsText(String text) throws IllegalArgumentException {

                final File file = getFileFrom(request);

                setValue(new Ranges(text, file.getSize()));
            }
        });
    }

    @ModelAttribute
    public File file(HttpServletRequest request) {

        return getFileFrom(request);
    }

    private static File getFileFrom(HttpServletRequest request) {

        final Object file = request.getAttribute(FILE);

        if (null == file) {
            throw new NoFileException();
        }
        return (File) file;
    }

    @RequestMapping(method = {GET, HEAD})
    public File file(File file) throws IOException {

        return file;
    }

    @RequestMapping(method = {GET, HEAD}, headers = RANGE)
    @ResponseStatus(PARTIAL_CONTENT)
    public File file(@RequestHeader(value = RANGE) Ranges ranges, File file, HttpServletResponse response)
            throws IOException {

        return file;
    }

    @ExceptionHandler
    @ResponseStatus(BAD_REQUEST)
    public Map<String, String> noFileProvided(NoFileException e) {

        return buildError(e);
    }

    /**
     * This exception is thrown when the {@link Requests#FILE} attribute isn't added to the
     * {@link javax.servlet.http.HttpServletRequest} that is sent into the {@link #getFileFrom} method.
     *
     * @author Karl Bennett
     */
    private static class NoFileException extends RuntimeException {

        public NoFileException() {
            super("No file provided.");
        }
    }

    private static void addContentRange(Ranges ranges, java.io.File file, HttpHeaders headers) {

        headers.set(CONTENT_RANGE, format("bytes %s/%d", ranges.get(0), file.length()));
    }

    private static void addContentRange(Ranges ranges, java.io.File file, HttpServletResponse response) {

        response.setHeader(CONTENT_RANGE, format("bytes %s/%d", ranges.get(0), file.length()));
    }

    private void copy(long start, long end, java.io.File file, HttpServletResponse response) throws IOException {

        final long length = end - start + 1;

        if (file.length() <= length) {

            copyToResponse(file, response);

            return;
        }

        try (
                final RandomAccessFile input = new RandomAccessFile(file, "r");
                final OutputStream output = response.getOutputStream()
        ) {

            input.seek(start);

            final byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int read;

            long toRead = length;

            while ((read = input.read(buffer)) > 0 && (toRead -= read) > 0) {
                output.write(buffer, 0, read);
            }

            output.write(buffer, 0, (int) toRead + read);
        }
    }

    private static void copyToResponse(java.io.File file, HttpServletResponse response) throws IOException {
        try (OutputStream out = response.getOutputStream()) {

            IOUtils.copy(new FileInputStream(file), out);
        }
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> requestedRangeNotSatisfiable(RequestedRangeNotSatisfiableException e) {

        final HttpHeaders headers = new HttpHeaders();

        headers.set(CONTENT_RANGE, format("bytes */%d", e.getFileSize()));

        return new ResponseEntity<>(buildError(e), headers, REQUESTED_RANGE_NOT_SATISFIABLE);
    }
}
