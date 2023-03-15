package service;

import dataaccess.*;
import model.*;
import result.FillResult;

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

    public FillResult fill(String username, int generations){

        FillResult fillResult = new FillResult();

        try{
            db.openConnection();
            UserDao userDao = db.getuserDao();
            EventDao eventDao = db.geteventDao();
            PersonDao personDao = db.getpersonDao();
            if (!userDao.find(username)){
                throw new DataAccessException("username does not exist");
            }

            User user = userDao.getUser(username); //user does not have the same personID as the userName user
            user.setPersonID(UUID.randomUUID().toString());


            db.removeUser(user); //deletes all things of user, including the user

            userDao.insert(user); //inserts same user but with new personID into the database

            Person root = new Person(user);  //creates a person representation of the user

            personDao.insert(root); //inserts root into database

            int rootBirthYear = eventDao.generateRootEvent(root); //make root's events

            //Now were going to give generateGenerations root, which generates fathers and mothers, then generates fathers and mothers events, and each father and mother is passed on to have its generations made

            if (generations == -1){//default case
                personDao.generateGenerations(root, 4, eventDao, rootBirthYear); //default is four generations
                fillResult.setPersons(31);
                fillResult.setEvents(124);
                fillResult.setSuccess(true);
            } else {
                personDao.generateGenerations(root, generations, eventDao, rootBirthYear);
                double numG = (double) generations;
                double answer = (Math.pow(2.0, (numG + 1.0)) - 1.0);
                int finalAnswer = (int) answer;
                fillResult.setPersons(finalAnswer);
                fillResult.setEvents(finalAnswer * 4);
                fillResult.setSuccess(true);
            }

            db.closeConnection(true);

        } catch (DataAccessException e){
            fillResult.setSuccess(false);
            fillResult.setMessage(e.getMessage());

            try{
                db.closeConnection(false);
            }catch (DataAccessException d){
                fillResult.setSuccess(false);
                fillResult.setMessage(d.getMessage());
            }
        }
        return fillResult;
    }
}
