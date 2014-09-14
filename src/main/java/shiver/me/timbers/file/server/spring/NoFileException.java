package shiver.me.timbers.file.server.spring;

/**
 * This exception is thrown when the {@link Requests#FILE} attribute isn't added to the
 * {@link javax.servlet.http.HttpServletRequest} that is sent into the {@link FileController#file method.
 *
 * @author Karl Bennett
 */
public class NoFileException extends RuntimeException {

    public NoFileException() {
        super("No file provided.");
    }
}