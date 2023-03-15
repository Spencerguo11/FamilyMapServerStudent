package service;

import dataaccess.*;
import model.*;
import result.EventResult;

public class EventService {

    private Database database;

    public EventService() {
        database = new Database();
    }

    public EventResult event(String authtoken) throws DataAccessException {
        EventResult result = new EventResult();

        try{
            database.openConnection();
            EventDao eventDao = database.geteventDao();
            AuthTokenDao authTokenDao = database.getauthTokenDao();
            if(authTokenDao.validate(authtoken)){
                AuthToken auth = authTokenDao.getAuthToken(authtoken);
                result.setData(eventDao.selectAllEvents(auth.getUsername()));
                database.closeConnection(true);
                result.setSuccess(true);
            } else {
                result.setSuccess(false);
                result.setMessage("{\"message\" : \" server error\"}");
                database.closeConnection(false);
            }

        } catch (DataAccessException e){
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            database.closeConnection(false);
        }

        return result;
    }
}
