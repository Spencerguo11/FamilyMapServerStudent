package service;

import dataaccess.*;
import model.*;
import result.FillResult;

import java.util.UUID;

public class FillService {

    private Database database;

    public FillService() {database = new Database();}

    public FillResult fill(String username, int gen) throws DataAccessException {

        FillResult fillResult = new FillResult();

        try{
            database.openConnection();
            UserDao userDao = database.getuserDao();
            EventDao eventDao = database.geteventDao();
            PersonDao personDao = database.getpersonDao();

            User user = userDao.getUser(username);
            user.setPersonID(UUID.randomUUID().toString());
            database.removeUser(user);
            userDao.insert(user);
            Person defaultPerson = new Person(user);
            personDao.insert(defaultPerson);

            int year = eventDao.generateEvents(defaultPerson);

            if (gen == -1){
                personDao.generateGenerations(defaultPerson, 4, eventDao, year);
                fillResult.setPersons(31); // 4 generation
                fillResult.setEvents(124); // 4 generations
                fillResult.setSuccess(true);
            } else {
                personDao.generateGenerations(defaultPerson, gen, eventDao, year);
                fillResult.setPersons((int) (Math.pow(2.0, (gen + 1.0)) - 1.0));
                fillResult.setEvents((int) (Math.pow(2.0, (gen + 1.0)) - 1.0) * 4);
                fillResult.setSuccess(true);
            }

            database.closeConnection(true);

        } catch (DataAccessException e) {
            fillResult.setSuccess(false);
            fillResult.setMessage(e.getMessage());
            database.closeConnection(false);
        }
        return fillResult;
    }
}
