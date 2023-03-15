package service;

import dataaccess.*;
import model.*;
import result.OneEventResult;

public class OneEventService {

    /**
     * current user is determined by the provided authtoken
     * @return Event object with specified ID
     */
    private Database database;

    public OneEventService(){
        database = new Database();
    }

    public OneEventResult eventID(String eventID, String authtoken) throws DataAccessException {

        OneEventResult result = new OneEventResult();

        try{
            database.openConnection();

            EventDao eventDao = database.geteventDao();
            AuthTokenDao authTokenDao = database.getauthTokenDao();


            if(authTokenDao.validate(authtoken)){
                AuthToken auth = authTokenDao.getAuthToken(authtoken);
                if (eventDao.find(eventID)){
                    Event event = eventDao.selectSingleEvent(eventID);
                    if (!event.getAssociatedUsername().equals(auth.getUsername())){
                        throw new DataAccessException("error finding username");
                    }

                    result = new OneEventResult(event);
                }
            }

            database.closeConnection(true);
            result.setSuccess(true);

        } catch (DataAccessException e){
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            database.closeConnection(false);
        }
        return result;
    }

}
