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
            PersonDao pDao = db.getpDao();
            AuthTokenDao aDao = db.getaDao();


            if(aDao.validAuthToken(authtoken)){
                AuthToken auth = aDao.getAuthToken(authtoken);
                personResult.setData(pDao.selectAllPersons(auth.getUsername()));
            }

            db.closeConnection(true);
            personResult.setSuccess(true);

        } catch (Database.DatabaseException e){
            personResult.setSuccess(false);
            personResult.setMessage("{\"message\" : \" internal server error\"}");
            personResult.setMessage(e.getMessage());
            try{
                db.closeConnection(false);
            }catch (Database.DatabaseException d){
                personResult.setSuccess(false);
                personResult.setMessage("{\"message\" : \" internal server error\"}");
                personResult.setMessage(d.getMessage());
            }
        }

        return personResult;
    }
}
