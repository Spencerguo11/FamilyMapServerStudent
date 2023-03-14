package result;

import model.AuthToken;

import java.util.Objects;

public class RegisterResult {

    private String authtoken;
    private String username;
    private String personID;
    private Boolean success;
    private String message;

    public RegisterResult(){

    }

    public RegisterResult(AuthToken auth, String personID) {
        this.authtoken = auth.getAuthtoken();
        this.username = auth.getUsername();
        this.personID = personID;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {return message;}

    public void setMessage(String message) { this.message = message;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegisterResult that = (RegisterResult) o;
        return authtoken.equals(that.authtoken) && username.equals(that.username) && personID.equals(that.personID) && Objects.equals(success, that.success) && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authtoken, username, personID, success, message);
    }
}
