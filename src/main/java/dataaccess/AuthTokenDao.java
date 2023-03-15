package dataaccess;

import model.AuthToken;

import java.sql.*;

public class AuthTokenDao {

    private Connection conn;

    public AuthTokenDao() {}

    public void setConnection(Connection c) throws DataAccessException {
        conn = c;
    }

    /**
     * insert a new authtoken onto authtoken table
     * @param authtoken
     * @throws DataAccessException
     */
    public void insert(AuthToken authtoken) throws DataAccessException {
        String sql = "insert into Authtoken (authtoken, userName) values (?,?)";

        try(PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, authtoken.getAuthtoken());
            stmt.setString(2, authtoken.getUsername());

            if (stmt.executeUpdate() != 1) {
                throw new DataAccessException("error inserting");
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
    public boolean validate(String authtoken) throws DataAccessException {
        String sql = "select * from Authtoken WHERE authtoken = '" + authtoken + "'";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = null;
            rs = stmt.executeQuery();

            if(!rs.next()) {
                throw new DataAccessException("error finding Authtoken");
            } else {
                return true;
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
            stmt = conn.createStatement();

            stmt.executeUpdate("drop table if exists Authtoken");
            stmt.executeUpdate("create table Authtoken (authtoken VARCHAR(50) NOT NULL PRIMARY KEY,\n" +
                    "\tuserName VARCHAR(50) NOT NULL,\n" +
                    "        CONSTRAINT auth_info UNIQUE (authtoken))");
        }
        catch (SQLException e) {
            throw new DataAccessException("error resetting Authtoken");
        }
    }

    public AuthToken getAuthToken(String authtoken) throws DataAccessException {
        AuthToken auth = new AuthToken();
        String sql = "select * from Authtoken WHERE authtoken = '" + authtoken +"'";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            ResultSet rs = null;
            rs = stmt.executeQuery();
            while (rs.next()) {
                auth.setAuthtoken(rs.getString(1));
                auth.setUsername(rs.getString(2));
            }
        }
        catch (SQLException e) {
            throw new DataAccessException("error getting AuthToken");
        }
        return auth;
    }

}
