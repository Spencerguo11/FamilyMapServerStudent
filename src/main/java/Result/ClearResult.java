package Result;

/**
 * A class to handle clearing result.
 */
public class ClearResult {
    /**
     * the message of a clear result
     */
    private String message; // the message of a clear result
    /**
     * a boolean variable to indicator whether successfully cleared
     */
    private boolean success; // a boolean variable to indicator whether successfully cleared

    // constructor

    /**
     * ClearResult constructor
     * @param messageInput Input of message
     * @param successInput a boolean input
     */

    public ClearResult(String messageInput, boolean successInput){
        message = messageInput;
        success = successInput;
    }

    public ClearResult(){}


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
