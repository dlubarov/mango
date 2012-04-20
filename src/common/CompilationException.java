package common;

public class CompilationException extends RuntimeException {
    private static final long serialVersionUID = -6763817349425673518L;

    public CompilationException(String format, Object... args) {
        super(String.format(format, args));
    }
}
