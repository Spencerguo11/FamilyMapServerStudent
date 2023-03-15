package service;

import dataaccess.*;
import result.ClearResult;

public class ClearService {

    private Database database;

    public ClearService() {database = new Database();}

    public ClearResult clear() throws DataAccessException {
        ClearResult clearResult = new ClearResult();
        try {
            database.openConnection();
            database.clearAll();
            database.closeConnection(true);
            clearResult.setMessage("Clear succeeded");
            return clearResult;

        } catch (DataAccessException e) {
            clearResult.setMessage(e.getMessage());
            database.closeConnection(false);
            return clearResult;
        }
    }
}
