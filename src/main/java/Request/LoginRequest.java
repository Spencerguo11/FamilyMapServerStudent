package Request;

/**
 * A class for creating a login request
 */
public class LoginRequest {
    /**
     * the username of a login request
     */
    private String username; // the username of a login request
    /**
     * the password of a login password
     */
    private String password; // the password of a login password

    // constructor

    /**
     * LoginRequest constructor
     * @param usernameInput Input of username
     * @param passwordInput Input of password
     */
    public LoginRequest(String usernameInput, String passwordInput){
        username =  usernameInput;
        password = passwordInput;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
