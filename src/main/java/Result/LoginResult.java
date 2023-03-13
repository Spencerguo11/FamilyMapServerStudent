package Result;

import Model.Authtoken;

/**
 * A class to handle logging a user in result.
 */
public class LoginResult {
    /**
     * an unique string of a login result
     */
    private String authtoken; // an unique string of a login result
    /**
     * the username of the login result
     */
    private String username; // the username of the login result
    /**
     * the personID of the login result
     */
    private String personID; // the personID of the login result
    /**
     * a boolean variable to indicator whether successfully logged a user in
     */
    private boolean success; // a boolean variable to indicator whether successfully logged a user in


    private String message;
    // constructor

    /**
     * LoginResult constructor
     * @param authtokenInput Input of an unique string
     * @param usernameInput Input of username
     * @param personIDInput Input of person ID
     * @param success a boolean input
     */
    public LoginResult(String authtokenInput, String usernameInput,
                       String personIDInput, boolean success){
        authtoken = authtokenInput;
        username = usernameInput;
        personID = personIDInput;
        success = success;
    }

    public LoginResult() {};

    public LoginResult(Authtoken authtoken, String personIDInput) {
        this.authtoken = authtoken.getAuthtoken();
        this.username = authtoken.getUsername();
        this.personID = personIDInput;

    };
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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
