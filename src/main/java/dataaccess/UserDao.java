package dataaccess;

import model.User;

import java.sql.*;
import java.util.UUID;

public class UserDao {

    private Connection conn;

    public UserDao() {}

    public void setConnection(Connection c) throws Database.DatabaseException{
        conn = c;
    }
    /**
     * insert a new user onto user table
     * @param user
     * @throws DataAccessException
     */
    public void insert(User user) throws Database.DatabaseException {

        try {
            PreparedStatement stmt = null;
            try {
                String sql = "insert into users (username, password, email, firstName, lastName, gender, personId) values (?,?,?,?,?,?,?)";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, user.getUsername());
                stmt.setString(2, user.getPassword());
                stmt.setString(3, user.getEmail());
                stmt.setString(4, user.getFirstName());
                stmt.setString(5, user.getLastName());
                stmt.setString(6, user.getGender());
                stmt.setString(7, user.getPersonID());

                if (stmt.executeUpdate() != 1) {
                    throw new Database.DatabaseException("error inserting user");
                }
            } finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        } catch (SQLException e) {
                throw new Database.DatabaseException("error inserting user");
            }

    }

    /**
     * find user in table using userID
     * @param username
     * @return
     * @throws DataAccessException
     */
    public boolean find(String username) throws Database.DatabaseException {
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "select * from users WHERE username = '" + username + "'";
                stmt = conn.prepareStatement(sql);

                rs = stmt.executeQuery();

                if (!rs.next() ) {
                    throw new Database.DatabaseException("error finding username");
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
            throw new Database.DatabaseException("error finding username");
        }

    }

    /**
     * clears user table
     * @throws DataAccessException
     */
    public void clear() throws Database.DatabaseException {
        try {
            Statement stmt = null;
            try {
                stmt = conn.createStatement();

                stmt.executeUpdate("drop table if exists users");
                stmt.executeUpdate("create table users (username VARCHAR(50) NOT NULL PRIMARY KEY, password VARCHAR(50) NOT NULL, email VARCHAR(50) NOT NULL, firstName VARCHAR(50) NOT NULL, " +
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
            throw new Database.DatabaseException("error resetting table");
        }
    }

    public boolean ExistingUsernamePassword(User user) throws Database.DatabaseException {
     try{
         PreparedStatement stmt = null;
         ResultSet rs = null;
         try {
             String username = user.getUsername();
             String sql = "select * from users WHERE username = '" + user.getUsername() + "' AND password = '" + user.getPassword() + "'";
             stmt = conn.prepareStatement(sql);
             rs =  stmt.executeQuery();

             if(!rs.next()) {
                 throw new Database.DatabaseException("error finding username/password");
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
         throw new Database.DatabaseException("error finding username/password");
         }
     }

     public User getUser(String username) throws Database.DatabaseException {
        User user = new User();
         try {
             PreparedStatement stmt = null;
             ResultSet rs = null;
             try {
                 String sql = "select * from users WHERE username = '" + username +"'";
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
             throw new Database.DatabaseException("error getting username");
         }
         return user;
     }

    public String getPersonIDOfUser(User user) throws Database.DatabaseException {
        String personID = new String();
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "select * from users WHERE username = '" + user.getUsername() + "' AND password = '" + user.getPassword() + "'";
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
            throw new Database.DatabaseException("error getting personID of user");
        }

        return personID;
    }

    public String tableToString() throws Database.DatabaseException{
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
            throw new Database.DatabaseException("error toString table");
        }
        return out.toString();
    }

    public void deleteUser(User user) throws Database.DatabaseException {
        try {
            Statement stmt = null;
            try {
                stmt = conn.createStatement();

                stmt.executeUpdate("DELETE FROM users WHERE username = '" + user.getUsername() + "'");

            }
            finally {
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
            }
        }
        catch (SQLException e) {
            throw new Database.DatabaseException("error deleting user");
        }
    }

}

