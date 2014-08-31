package shiver.me.timbers.file.server.spring;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import shiver.me.timbers.file.io.Directory;

import java.io.IOException;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static shiver.me.timbers.file.server.spring.GlobalControllerAdvice.buildError;

/**
 * Controller for mapping the file and directory requests.
 *
 * @author Karl Bennett
 */
@RestController
@RequestMapping("/directory")
public class DirectoryController {

    @RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE)
    public Directory directory(Directory directory) throws IOException {

        return directory;
    }

    @ExceptionHandler
    @ResponseStatus(BAD_REQUEST)
    public Map<String, String> noDirectoryProvided(NoDirectoryException e) {

        return buildError(e);
    }
}
