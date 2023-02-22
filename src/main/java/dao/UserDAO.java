package dao;
import Model.User;
import java.sql.*;

/**
 * A class to handle users from the database
 */
public class UserDAO {
    /**
     * connect to the database
     */
    private final Connection conn; // connect to the database

    /**
     * UserDAO constructor
     * @param conn connect to the database
     */
    public UserDAO(Connection conn) {
        this.conn = conn;
    }


    /**
     * Create a new user
     * @param user pass in a user object
     */
    public void createUser(User user) {
        // pass in an user object and add the information into database
    }

    /**
     * Check to see if the username and password are valid
     * @param username pass in the username
     * @param password pass in the password
     * @return a boolean result
     */
    public boolean validate(String username, String password){
        // check to see if the username and password are correct
        return true;}


    // find a User by using the userId

    /**
     * Find a user by the user ID
     * @param userId pass in the user ID
     * @return a User object
     */
    User getUserById(String userId){return null;}


    /**
     * Clear all users
     */
    public void clear(){
        // clear all users;
    }


    /**
     * Clear a user by the user ID
     * @param userId pass in a user ID
     */
    public void clearByUserID(String userId){
        // clear a user by user ID
    }


}
