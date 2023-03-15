package result;

import model.Event;

import java.util.Arrays;
import java.util.Objects;

public class EventResult {

    private Event[] data;
    private Boolean success;
    private String message;

    public EventResult(){

    }

    public Event[] getData() {
        return data;
    }

    public void setData(Event[] data) {
        this.data = data;
    }

    public Boolean isSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public void setMessage(String message) { this.message = message;}

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventResult that = (EventResult) o;
        return Arrays.equals(data, that.data) && Objects.equals(success, that.success) && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(success, message);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }
}
