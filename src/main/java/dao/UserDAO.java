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
    private Connection conn; // connect to the database


    public UserDAO() {}

    public void setConnection(Connection conn){this.conn = conn;}


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
//    public void clear() throws DataAccessException {
//        ResultSet rs;
//
//        try {
//            Statement stmt = conn.createStatement();
//            String sql = "DELETE FROM User;";
//            stmt.executeUpdate(sql);
//            stmt.close();
////                stmt.executeUpdate("drop table if exists User");
////                stmt.executeUpdate("create table User (username VARCHAR(255) NOT NULL PRIMARY KEY, password VARCHAR(255) NOT NULL, email VARCHAR(255) NOT NULL, firstName VARCHAR(255) NOT NULL, " +
////                        "lastName VARCHAR(255) NOT NULL, gender VARCHAR(10) NOT NULL, personID VARCHAR(255) NOT NULL)");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//    }
    public void clear() throws DataAccessException{
        String sql = "DELETE FROM User;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the user table");
        }
    }

    public boolean validate(String username, String password) throws DataAccessException{
        String passwordOutput;
        ResultSet rs;
        String sql = "SELECT * FROM User WHERE username = '" + username + "' AND password = '" + password + "'";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            rs = stmt.executeQuery();
            if (!rs.next()) {
                throw new DataAccessException("error finding username/password");
            } else{
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting an event into the database");
        }

    }

    public void clearByUsername(String username) throws DataAccessException{
        // clear a user by a username;
        String sql = "DELETE FROM User WHERE username = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting an event into the database");
        }
    }




}
