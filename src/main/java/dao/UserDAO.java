//package dao;
//import Model.Event;
//import Model.User;
//
//import javax.xml.crypto.Data;
//import java.sql.*;
//
///**
// * A class to handle users from the database
// */
//public class UserDAO {
//    /**
//     * connect to the database
//     */
//    private Connection conn; // connect to the database
//
//
//    public UserDAO() {}
//
//    public void setConnection(Connection conn){this.conn = conn;}
//
//
//    public void insert(User user) throws DataAccessException {
//        String sql = "INSERT INTO User (Username, Password, Email, FirstName, LastName, " +
//                "Gender, PersonID) VALUES(?,?,?,?,?,?,?)";
//        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//            //Using the statements built-in set(type) functions we can pick the question mark we want
//            //to fill in and give it a proper value. The first argument corresponds to the first
//            //question mark found in our sql String
//            stmt.setString(1, user.getUsername());
//            stmt.setString(2, user.getPassword());
//            stmt.setString(3, user.getEmail());
//            stmt.setString(4, user.getFirstName());
//            stmt.setString(5, user.getLastName());
//            stmt.setString(6, user.getGender());
//            stmt.setString(7, user.getPersonID());
//
//
//            if (stmt.executeUpdate() != 1) {
//                throw new DataAccessException("error inserting user");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw new DataAccessException("Error encountered while inserting a user into the database");
//        }
//    }
//
//    public User find(String username) throws DataAccessException{
//        User user;
//        ResultSet rs;
//        String sql = "SELECT * FROM User WHERE username = ?;";
//        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setString(1, username);
//            rs = stmt.executeQuery();
//            if (rs.next()) {
//                user = new User(rs.getString("Username"), rs.getString("Password"),
//                        rs.getString("Email"), rs.getString("FirstName"), rs.getString("LastName"),
//                        rs.getString("Gender"), rs.getString("PersonID"));
//                return user;
//            } else {
//                return null;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw new DataAccessException("Error encountered while finding a user in the database");
//        }
//    }
//
//    /**
//     * Clear all users
//     */
////    public void clear() throws DataAccessException {
////        ResultSet rs;
////
////        try {
////            Statement stmt = conn.createStatement();
////            String sql = "DELETE FROM User;";
////            stmt.executeUpdate(sql);
////            stmt.close();
//////                stmt.executeUpdate("drop table if exists User");
//////                stmt.executeUpdate("create table User (username VARCHAR(255) NOT NULL PRIMARY KEY, password VARCHAR(255) NOT NULL, email VARCHAR(255) NOT NULL, firstName VARCHAR(255) NOT NULL, " +
//////                        "lastName VARCHAR(255) NOT NULL, gender VARCHAR(10) NOT NULL, personID VARCHAR(255) NOT NULL)");
////        } catch (SQLException e) {
////            e.printStackTrace();
////        }
////
////    }
//    public void clear() throws DataAccessException{
//        String sql = "DELETE FROM User;";
//        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw new DataAccessException("Error encountered while clearing the user table");
//        }
//    }
//
//    public boolean validate(String username, String password) throws DataAccessException{
//        String passwordOutput;
//        ResultSet rs;
//        String sql = "SELECT * FROM User WHERE username = '" + username + "' AND password = '" + password + "'";
//
//        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//            rs = stmt.executeQuery();
//            if (!rs.next()) {
//                throw new DataAccessException("error finding username/password");
//            } else{
//                return true;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw new DataAccessException("Error encountered while inserting an event into the database");
//        }
//
//    }
//
//    public void clearByUsername(String username) throws DataAccessException{
//        // clear a user by a username;
//        String sql = "DELETE FROM User WHERE username = ?;";
//        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setString(1, username);
//            stmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw new DataAccessException("Error encountered while inserting an event into the database");
//        }
//    }
//
//
//
//
//}


package dao;


import Model.User;

import java.sql.*;
import java.util.UUID;

public class UserDAO {

    private Connection conn;

    public UserDAO() {}

