package service;

import dataaccess.*;
import result.ClearResult;

public class ClearService {

    /**
     * Deletes all data from database
     * @return cleared database with message
     */

    private Database db;

    public ClearService() {db = new Database();}

    public ClearResult clear() {
        ClearResult clearResult = new ClearResult();
        try {
            db.openConnection();
            db.clearAll();

            db.closeConnection(true);


        } catch (DataAccessException e) {
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
        clearResult.setMessage("Clear succeeded");
        return clearResult;

    }
}
