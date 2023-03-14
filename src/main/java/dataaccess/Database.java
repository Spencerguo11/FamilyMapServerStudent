package dataaccess;

import model.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    public static class DatabaseException extends Exception {
        private String message;

        public DatabaseException(){
            message = new String();
        }
        public DatabaseException(String message){
            this.message = message;
        }
        public String getMessage(){
            return message;
        }
    }

    private Connection conn;
    private UserDao uDao;
    private PersonDao pDao;
    private EventDao eDao;
    private AuthTokenDao aDao;

    static {
        try {
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);
        }
        catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Database() {
        uDao = new UserDao();
        pDao = new PersonDao();
        eDao = new EventDao();
        aDao = new AuthTokenDao();
    }

    public Connection openConnection() throws DatabaseException {
        try {

            final String CONNECTION_URL = "jdbc:sqlite:server.db";

            // Open a database connection to the file given in the path
            conn = DriverManager.getConnection(CONNECTION_URL);

            uDao.setConnection(conn);
            pDao.setConnection(conn);
            eDao.setConnection(conn);
            aDao.setConnection(conn);


            // Start a transaction
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Unable to open connection to database");
        }

        return conn;
    }

    public void deleteEverythingOfUser(User u) throws DatabaseException { //does not make new usermodel of username given
        uDao.deleteUser(u);
        pDao.deleteAllPeopleofUser(u);
        eDao.deleteAllEventsOfUser(u);

    }

    public Connection getConnection() throws DatabaseException {
        if(conn == null) {
            return openConnection();
        } else {
            return conn;
        }
    }


    public void closeConnection(boolean commit) throws DatabaseException {
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
            throw new DatabaseException("Unable to close database connection");
        }
    }

    public void clearTables() throws DatabaseException {
        aDao.clear();
        uDao.clear();
        pDao.clear();
        eDao.clear();


    }

    public UserDao getuDao() {
        return uDao;
    }

    public PersonDao getpDao() {
        return pDao;
    }

    public EventDao geteDao() {
        return eDao;
    }

    public AuthTokenDao getaDao() {
        return aDao;
    }
}

