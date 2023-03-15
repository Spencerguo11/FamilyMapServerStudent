package service;

import dataaccess.*;
import model.*;
import request.RegisterRequest;
import result.RegisterResult;

public class UserRegisterService {

    /**
     * creates new account using input from RegisterRequest
     * generate 4 generations of ancestor data using user's username as parameters
     * logs user in
     *
     * @param input
     * @return authtoken
     */

    private Database db;

    public UserRegisterService(){db = new Database();}

    public RegisterResult register(RegisterRequest input){
        RegisterResult result = new RegisterResult();

        try{
            db.openConnection();


            UserDao userDao = db.getuserDao();
            PersonDao personDao = db.getpersonDao();
            EventDao eventDao = db.geteventDao();
            AuthTokenDao authTokenDao = db.getauthTokenDao();



            User user = new User(input);
            userDao.insert(user);

            Person person = new Person(user);

            personDao.insert(person); //inserts root into database

            int rootBirthYear = eventDao.generateRootEvent(person); //make root's events

            //Now were going to give generations root, which generates fathers and mothers, then generates fathers and mothers events, and each father and mother is passed on to have its generations made
            personDao.generateGenerations(person, 4, eventDao, rootBirthYear);


            //Auth token stuff
            AuthToken auth = new AuthToken(user);
            authTokenDao.insert(auth);
            result = new RegisterResult(auth, user.getPersonID());
            result.setSuccess(true);


            db.closeConnection(true);

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