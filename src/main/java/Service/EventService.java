//package Service;
//
//import Model.Event;
//import Model.Person;
//import Result.EventResult;
//import Result.PersonResult;
//import dao.*;
//
//import java.sql.Connection;
//import java.util.List;
//
///**
// * A class to handle getting an event information from database
// */
//
//public class EventService {
//    private Database db ;
//
//    public EventService(){db = new Database();}
//
//
//
//    /**
//     * Get all event'f information
//     * @param authtoken An string of token
//     * @return An EventResult
//     */
//    public EventResult getAllEventInfo(String authtoken) throws DataAccessException{
//
//        EventResult eventResult = new EventResult();
//
//        try{
//            db.openConnection();
//            EventDAO eventDAO = db.getEventDAO();
//            AuthtokenDAO authtokenDAO = db.getAuthtokenDAO();
//            String username = authtokenDAO.findUsernameByAuthToken(authtoken);
//            List<Event> events = eventDAO.listAllEventsByUsername(username);
//
//            eventResult = new EventResult(events);
//
//            eventResult.setSuccess(true);
//            db.closeConnection(true);
//
//        } catch (DataAccessException dataAccessException){
//            eventResult.setMessage(dataAccessException.getMessage());
//            eventResult.setSuccess(false);
//        }
//
//        return eventResult;
//    }
//
//    /**
//     * Find an event by event ID
//     * @param eventID An event ID
//     * @param authtoken An string of token
//     * @return An event object
//     */
//    public EventResult getAEventbyEventID(String eventID, String authtoken) throws DataAccessException {
//
//        EventResult eventResult = new EventResult();
//
//        try{
//            db.openConnection();
//            EventDAO eventDAO = db.getEventDAO();
//            AuthtokenDAO authtokenDAO = db.getAuthtokenDAO();
//            String username = authtokenDAO.findUsernameByAuthToken(authtoken);
//            List<Event> events = eventDAO.listAllEventsByUsername(username);
//            Event targetEvent = null;
//            for(int i = 0; i < events.size(); i++){
//                if(events.get(i).getEventID().equals(eventID)){
//                    targetEvent = events.get(i);
//                }
//            }
//
//            if(targetEvent != null) {
//                eventResult = new EventResult(targetEvent, true);
//            } else{
//                eventResult.setMessage("Cannot find this event");
//                eventResult.setSuccess(false);
//            }
//
////            db.closeConnection(true);
//
//        } catch (DataAccessException dataAccessException){
//            eventResult.setMessage(dataAccessException.getMessage());
//            eventResult.setSuccess(false);
////            db.closeConnection(false);
//        } finally {
//            if (eventResult.isSuccess()) {
//                db.closeConnection(true);
//            } else {
//                db.closeConnection(false);
//            }
//        }
//
//        return eventResult;
//    }
//
//    public boolean validateToken(String authToken) throws DataAccessException {
//        try {
//            db.openConnection();
//            AuthtokenDAO authtokenDAO = db.getAuthtokenDAO();
//            db.closeConnection(true);
//            return authtokenDAO.validate(authToken);
//        } catch (Exception e) {
//            db.closeConnection(false);
//            e.printStackTrace();
//        }
//        return false;
//    }
//}


package Service;


import Model.Authtoken;
import Result.EventResult;
import dao.AuthtokenDAO;
import dao.DataAccessException;
import dao.Database;
import dao.EventDAO;

import java.util.List;

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
            EventDAO eDao = db.getEventDAO();
            AuthtokenDAO aDao = db.getAuthtokenDAO();


            if(aDao.validAuthToken(authtoken)){
                Authtoken auth = aDao.getAuthToken(authtoken);
                result.setData(eDao.selectAllEvents(auth.getUsername()));
                db.closeConnection(true);
                result.setSuccess(true);
            } else {
                result.setSuccess(false);
                result.setMessage("{\"message\" : \" internal server error\"}");
                db.closeConnection(false);
            }



        } catch ( DataAccessException e){
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