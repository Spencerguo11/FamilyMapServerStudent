package dataaccess;

public class DataAccessException extends Exception{

    public DataAccessException(String error){
        super (error);
    }
    DataAccessException(){super();}
}
