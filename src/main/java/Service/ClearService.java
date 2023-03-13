package Service;

import Result.ClearResult;
import Result.LoginResult;
import dao.*;

import java.sql.Connection;

/** A service to handle clearing the database
 *
 */
public class ClearService {
    private Database db ;


    public ClearService() {db = new Database();}

    /**
     * Clear all data
     * @return a Clear Result
     */
    public ClearResult clear() throws DataAccessException {
        Connection conn = db.getConnection();
        db.openConnection();
        ClearResult result = new ClearResult();

        try{
            db.openConnection();
            db.getUserDAO().clear();
            db.getAuthtokenDAO().clear();
            db.getPersonDAO().clear();
            db.getEventDAO().clear();
            db.closeConnection(true);

        } catch (DataAccessException dataAccessException){
            result.setMessage(dataAccessException.getMessage());
            dataAccessException.printStackTrace();

            try {
                db.closeConnection(false);
            } catch (Exception e) {
                result.setMessage(e.getMessage());
                return result;
            }
            return result;
        }
        result.setMessage("clear succeeded");
        result.setSuccess(true);
        return result;
    }


}
