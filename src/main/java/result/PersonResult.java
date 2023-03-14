package result;

import model.Person;

import java.util.Arrays;
import java.util.Objects;

public class PersonResult {

    private Person[] data;
    private Boolean success;
    private String message;

    public PersonResult(){

    }

    public Person[] getData() {
        return data;
    }

    public void setData(Person[] data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {this.message = message;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonResult that = (PersonResult) o;
        return Arrays.equals(data, that.data) && Objects.equals(success, that.success) && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(success, message);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }
}
