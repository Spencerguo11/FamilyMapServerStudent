package Result;

/**
 * A class to handle loading in information result.
 */
public class LoadResult {
    /**
     * the message of load result
     */
    private String message; // the message of load result
    /**
     * a boolean variable to indicator whether successfully loaded
     */
    private boolean success; // a boolean variable to indicator whether successfully loaded

    // constructor

    /**
     * LoadResult constructor
     * @param messageInput Input of message
     * @param successInput a boolean input
     */
    public LoadResult(String messageInput, boolean successInput){
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
