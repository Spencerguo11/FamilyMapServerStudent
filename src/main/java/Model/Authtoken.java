package Model;

import java.util.Objects;

/**
 * A class for authtoken
 */
public class Authtoken {

    /**
     * an authtoken that is associated with the username
     */
    String authtoken; // an authtoken that is associated with the username

    /**
     * the username
     */
    String username; // the username

    // constructor

    /**
     * Authtoken constructor
     * @param authtokenInput Input of the authtoken
     * @param usernameInput Input of the username
     */
    public Authtoken(String authtokenInput, String usernameInput){
        authtoken = authtokenInput;
        username = usernameInput;
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


    /**
     * Equals function
     * @param o An object input
     * @return A boolean result
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Authtoken authtoken = (Authtoken) o;
        return Objects.equals(authtoken, authtoken.authtoken) && Objects.equals(username, authtoken.username);
    }

}
