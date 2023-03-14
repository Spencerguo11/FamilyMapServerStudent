//package dao;
//import Model.Authtoken;
//
//import javax.xml.crypto.Data;
//import java.sql.*;
//
///**
// * A class to handle authtoken from the database
// */
//public class AuthtokenDAO {
//    /**
//     * connect to the database
//     */
//    private Connection conn; // connect to the database
//
//    /**
//     * Authtoken constructor
//     */
//    public AuthtokenDAO() {
//    }
//
//    public void setConnection(Connection conn) {this.conn = conn;}
//
//    public void insert(Authtoken authtoken) throws DataAccessException {
//        String sql = "INSERT INTO Authtoken (authtoken, username) VALUES(?,?)";
//        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//            //Using the statements built-in set(type) functions we can pick the question mark we want
//            //to fill in and give it a proper value. The first argument corresponds to the first
//            //question mark found in our sql String
//            stmt.setString(1, authtoken.getAuthtoken());
//            stmt.setString(2, authtoken.getUsername());
////            stmt.setString(3, authtoken.getPersonID());
//
//            if (stmt.executeUpdate() != 1) {
//                throw new DataAccessException("error inserting");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw new DataAccessException("Error encountered while inserting a authTokenr into the database");
//        }
//    }
//
//    public String findUsernameByAuthToken(String authtoken) throws DataAccessException{
//        String username;
//        ResultSet rs;
//        String sql = "SELECT * FROM Authtoken WHERE authtoken = ?;";
//        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setString(1, authtoken);
//            rs = stmt.executeQuery();
//            if (rs.next()) {
//                username = rs.getString("Username");
//                return username;
//            } else {
//                return null;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw new DataAccessException("Error encountered while finding an username in the database");
//        }
//    }
//
//    /**
//     * Clear all users
//     */
////    public void clear() throws DataAccessException { //Also clears tables
////        try {
////            Statement stmt = null;
////            try {
////                stmt = conn.createStatement();
////
////                stmt.executeUpdate("drop table if exists Authtoken");
////                stmt.executeUpdate("create table Authtoken (authtoken VARCHAR(255) NOT NULL PRIMARY KEY,\n" +
////                        "\tuserName VARCHAR(255) NOT NULL,\n" +
////                        "\tpersonID VARCHAR(255) NOT NULL,\n" +
////                        "        CONSTRAINT auth_info UNIQUE (authtoken))");
////            }
////            finally {
////                if (stmt != null) {
////                    stmt.close();
////                    stmt = null;
////                }
////            }
////        }
////        catch (SQLException e) {
////            throw new DataAccessException("error resetting Authtoken");
////        }
////    }
//
//    public void clear() throws DataAccessException{
//        String sql = "DELETE FROM Authtoken;";
//        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw new DataAccessException("Error encountered while clearing the user table");
//        }
//    }
//
//    public void clearByUsername(String username) throws DataAccessException{
//        // clear events by a username;
//        String sql = "DELETE FROM Authtoken WHERE Username = ?;";
//        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setString(1, username);
//            stmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw new DataAccessException("Error encountered while inserting an authtoken into the database");
//        }
//    }
//
//    public boolean validate(String authtoken) throws DataAccessException {
//        try {
//            PreparedStatement stmt = null;
//            ResultSet rs = null;
//            try {
//                String sql = "select * from Authtoken WHERE authtoken = '" + authtoken + "'";
//                stmt = conn.prepareStatement(sql);
//                rs = stmt.executeQuery();
//
//                if(!rs.next()) {
//                    return false;
//                } else {
//                    return true;
//                }
//            }
//            finally {
//                if(rs != null) {
//                    rs.close();
//                }
//                if(stmt != null) {
//                    stmt.close();
//                }
//            }
//        } catch (SQLException e) {
//            throw new DataAccessException("error finding Authtoken");
//        }
//    }
//
//    public Authtoken getAuthToken(String authtoken) throws DataAccessException {
//        Authtoken auth = new Authtoken();
//        try {
//            PreparedStatement stmt = null;
//            ResultSet rs = null;
//            try {
//                String sql = "select * from Authtoken WHERE authtoken = '" + authtoken +"'";
//                stmt = conn.prepareStatement(sql);
//                rs = stmt.executeQuery();
//                while (rs.next()) {
//                    auth.setAuthtoken(rs.getString(1));
//                    auth.setUsername(rs.getString(2));
//                }
//            }
//            finally {
//                if (rs != null){
//                    rs.close();
//                }
//                if(stmt != null){
//                    stmt.close();
//                }
//            }
//        }
//        catch (SQLException e) {
//            throw new DataAccessException("error getting AuthToken");
//        }
//        return auth;
//    }
//
//
//
////    /**
////     * Create an authtoken for a username
////     */
////    public void createAuthtoken(String username) {
////        // create an authtoken by the username
////        Authtoken = UUID
////    }
////
////    /**
////     * Get the authtoken by the username
////     * @param username pass in the username
////     * @return an authtoken
////     */
////    public Authtoken getAuthtoken(String username){
////        // return an authtoken
////        return null;}
////
////
////    /**
////     * Clear all authtokens
////     */
////    public void clear(){
////        // clear all Authtokens
////    }
////
////    /**
////     * Clear an authtoken by a username
////     * @param username pass in a username
////     */
////    public void clearByUsername(String username){
////        // clear an authtoken by a username
////    }
//
//
////    public String tableToString() throws DataAccessException{
////        StringBuilder out = new StringBuilder();
////        try {
////            PreparedStatement stmt = null;
////            ResultSet rs = null;
////            try {
////                String sql = "select * from authTable";
////                stmt = conn.prepareStatement(sql);
////
////                rs = stmt.executeQuery();
////                while (rs.next()) {
////                    String authtoken = rs.getString(1);
////                    String username = rs.getString(2);
////                    String personID = rs.getString(3);
////
////                    out.append((authtoken + "\t" + username + "\t" + personID + "\n"));
////                }
////            }
////            finally {
////                if (rs != null) {
////                    rs.close();
////                }
////                if (stmt != null) {
////                    stmt.close();
////                }
////            }
////        }
////        catch (SQLException e) {
////            throw new DataAccessException("error toString table");
////        }
////        return out.toString();
////    }
//}


