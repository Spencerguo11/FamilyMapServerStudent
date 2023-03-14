package Result;

import Model.Authtoken;

/**
 * A class to handle registering result.
 */
public class RegisterResult {
    private String authtoken; // an unique string of a register result
    private String username; // the username of the register result
    private String personID; // the personID of the register result

    private String message;

    private boolean success; // a boolean variable to indicator whether successfully registered

    // constructor

    /**
     * RegisterResult constructor
     * @param authtokenInput Input an unique string
     * @param usernameInput Input of username
     * @param personIDInput Input of personID
     * @param successInput a boolean input
     */
    public RegisterResult(String authtokenInput, String usernameInput,
                          String personIDInput, boolean successInput){
        authtoken = authtokenInput;
        username = usernameInput;
        personID = personIDInput;
        success = successInput;
    }

    public RegisterResult(){}

    public RegisterResult(Authtoken authtokenObj) {
        this.authtoken = authtokenObj.getAuthtoken();
        this.username = authtokenObj.getUsername();
//        this.personID = authtokenObj.getPersonID();
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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {return message;}

    public void setMessage(String message) {this.message = message;}

}
