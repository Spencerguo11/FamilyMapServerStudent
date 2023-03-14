package Service;


import Model.Authtoken;
import Model.Event;
import Result.EventIDResult;
import dao.AuthtokenDAO;
import dao.DataAccessException;
import dao.Database;
import dao.EventDAO;

public class EventIDService {

    /**
     * current user is determined by the provided authtoken
     * @return Event object with specified ID
     */
    private Database db;

    public EventIDService(){
        db = new Database();
    }

    public EventIDResult eventID(String eventID, String authtoken){

        EventIDResult result = new EventIDResult();

        try{
            db.openConnection();

            EventDAO eDao = db.getEventDAO();
            AuthtokenDAO aDao = db.getAuthtokenDAO();


            if(aDao.validAuthToken(authtoken)){
                Authtoken auth = aDao.getAuthToken(authtoken);
                if (eDao.find(eventID)){
                    Event event = eDao.selectSingleEvent(eventID);
                    if (!event.getAssociatedUsername().equals(auth.getUsername())){
                        throw new DataAccessException("error finding username");
                    }

                    result = new EventIDResult(event);
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