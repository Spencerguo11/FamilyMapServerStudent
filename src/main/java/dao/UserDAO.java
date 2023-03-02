package dao;
import Model.Event;
import Model.User;

import javax.xml.crypto.Data;
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


    public void insert(User user) throws DataAccessException {
        String sql = "INSERT INTO User (Username, Password, Email, FirstName, LastName, " +
                "Gender, PersonID) VALUES(?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setString(6, user.getGender());
            stmt.setString(7, user.getPersonID());


            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting a user into the database");
        }
    }

    public User find(String username) throws DataAccessException{
        User user;
        ResultSet rs;
        String sql = "SELECT * FROM User WHERE username = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User(rs.getString("Username"), rs.getString("Password"),
                        rs.getString("Email"), rs.getString("FirstName"), rs.getString("LastName"),
                        rs.getString("Gender"), rs.getString("PersonID"));
                return user;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding a user in the database");
        }
    }

    /**
     * Clear all users
     */
    public void clear() throws DataAccessException{
        String sql = "DELETE FROM User";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the user table");
        }
    }



//    /**
//     * Create a new user
//     * @param user pass in a user object
//     */
//    public void createUser(User user) {
//        // pass in an user object and add the information into database
//    }
//
//    /**
//     * Check to see if the username and password are valid
//     * @param username pass in the username
//     * @param password pass in the password
//     * @return a boolean result
//     */
//    public boolean validate(String username, String password){
//        // check to see if the username and password are correct
//        return true;}
//
//
//    // find a User by using the userId
//
//    /**
//     * Find a user by the user ID
//     * @param userId pass in the user ID
//     * @return a User object
//     */
//    User getUserById(String userId){return null;}
//
//
//
//    /**
//     * Clear a user by the user ID
//     * @param userId pass in a user ID
//     */
//    public void clearByUserID(String userId){
//        // clear a user by user ID
//    }




}
