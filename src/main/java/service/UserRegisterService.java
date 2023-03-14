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


            UserDao uDao = db.getuDao();
            PersonDao pDao = db.getpDao();
            EventDao eDao = db.geteDao();
            AuthTokenDao aDao = db.getaDao();



            User user = new User(input);
            uDao.insert(user);

            Person person = new Person(user);

            pDao.insert(person); //inserts root into database

            int rootBirthYear = eDao.generateRootEvent(person); //make root's events

            //Now were going to give generations root, which generates fathers and mothers, then generates fathers and mothers events, and each father and mother is passed on to have its generations made
            pDao.generateGenerations(person, 4, eDao, rootBirthYear);


            //Auth token stuff
            AuthToken auth = new AuthToken(user);
            aDao.insert(auth);
            result = new RegisterResult(auth, user.getPersonID());
            result.setSuccess(true);


            db.closeConnection(true);

        } catch (Database.DatabaseException e){
            result.setSuccess(false);
            result.setMessage(e.getMessage());

            try{
                db.closeConnection(false);
            }catch (Database.DatabaseException d){
                result.setSuccess(false);
                result.setMessage(d.getMessage());
            }
        }
        return result;
    }
}