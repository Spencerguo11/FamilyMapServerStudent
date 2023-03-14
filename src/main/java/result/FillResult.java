package result;

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
