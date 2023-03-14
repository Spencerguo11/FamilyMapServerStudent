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
            EventDao eDao = db.geteDao();
            AuthTokenDao aDao = db.getaDao();


            if(aDao.validAuthToken(authtoken)){
                AuthToken auth = aDao.getAuthToken(authtoken);
                result.setData(eDao.selectAllEvents(auth.getUsername()));
                db.closeConnection(true);
                result.setSuccess(true);
            } else {
                result.setSuccess(false);
                result.setMessage("{\"message\" : \" internal server error\"}");
                db.closeConnection(false);
            }



        } catch (Database.DatabaseException e){
            result.setSuccess(false);
            result.setMessage(e.getMessage());

            try{
                db.closeConnection(false);
            }catch (Database.DatabaseException d){
                result.setSuccess(false);
                result.setMessage(e.getMessage());
            }
        }

        return result;
    }
}
