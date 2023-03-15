package service;

import dataaccess.*;
import model.*;
import result.OnePersonResult;

public class PersonIDService {

    /**
     * current user determined by authtoken
     * @return person object with specified ID
     */
    private Database db;

    public PersonIDService() {
        db = new Database();
    }

    public OnePersonResult personID(String personID, String authtoken){
        OnePersonResult idResult = new OnePersonResult();

        try{
            db.openConnection();
            PersonDao personDao = db.getpersonDao();
            AuthTokenDao authTokenDao = db.getauthTokenDao();
            UserDao userDao = db.getuserDao();


            if(authTokenDao.validAuthToken(authtoken)){
                AuthToken auth = authTokenDao.getAuthToken(authtoken);
                User user = userDao.getUser(auth.getUsername());

                if (!personID.equals(userDao.getPersonIDOfUser(user))){
                    throw new DataAccessException("{\"message\" : \" internal server error\"}");
                }
                if (personDao.find(personID)){
                    Person out = personDao.selectSinglePerson(personID);
                    idResult = new OnePersonResult(out);
                }

            }

            idResult.setSuccess(true);
            db.closeConnection(true);
            idResult.setSuccess(true);

        } catch (DataAccessException e){
            idResult.setSuccess(false);
            idResult.setMessage(e.getMessage());
            try{
                db.closeConnection(false);
            }catch (DataAccessException d){
                idResult.setSuccess(false);
                idResult.setMessage(d.getMessage());
            }
        }
        return idResult;
    }
}
