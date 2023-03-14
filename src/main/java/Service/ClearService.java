//package Service;
//
//import Result.ClearResult;
//import Result.LoginResult;
//import dao.*;
//
//import java.sql.Connection;
//
///** A service to handle clearing the database
// *
// */
//public class ClearService {
//    private Database db ;
//
//
//    public ClearService() {db = new Database();}
//
//    /**
//     * Clear all data
//     * @return a Clear Result
//     */
//    public ClearResult clear() throws DataAccessException {
////        Connection conn = db.getConnection();
////        db.openConnection();
//        ClearResult result = new ClearResult();
//
//        try{
//            db.openConnection();
//            db.getUserDAO().clear();
//            db.getAuthtokenDAO().clear();
//            db.getPersonDAO().clear();
//            db.getEventDAO().clear();
//            result.setMessage("clear succeeded");
//            result.setSuccess(true);
//
//        } catch (DataAccessException dataAccessException){
//            result.setMessage(dataAccessException.getMessage());
//            dataAccessException.printStackTrace();
//            result.setSuccess(false);
//            result.setMessage("failed");
//            db.closeConnection(false);
//
////            try {
////                db.closeConnection(false);
////            } catch (Exception e) {
////                db.closeConnection(false);
////                result.setMessage(e.getMessage());
////                return result;
////            }
//        }finally{
//            if(result.isSuccess()){
//                db.closeConnection(true);
//            }else{
//                db.closeConnection(false);
//            }
//            return result;
//        }
//
//
//    }
//
//
//}

package Service;


import Result.ClearResult;
import dao.DataAccessException;
import dao.Database;

public class ClearService {

    /**
     * Deletes all data from database
     * @return cleared database with message
     */

    private Database db;

    public ClearService() {
        db = new Database();
    }

    public ClearResult clear() {
        ClearResult clearResult = new ClearResult();
        try {
            db.openConnection();
            db.clearTables();

            db.closeConnection(true);


        } catch ( DataAccessException e) {
            e.printStackTrace();
            clearResult.setMessage(e.getMessage());

            try {
                db.closeConnection(false);
            } catch (DataAccessException d) {
                clearResult.setMessage(d.getMessage());
                return clearResult;
            }
            return clearResult;
        }
        clearResult.setMessage("clear succeeded");
        return clearResult;

    }
}