package dao;


import Model.Authtoken;

import java.sql.*;

public class AuthtokenDAO {

    private Connection conn;

    public AuthtokenDAO() {}

    public void setConnection(Connection c) throws DataAccessException{
        conn = c;
    }

    /**
     * insert a new authtoken onto authtoken table
     * @param authtoken
     * @throws DataAccessException
     */
    public void insert(Authtoken authtoken) throws DataAccessException {
        //We can structure our string to be similar to a sql command, but if we insert question
        //marks we can change them later with help from the statement
        try{
            PreparedStatement stmt = null;
            try {
                String sql = "insert into Authtoken (authtoken, userName) values (?,?)";
                stmt = conn.prepareStatement(sql);

                stmt.setString(1, authtoken.getAuthtoken());
                stmt.setString(2, authtoken.getUsername());

                if (stmt.executeUpdate() != 1) {
                    throw new DataAccessException("error inserting");
                }
            }
            finally {
                if(stmt != null) {
                    stmt.close();
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("error encountered while inserting into the database");
        }
    }

    /**
     * find specific authtoken using its ID
     * @param authtoken
     * @return
     * @throws DataAccessException
     */
    public boolean validAuthToken(String authtoken) throws DataAccessException {
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "select * from Authtoken WHERE authtoken = '" + authtoken + "'";
                stmt = conn.prepareStatement(sql);
                rs = stmt.executeQuery();

                if(!rs.next()) {
                    throw new DataAccessException("error finding Authtoken");
                } else {
                    return true;
                }
            }
            finally {
                if(rs != null) {
                    rs.close();
                }
                if(stmt != null) {
                    stmt.close();
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("error finding Authtoken");
        }
    }


    /**
     * delete all data from table
     * @throws DataAccessException
     */
    public void clear() throws DataAccessException { //Also clears tables
        try {
            Statement stmt = null;
            try {
                stmt = conn.createStatement();

                stmt.executeUpdate("drop table if exists Authtoken");
                stmt.executeUpdate("create table Authtoken (authtoken VARCHAR(50) NOT NULL PRIMARY KEY,\n" +
                        "\tusername VARCHAR(50) NOT NULL,\n" +
                        "        CONSTRAINT auth_info UNIQUE (authtoken))");
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
            }
        }
        catch (SQLException e) {
            throw new DataAccessException("error resetting Authtoken");
        }
    }

    public Authtoken getAuthToken(String authtoken) throws DataAccessException {
        Authtoken auth = new Authtoken();
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "select * from Authtoken WHERE authtoken = '" + authtoken +"'";
                stmt = conn.prepareStatement(sql);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    auth.setAuthtoken(rs.getString(1));
                    auth.setUsername(rs.getString(2));
                }
            }
            finally {
                if (rs != null){
                    rs.close();
                }
                if(stmt != null){
                    stmt.close();
                }
            }
        }
        catch (SQLException e) {
            throw new DataAccessException("error getting AuthToken");
        }
        return auth;
    }

    public String tableToString() throws DataAccessException{
        StringBuilder out = new StringBuilder();
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "select * from Authtoken";
                stmt = conn.prepareStatement(sql);

                rs = stmt.executeQuery();
                while (rs.next()) {
                    String authtoken = rs.getString(1);
                    String username = rs.getString(2);

                    out.append((authtoken + "\t" + username + "\n"));
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

}