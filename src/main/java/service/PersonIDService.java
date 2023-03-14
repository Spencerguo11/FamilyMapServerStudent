package service;

import dataaccess.*;
import model.*;
import result.PersonIDResult;

public class PersonIDService {

    /**
     * current user determined by authtoken
     * @return person object with specified ID
     */
    private Database db;

    public PersonIDService() {
        db = new Database();
    }

    public PersonIDResult personID(String personID, String authtoken){
        PersonIDResult idResult = new PersonIDResult();

        try{
            db.openConnection();
            PersonDao pDao = db.getpDao();
            AuthTokenDao aDao = db.getaDao();
            UserDao userDao = db.getuDao();


            if(aDao.validAuthToken(authtoken)){
                AuthToken auth = aDao.getAuthToken(authtoken);
                User user = userDao.getUser(auth.getUsername());

                if (!personID.equals(userDao.getPersonIDOfUser(user))){
                    throw new Database.DatabaseException("{\"message\" : \" internal server error\"}");
                }
                if (pDao.find(personID)){
                    Person out = pDao.selectSinglePerson(personID);
                    idResult = new PersonIDResult(out);
                }

            }

            idResult.setSuccess(true);
            db.closeConnection(true);
            idResult.setSuccess(true);

        } catch (Database.DatabaseException e){
            idResult.setSuccess(false);
            idResult.setMessage(e.getMessage());
            try{
                db.closeConnection(false);
            }catch (Database.DatabaseException d){
                idResult.setSuccess(false);
                idResult.setMessage(d.getMessage());
            }
        }
        return idResult;
    }
}
