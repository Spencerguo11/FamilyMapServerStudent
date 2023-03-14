package dataaccess;

import model.AuthToken;

import java.sql.*;

public class AuthTokenDao {

    private Connection conn;

    public AuthTokenDao() {}

    public void setConnection(Connection c) throws Database.DatabaseException{
        conn = c;
    }

    /**
     * insert a new authtoken onto authtoken table
     * @param authtoken
     * @throws DataAccessException
     */
    public void insert(AuthToken authtoken) throws Database.DatabaseException {
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
                    throw new Database.DatabaseException("error inserting");
                }
            }
            finally {
                if(stmt != null) {
                    stmt.close();
                }
            }
        } catch (SQLException e) {
            throw new Database.DatabaseException("error encountered while inserting into the database");
        }
    }

    /**
     * find specific authtoken using its ID
     * @param authtoken
     * @return
     * @throws DataAccessException
     */
    public boolean validAuthToken(String authtoken) throws Database.DatabaseException {
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "select * from Authtoken WHERE authtoken = '" + authtoken + "'";
                stmt = conn.prepareStatement(sql);
                rs = stmt.executeQuery();

                if(!rs.next()) {
                    throw new Database.DatabaseException("error finding Authtoken");
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
            throw new Database.DatabaseException("error finding Authtoken");
        }
    }


    /**
     * delete all data from table
     * @throws DataAccessException
     */
    public void clear() throws Database.DatabaseException { //Also clears tables
        try {
            Statement stmt = null;
            try {
                stmt = conn.createStatement();

                stmt.executeUpdate("drop table if exists Authtoken");
                stmt.executeUpdate("create table Authtoken (authtoken VARCHAR(50) NOT NULL PRIMARY KEY,\n" +
                        "\tuserName VARCHAR(50) NOT NULL,\n" +
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
            throw new Database.DatabaseException("error resetting Authtoken");
        }
    }

    public AuthToken getAuthToken(String authtoken) throws Database.DatabaseException {
        AuthToken auth = new AuthToken();
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
            throw new Database.DatabaseException("error getting AuthToken");
        }
        return auth;
    }

    public String tableToString() throws Database.DatabaseException{
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
                    String personID = rs.getString(3);

                    out.append((authtoken + "\t" + username + "\t" + personID + "\n"));
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
            throw new Database.DatabaseException("error toString table");
        }
        return out.toString();
    }

}
