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

    private int numPersons;
    private int numEvents;
    private int numGenerations;

    public int getNumPersons() {
        return numPersons;
    }

    public void setNumPersons(int numPersons) {
        this.numPersons = numPersons;
    }

    public int getNumEvents() {
        return numEvents;
    }

    public void setNumEvents(int numEvents) {
        this.numEvents = numEvents;
    }

    public int getNumGenerations() {
        return numGenerations;
    }

    public void setNumGenerations(int numGenerations) {
        this.numGenerations = numGenerations;
    }
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



    public FillResult(){}

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
