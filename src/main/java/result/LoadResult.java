package result;

import java.util.Objects;

public class LoadResult {

    private String message;
    private Boolean success;
    private int numUsers;
    private int numPersons;
    private int numEvents;


    public LoadResult(){

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean isSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public int getNumUsers() {
        return numUsers;
    }

    public void setNumUsers(int numUsers) {
        this.numUsers = numUsers;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoadResult that = (LoadResult) o;
        return numUsers == that.numUsers && numPersons == that.numPersons && numEvents == that.numEvents && Objects.equals(message, that.message) && Objects.equals(success, that.success);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, success, numUsers, numPersons, numEvents);
    }
}
