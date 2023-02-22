package Result;

/**
 * A class to handle filling in information result.
 */
public class FillResult {
    /**
     * the message of fill result
     */
    private String message; // the message of fill result
    /**
     * a boolean variable to indicator whether successfully filled
     */
    private boolean success; // a boolean variable to indicator whether successfully filled

    // Constructor

    /**
     * FillResult constructor
     * @param messageInput Input of message
     * @param successInput a boolean input
     */
    public FillResult(String messageInput, boolean successInput){
        message = messageInput;
        success = successInput;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
