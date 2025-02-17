//package dao;
//
//import Model.Authtoken;
//import Model.Event;
//import Model.Person;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.PrimitiveIterator;
//
///**
// * A class to handle interacting with the database
// */
//public class Database {
//    private Connection conn;
//    private UserDAO userDAO;
//    private PersonDAO personDAO;
//    private EventDAO eventDAO;
//    private AuthtokenDAO authtokenDAO;
//
//    public Database(){
//        userDAO = new UserDAO();
//        personDAO = new PersonDAO();
//        eventDAO = new EventDAO();
//        authtokenDAO = new AuthtokenDAO();
//    }
//
//
//    /**
//     * Open a connection and use the statements to initial transactions.
//     * @return A connection
//     * @throws DataAccessException
//     */
//    // Whenever we want to make a change to our database we will have to open a connection and use
//    // Statements created by that connection to initiate transactions
//    public Connection openConnection() throws DataAccessException {
//        try {
//            // The Structure for this Connection is driver:language:path
//            // The path assumes you start in the root of your project unless given a full file path
//
////            final String CONNECTION_URL = "jdbc:sqlite:FamilyServer.sqlite"; // original
//            final String CONNECTION_URL = "jdbc:sqlite:server.db";
//
//            // Open a database connection to the file given in the path
//            conn = DriverManager.getConnection(CONNECTION_URL);
//
//            userDAO.setConnection(conn);
//            personDAO.setConnection(conn);
//            eventDAO.setConnection(conn);
//            authtokenDAO.setConnection(conn);
//
//
//            // Start a transaction
//            conn.setAutoCommit(false);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw new DataAccessException("Unable to open connection to database");
//        }
//
//        return conn;
//    }
//
//
//    /**
//     * Find the connection
//     * @return An connection
//     * @throws DataAccessException
//     */
//    public Connection getConnection() throws DataAccessException {
//        if (conn == null) {
//            return openConnection();
//        } else {
//            return conn;
//        }
//    }
//
//    // When we are done manipulating the database it is important to close the connection. This will
//    // end the transaction and allow us to either commit our changes to the database (if true is passed in)
//    // or rollback any changes that were made before we encountered a potential error (if false is passed in).
//
//    // IMPORTANT: IF YOU FAIL TO CLOSE A CONNECTION AND TRY TO REOPEN THE DATABASE THIS WILL CAUSE THE
//    // DATABASE TO LOCK. YOUR CODE MUST ALWAYS CLOSE THE DATABASE NO MATTER WHAT ERRORS
//    // OR PROBLEMS ARE ENCOUNTERED
//
//    /**
//     * Close the connection
//     * @param commit A boolean input
//     */
//    public void closeConnection(boolean commit) throws DataAccessException {
//        try {
//            if (commit) {
//                // This will commit the changes to the database
//                conn.commit();
//            } else {
//                // If we find out something went wrong, pass a false into closeConnection and this
//                // will rollback any changes we made during this connection
//                conn.rollback();
//            }
//            conn.close();
//            conn = null;
//        } catch (SQLException e) {
//            // If you get here there are probably issues with your code and/or a connection is being left open
//            e.printStackTrace();
//            throw new DataAccessException("Unable to close database connection");
//        }
//    }
//
//
//
//    public UserDAO getUserDAO() {
//        return userDAO;
//    }
//
//    public PersonDAO getPersonDAO() {
//        return personDAO;
//    }
//
//    public EventDAO getEventDAO() {
//        return eventDAO;
//    }
//
//    public AuthtokenDAO getAuthtokenDAO() {
//        return authtokenDAO;
//    }
//
//    public void clearTables() throws DataAccessException {
//        authtokenDAO.clear();
//        userDAO.clear();
//        personDAO.clear();
//        eventDAO.clear();
//    }
//}
//


package dao;

import Model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {


    private Connection conn;
    private UserDAO uDao;
    private PersonDAO pDao;
    private EventDAO eDao;
    private AuthtokenDAO aDao;

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
        uDao = new UserDAO();
        pDao = new PersonDAO();
        eDao = new EventDAO();
        aDao = new AuthtokenDAO();
    }

    public Connection openConnection() throws DataAccessException {
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
            throw new DataAccessException("Unable to open connection to database");
        }

        return conn;
    }

    public void deleteEverythingOfUser(User u) throws DataAccessException { //does not make new usermodel of username given
        uDao.deleteUser(u);
        pDao.deleteAllPeopleofUser(u);
        eDao.deleteAllEventsOfUser(u);

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

    public void clearTables() throws DataAccessException {
        aDao.clear();
        uDao.clear();
        pDao.clear();
        eDao.clear();


    }

    public UserDAO getUserDAO() {
        return uDao;
    }

    public PersonDAO getPersonDAO() {
        return pDao;
    }

    public EventDAO getEventDAO() {
        return eDao;
    }

    public AuthtokenDAO getAuthtokenDAO() {
        return aDao;
    }
}