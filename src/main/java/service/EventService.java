package service;

import dataaccess.*;
import model.*;
import result.EventResult;

public class EventService {

    /**
     * current user is determined by the provided authtoken
     * @return All events for All family members of current user
     */

    private Database db;

    public EventService() {
        db = new Database();
    }

    public EventResult event(String authtoken){
        EventResult result = new EventResult();

        try{

            db.openConnection();
            EventDao eventDao = db.geteventDao();
            AuthTokenDao authTokenDao = db.getauthTokenDao();


            if(authTokenDao.validAuthToken(authtoken)){
                AuthToken auth = authTokenDao.getAuthToken(authtoken);
                result.setData(eventDao.selectAllEvents(auth.getUsername()));
                db.closeConnection(true);
                result.setSuccess(true);
            } else {
                result.setSuccess(false);
                result.setMessage("{\"message\" : \" internal server error\"}");
                db.closeConnection(false);
            }



        } catch (DataAccessException e){
            result.setSuccess(false);
            result.setMessage(e.getMessage());

            try{
                db.closeConnection(false);
            }catch (DataAccessException d){
                result.setSuccess(false);
                result.setMessage(e.getMessage());
            }
        }

        return result;
    }
}
