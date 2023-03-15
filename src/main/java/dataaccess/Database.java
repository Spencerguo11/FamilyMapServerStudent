package dataaccess;

import model.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private Connection conn;
    private UserDao userDao;
    private PersonDao personDao;
    private EventDao eventDao;
    private AuthTokenDao authTokenDao;
    
    public Database() {
        userDao = new UserDao();
        personDao = new PersonDao();
        eventDao = new EventDao();
        authTokenDao = new AuthTokenDao();
    }

    public Connection openConnection() throws DataAccessException {
        try {

            final String CONNECTION_URL = "jdbc:sqlite:server.db";

            // Open a database connection to the file given in the path
            conn = DriverManager.getConnection(CONNECTION_URL);

            userDao.setConnection(conn);
            personDao.setConnection(conn);
            eventDao.setConnection(conn);
            authTokenDao.setConnection(conn);


            // Start a transaction
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to open connection to database");
        }

        return conn;
    }

    public Connection getConnection() throws DataAccessException {
        if(conn == null) {
            return openConnection();
        } else {
            return conn;
        }
    }


    public void closeConnection(boolean commit) throws DataAccessException {
        try {
            if (commit) {
                //This will commit the changes to the database
                conn.commit();
            } else {
                //If we find out something went wrong, pass a false into closeConnection and this
                //will rollback any changes we made during this connection
                conn.rollback();
            }

            conn.close();
            conn = null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to close database connection");
        }
    }

    public void clearAll() throws DataAccessException {
        authTokenDao.clear();
        userDao.clear();
        personDao.clear();
        eventDao.clear();
    }

    public void removeUser(User u) throws DataAccessException {
        userDao.deleteUser(u);
        personDao.deleteAllPeopleofUser(u);
        eventDao.deleteAllEventsOfUser(u);
    }

    public UserDao getuserDao() {
        return userDao;
    }

    public PersonDao getpersonDao() {
        return personDao;
    }

    public EventDao geteventDao() {
        return eventDao;
    }

    public AuthTokenDao getauthTokenDao() {
        return authTokenDao;
    }
}

