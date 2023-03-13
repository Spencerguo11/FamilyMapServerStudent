package dao;

/**
 * A class to handle data access issues
 */
public class DataAccessException extends Exception {
    /**
     * The exception of data access issues
     * @param message pass in a string
     */
    public DataAccessException(String message) {
        // throw an exception
        super(message);
    }
}
