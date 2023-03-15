package service;

import dataaccess.AuthTokenDao;
import dataaccess.*;
import model.*;
import result.PersonResult;

public class PersonService {

    private Database database;

    public PersonService() {
        database = new Database();
    }

    public PersonResult person(String authtoken) throws DataAccessException {
        PersonResult personResult = new PersonResult();

        try{
            database.openConnection();
            PersonDao personDao = database.getpersonDao();
            AuthTokenDao authTokenDao = database.getauthTokenDao();


            if(authTokenDao.validate(authtoken)){
                AuthToken auth = authTokenDao.getAuthToken(authtoken);
                personResult.setData(personDao.getAllPerson(auth.getUsername()));
            }

            database.closeConnection(true);
            personResult.setSuccess(true);

        } catch (DataAccessException e){
            personResult.setSuccess(false);
            personResult.setMessage("{\"message\" : \" internal server error\"}");
            personResult.setMessage(e.getMessage());
            database.closeConnection(false);
        }

        return personResult;
    }
}
