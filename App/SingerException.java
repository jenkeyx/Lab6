package App;

public class SingerException extends Exception {
    public SingerException() {
    }
    public SingerException(String message) {
        super(message);
    }

    public SingerException(String message, Throwable cause) {
        super(message, cause);
    }

    public SingerException(Throwable cause) {
        super(cause);
    }

    public SingerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