    public void setConnection(Connection c) throws DataAccessException{
        conn = c;
    }
    /**
     * insert a new user onto user table
     * @param user
     * @throws DataAccessException
     */
    public void insert(User user) throws DataAccessException {

        try {
            PreparedStatement stmt = null;
            try {
                String sql = "insert into User (username, password, email, firstName, lastName, gender, personId) values (?,?,?,?,?,?,?)";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, user.getUsername());
                stmt.setString(2, user.getPassword());
                stmt.setString(3, user.getEmail());
                stmt.setString(4, user.getFirstName());
                stmt.setString(5, user.getLastName());
                stmt.setString(6, user.getGender());
                stmt.setString(7, user.getPersonID());

                if (stmt.executeUpdate() != 1) {
                    throw new DataAccessException("error inserting user");
                }
            } finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("error inserting user");
        }

    }

    /**
     * find user in table using userID
     * @param username
     * @return
     * @throws DataAccessException
     */
    public boolean find(String username) throws DataAccessException {
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "select * from User WHERE username = '" + username + "'";
                stmt = conn.prepareStatement(sql);

                rs = stmt.executeQuery();

                if (!rs.next() ) {
                    throw new DataAccessException("error finding username");
                } else {
                    return true;
                }
            }
            finally {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
        catch (SQLException e) {
            throw new DataAccessException("error finding username");
        }

    }

    /**
     * clears user table
     * @throws DataAccessException
     */
    public void clear() throws DataAccessException {
        try {
            Statement stmt = null;
            try {
                stmt = conn.createStatement();

                stmt.executeUpdate("drop table if exists User");
                stmt.executeUpdate("create table User (username VARCHAR(50) NOT NULL PRIMARY KEY, password VARCHAR(50) NOT NULL, email VARCHAR(50) NOT NULL, firstName VARCHAR(50) NOT NULL, " +
                        "lastName VARCHAR(50) NOT NULL, gender CHAR(1) NOT NULL, personId VARCHAR(50) NOT NULL, CONSTRAINT user_info UNIQUE (username))");
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
            }
        }
        catch (SQLException e) {
            throw new DataAccessException("error resetting table");
        }
    }

    public boolean ExistingUsernamePassword(User user) throws DataAccessException {
        try{
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String username = user.getUsername();
                String sql = "select * from User WHERE username = '" + user.getUsername() + "' AND password = '" + user.getPassword() + "'";
                stmt = conn.prepareStatement(sql);
                rs =  stmt.executeQuery();

                if(!rs.next()) {
                    throw new DataAccessException("error finding username/password");
                } else {
                    return true;
                }
            }
            finally {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("error finding username/password");
        }
    }

    public User getUser(String username) throws DataAccessException {
        User user = new User();
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "select * from User WHERE username = '" + username +"'";
                stmt = conn.prepareStatement(sql);

                rs = stmt.executeQuery();
                while (rs.next()) {
                    user.setUsername(rs.getString(1));
                    user.setPassword(rs.getString(2));
                    user.setEmail(rs.getString(3));
                    user.setFirstName(rs.getString(4));
                    user.setLastName(rs.getString(5));
                    user.setGender(rs.getString(6));
                    user.setPersonID(UUID.randomUUID().toString()); //
                }
            }
            finally {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
        catch (SQLException e) {
            throw new DataAccessException("error getting username");
        }
        return user;
    }

    public String getPersonIDOfUser(User user) throws DataAccessException {
        String personID = new String();
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "select * from User WHERE username = '" + user.getUsername() + "' AND password = '" + user.getPassword() + "'";
                stmt = conn.prepareStatement(sql);

                rs = stmt.executeQuery();
                while (rs.next()) {
                    personID = rs.getString(7);
                }

            }
            finally {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
        catch (SQLException e) {
            throw new DataAccessException("error getting personID of user");
        }

        return personID;
    }

    public String tableToString() throws DataAccessException{
        StringBuilder out = new StringBuilder();
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "select * from users";
                stmt = conn.prepareStatement(sql);

                rs = stmt.executeQuery();
                while (rs.next()) {
                    String word = rs.getString(1);
                    String password = rs.getString(2);
                    String email = rs.getString(3);
                    String firstName = rs.getString(4);
                    String lastName = rs.getString(5);
                    String gender = rs.getString(6);
                    String personID = rs.getString(7);
                    out.append((word + "\t" + password + "\t" + email + "\t" + firstName + "\t" + lastName + "\t" + gender + "\t" + personID + "\n"));
                }
            }
            finally {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
        catch (SQLException e) {
            throw new DataAccessException("error toString table");
        }
        return out.toString();
    }

    public void deleteUser(User user) throws DataAccessException {
        try {
            Statement stmt = null;
            try {
                stmt = conn.createStatement();

                stmt.executeUpdate("DELETE FROM User WHERE username = '" + user.getUsername() + "'");

            }
            finally {
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
            }
        }
        catch (SQLException e) {
            throw new DataAccessException("error deleting user");
        }
    }

}