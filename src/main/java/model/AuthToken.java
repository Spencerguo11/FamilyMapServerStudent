package model;

import java.util.Objects;
import java.util.UUID;

public class AuthToken {

    private String authtoken;
    private String username;

    public AuthToken(){
        authtoken = new String();
        username = new String();
    }

    public AuthToken(User user) {
        authtoken = UUID.randomUUID().toString();
        username = user.getUsername();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthToken authToken = (AuthToken) o;
        return authtoken.equals(authToken.authtoken) && username.equals(authToken.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authtoken, username);
    }
}
