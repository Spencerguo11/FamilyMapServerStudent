package Service;

import Model.Person;
import Model.User;
import Result.FillResult;
import dao.DataAccessException;
import dao.Database;
import dao.EventDAO;
import dao.PersonDAO;
import dao.UserDAO;

import java.util.UUID;

public class FillService {

    /**
     * populates server's database with generated data for specific username
     * if data already exists, original data is to be deleted
     * optional generation parameter to specify number of generations generated
     * @param username, generations(optional)
     * @return
     */

    private Database db;

    public FillService() {db = new Database();}

    public FillResult fill(String username, int generations) throws DataAccessException{

        FillResult result = new FillResult();

        try{
            db.openConnection();
            UserDAO userDao = db.getUserDAO();
            EventDAO eventDao = db.getEventDAO();
            PersonDAO personDao = db.getPersonDAO();
            if (userDao.find(username) == null){
                throw new DataAccessException("username does not exist");
            }

            User user = userDao.find(username); //user does not have the same personID as the userName user
            user.setPersonID(UUID.randomUUID().toString());

            //deletes all things of user, including the user
            userDao.clearByUsername(username);
            personDao.clearByUsername(username);
            eventDao.clearByUsername(username);

            userDao.insert(user); //inserts same user but with new personID into the database

            Person userPerson = new Person(user);  //creates a person representation of the user

            personDao.insert(userPerson); //inserts root into database

            int rootBirthYear = eventDao.generateBaseEvent(userPerson); //make root's events

            //Now were going to give generateGenerations root, which generates fathers and mothers, then generates fathers and mothers events, and each father and mother is passed on to have its generations made

            if (generations == -1){//default case
                personDao.generateAllGenerations(userPerson, 4, eventDao, rootBirthYear); //default is four generations
                result.setNumPersons(31);
                result.setNumEvents(124);
                result.setSuccess(true);
            } else {
                personDao.generateAllGenerations(userPerson, generations, eventDao, rootBirthYear);
                double numGen = (double) generations;
                double numPeoplePrecise = (Math.pow(2.0, (numGen + 1.0)) - 1.0);
                int numPerson = (int) numPeoplePrecise;
                result.setNumPersons(numPerson);
                result.setNumEvents(numPerson * 4);
                result.setSuccess(true);
            }

            db.closeConnection(true);

        } catch (DataAccessException e){
            result.setSuccess(false);
            result.setMessage(e.getMessage());

            db.closeConnection(false);
        }
        return result;
    }
}