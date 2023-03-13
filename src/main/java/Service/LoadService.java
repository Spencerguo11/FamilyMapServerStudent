package Service;

import Model.Event;
import Model.Person;
import Model.User;
import Request.LoadRequest;
import Result.LoadResult;
import dao.*;

import java.sql.Connection;

/**
 * A service to handle loading in information
 */
public class LoadService {
    private Database db ;


    public LoadService() {db = new Database();}

    /**
     * load all users, persons, events into the database
     * @param request pass in a load request
     * @return a Load Result
     */
    public LoadResult load(LoadRequest request) throws DataAccessException {
        db = new Database();
        Connection conn = db.getConnection();
        LoadResult loadResult = new LoadResult();

        try{
            db.openConnection();

            AuthtokenDAO authtokenDAO = db.getAuthtokenDAO();
            UserDAO userDAO = db.getUserDAO();
            PersonDAO personDAO = db.getPersonDAO();
            EventDAO eventDAO = db.getEventDAO();

            userDAO.clear();
            authtokenDAO.clear();
            personDAO.clear();
            eventDAO.clear();

            Person[] persons = request.getPersons();
            Event[] events = request.getEvents();
            User[] users = request.getUsers();

            for(int i = 0; i < persons.length; i++){
                personDAO.insert(persons[i]);
            }

            for(int i = 0; i < users.length; i++){
                userDAO.insert(users[i]);
            }

            for(int i = 0; i < events.length; i++){
                eventDAO.insert(events[i]);
            }

            loadResult.setMessage("message\":\"Successfully added " + users.length + " users," + persons.length + " persons, and " + events.length + " events to the database.");
            loadResult.setSuccess(true);
            db.closeConnection(true);

        } catch (DataAccessException dataAccessException){
            loadResult.setMessage(dataAccessException.getMessage());
            loadResult.setSuccess(false);
        }


        return loadResult;
    }
}
