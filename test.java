package service;

import dataaccess.*;
import model.*;

public class EventService {

    /**
     * current user is determined by the provided authtoken
     * @return All events for All family members of current user
     */

    private Database database;

    public EventService() {
        database = new Database();
    }

    public EventResult event(String authtoken){
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
                result.setMessage("{\"message\" : \" internal server error\"}");
                database.closeConnection(false);
            }



        } catch (DataAccessException e){
            result.setSuccess(false);
            result.setMessage(e.getMessage());

            try{
                database.closeConnection(false);
            }catch (DataAccessException d){
                result.setSuccess(false);
                result.setMessage(e.getMessage());
            }
        }

        return result;
    }
}