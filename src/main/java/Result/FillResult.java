//package Result;
//
///**
// * A class to handle filling in information result.
// */
//public class FillResult {
//    /**
//     * the message of fill result
//     */
//    private String message; // the message of fill result
//    /**
//     * a boolean variable to indicator whether successfully filled
//     */
//    private boolean success; // a boolean variable to indicator whether successfully filled
//
//    private int numPersons;
//    private int numEvents;
//    private int numGenerations;
//
//    public int getNumPersons() {
//        return numPersons;
//    }
//
//    public void setNumPersons(int numPersons) {
//        this.numPersons = numPersons;
//    }
//
//    public int getNumEvents() {
//        return numEvents;
//    }
//
//    public void setNumEvents(int numEvents) {
//        this.numEvents = numEvents;
//    }
//
//    public int getNumGenerations() {
//        return numGenerations;
//    }
//
//    public void setNumGenerations(int numGenerations) {
//        this.numGenerations = numGenerations;
//    }
//// Constructor
//
//    /**
//     * FillResult constructor
//     * @param messageInput Input of message
//     * @param successInput a boolean input
//     */
//    public FillResult(String messageInput, boolean successInput){
//        message = messageInput;
//        success = successInput;
//    }
//
//
//
//    public FillResult(){}
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public boolean isSuccess() {
//        return success;
//    }
//
//    public void setSuccess(boolean success) {
//        this.success = success;
//    }
//}


package Result;

import java.util.Objects;

public class FillResult {

    private String message;
    private Boolean success;
    private String username;
    private int generations;
    private int events;
    private int persons;


    public FillResult(){

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getUsername() {return username;}

    public void setUsername(String username) {this.username = username;}

    public int getGenerations() {return generations;}

    public void setGenerations(int generations) {this.generations = generations;}

    public int getEvents() {
        return events;
    }

    public void setEvents(int events) {
        this.events = events;
    }

    public int getPersons() {
        return persons;
    }

    public void setPersons(int persons) {
        this.persons = persons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FillResult that = (FillResult) o;
        return generations == that.generations && events == that.events && persons == that.persons && Objects.equals(message, that.message) && Objects.equals(success, that.success) && username.equals(that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, success, username, generations, events, persons);
    }
}