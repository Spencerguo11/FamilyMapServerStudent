package service;

import dataaccess.AuthTokenDao;
import dataaccess.*;
import model.*;
import result.PersonResult;

public class PersonService {


    /**
     * current user determined by provided authtoken
     * @return All family members of current user
     */

    private Database db;

    public PersonService() {
        db = new Database();
    }

    public PersonResult person(String authtoken){
        PersonResult personResult = new PersonResult();

        try{
            db.openConnection();
            PersonDao personDao = db.getpersonDao();
            AuthTokenDao authTokenDao = db.getauthTokenDao();


            if(authTokenDao.validAuthToken(authtoken)){
                AuthToken auth = authTokenDao.getAuthToken(authtoken);
                personResult.setData(personDao.selectAllPersons(auth.getUsername()));
            }

            db.closeConnection(true);
            personResult.setSuccess(true);

        } catch (DataAccessException e){
            personResult.setSuccess(false);
            personResult.setMessage("{\"message\" : \" internal server error\"}");
            personResult.setMessage(e.getMessage());
            try{
                db.closeConnection(false);
            }catch (DataAccessException d){
                personResult.setSuccess(false);
                personResult.setMessage("{\"message\" : \" internal server error\"}");
                personResult.setMessage(d.getMessage());
            }
        }

        return personResult;
    }
}
