package dao;
import Model.Authtoken;
import java.sql.*;

/**
 * A class to handle authtoken from the database
 */
public class AuthtokenDAO {
    /**
     * connect to the database
     */
    private final Connection conn; // connect to the database

    /**
     * Authtoken constructor
     * @param conn connect to the database
     */
    public AuthtokenDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Create an authtoken for a username
     */
    public void createAuthtoken(String username) {
        // create an authtoken by the username
    }

    /**
     * Get the authtoken by the username
     * @param username pass in the username
     * @return an authtoken
     */
    public Authtoken getAuthtoken(String username){
        // return an authtoken
        return null;}


    /**
     * Clear all authtokens
     */
    public void clear(){
        // clear all Authtokens
    }

    /**
     * Clear an authtoken by a username
     * @param username pass in a username
     */
    public void clearByUsername(String username){
        // clear an authtoken by a username
    }

}
