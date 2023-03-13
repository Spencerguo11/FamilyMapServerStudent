package Model;

import java.util.Objects;
import java.util.UUID;

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

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    String personID;

    public Authtoken(){
        authtoken = new String();
        username = new String();
        personID = new String();
    }

    public Authtoken(User user){
        authtoken = UUID.randomUUID().toString();
        username = user.getUsername();
        personID = user.getPersonID();
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
