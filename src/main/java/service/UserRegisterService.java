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

    private Database database;

    public UserRegisterService(){database = new Database();}

    public RegisterResult register(RegisterRequest input) throws DataAccessException {
        RegisterResult result = new RegisterResult();

        try{
            database.openConnection();
            UserDao userDao = database.getuserDao();
            PersonDao personDao = database.getpersonDao();
            EventDao eventDao = database.geteventDao();
            AuthTokenDao authTokenDao = database.getauthTokenDao();
            
            User user = new User(input);
            userDao.insert(user);
            Person person = new Person(user);
            personDao.insert(person); 

            int year = eventDao.generateEvents(person);
            personDao.generateGenerations(person, 4, eventDao, year);

            AuthToken auth = new AuthToken(user);
            authTokenDao.insert(auth);
            result = new RegisterResult(auth, user.getPersonID());
            result.setSuccess(true);
            database.closeConnection(true);

        } catch (DataAccessException e){
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            database.closeConnection(false);

        }
        return result;
    }
}