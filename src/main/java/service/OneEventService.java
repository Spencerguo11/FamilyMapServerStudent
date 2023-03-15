package service;

import dataaccess.*;
import model.*;
import result.OneEventResult;

public class OneEventService {

    /**
     * current user is determined by the provided authtoken
     * @return Event object with specified ID
     */
    private Database db;

    public OneEventService(){
        db = new Database();
    }

    public OneEventResult eventID(String eventID, String authtoken){

        OneEventResult result = new OneEventResult();

        try{
            db.openConnection();

            EventDao eventDao = db.geteventDao();
            AuthTokenDao authTokenDao = db.getauthTokenDao();


            if(authTokenDao.validAuthToken(authtoken)){
                AuthToken auth = authTokenDao.getAuthToken(authtoken);
                if (eventDao.find(eventID)){
                    Event event = eventDao.selectSingleEvent(eventID);
                    if (!event.getAssociatedUsername().equals(auth.getUsername())){
                        throw new DataAccessException("error finding username");
                    }

                    result = new OneEventResult(event);
                }
            }

            db.closeConnection(true);
            result.setSuccess(true);

        } catch (DataAccessException e){
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            try{
                db.closeConnection(false);
            }catch (DataAccessException d){
                result.setSuccess(false);
                result.setMessage(d.getMessage());
            }
        }
        return result;
    }

}
