package sl314.excepciones;


public class SoccerException extends Exception{

    public SoccerException() {
    }

    public SoccerException(String message) {
        super(message);
    }

    public SoccerException(String message, Throwable cause) {
        super(message, cause);
    }

    public SoccerException(Throwable cause) {
        super(cause);
    }

    public SoccerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
    
    
}
