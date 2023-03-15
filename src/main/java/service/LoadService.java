package service;

import dataaccess.*;
import model.*;
import model.Event;
import request.LoadRequest;
import result.LoadResult;

public class LoadService {

    private Database database;

    public LoadService() {
        database = new Database();
    }

    public LoadResult load(LoadRequest loadRequest) throws DataAccessException {
        LoadResult loadResult = new LoadResult();
        try {
            database.openConnection();
            database.clearAll();

            UserDao userDao = database.getuserDao();
            PersonDao personDao = database.getpersonDao();
            EventDao eventDao = database.geteventDao();

            User[] users = loadRequest.getUsers();
            Person[] persons = loadRequest.getPersons();
            Event[] events = loadRequest.getEvents();

            for(int i = 0; i < users.length; i++) {
                userDao.insert(users[i]);
            }

            for(int i = 0; i < persons.length; i++) {
                personDao.insert(persons[i]);
            }

            for(int i = 0; i < events.length; i++) {
                eventDao.insert(events[i]);
            }

            database.closeConnection(true);
            loadResult.setSuccess(true);
            loadResult.setNumUsers(users.length);
            loadResult.setNumPersons(persons.length);
            loadResult.setNumEvents(events.length);

        } catch (DataAccessException e) {
            loadResult.setSuccess(false);
            loadResult.setMessage(e.getMessage());
            database.closeConnection(false);
        }
        return loadResult;
    }
}
