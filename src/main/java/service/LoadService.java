package service;

import dataaccess.*;
import model.*;
import model.Event;
import request.LoadRequest;
import result.LoadResult;

public class LoadService {

    /**
     * clears all data from database
     * loads data from LoadRequest input into the database
     * @param input
     * @return
     */

    private Database db;

    public LoadService() {
        db = new Database();
    }

    public LoadResult load(LoadRequest input){
        LoadResult loadResult = new LoadResult();
        try {
            db.openConnection();
            db.clearAll();

            UserDao userDao = db.getuserDao();
            PersonDao personDao = db.getpersonDao();
            EventDao eventDao = db.geteventDao();

            User[] users = input.getUsers();
            Person[] persons = input.getPersons();
            Event[] events = input.getEvents();

            for(int i = 0; i < users.length; i++) {
                userDao.insert(users[i]);
            }

            for(int i = 0; i < persons.length; i++) {
                personDao.insert(persons[i]);
            }

            for(int i = 0; i <events.length; i++) {
                eventDao.insert(events[i]);
            }

            db.closeConnection(true);
            loadResult.setSuccess(true);
            loadResult.setNumUsers(users.length);
            loadResult.setNumPersons(persons.length);
            loadResult.setNumEvents(events.length);

        } catch (DataAccessException e) {
            loadResult.setSuccess(false);
            loadResult.setMessage(e.getMessage());

            try {
                db.closeConnection(false);
            } catch (DataAccessException d) {
                loadResult.setSuccess(false);
                loadResult.setMessage(d.getMessage());
            }
        }
        return loadResult;
    }
}
