package service;

import dataaccess.*;
import model.*;
import result.OnePersonResult;

public class OnePersonService {
    private Database database;

    public OnePersonService() {
        database = new Database();
    }

    public OnePersonResult getOnePerson(String personID, String authtoken) throws DataAccessException {
        OnePersonResult onePersonResult = new OnePersonResult();

        try{
            database.openConnection();
            PersonDao personDao = database.getpersonDao();
            AuthTokenDao authTokenDao = database.getauthTokenDao();
            UserDao userDao = database.getuserDao();


            if(authTokenDao.validate(authtoken)){
                AuthToken auth = authTokenDao.getAuthToken(authtoken);
                User user = userDao.getUser(auth.getUsername());

                if (!personID.equals(userDao.getPersonIDOfUser(user))){
                    throw new DataAccessException("{\"message\" : \" internal server error\"}");
                }

                Person out = personDao.selectSinglePerson(personID);
                onePersonResult = new OnePersonResult(out);


            }

            onePersonResult.setSuccess(true);
            database.closeConnection(true);

        } catch (DataAccessException e){
            onePersonResult.setSuccess(false);
            onePersonResult.setMessage(e.getMessage());
            database.closeConnection(false);
        }
        return onePersonResult;
    }
}
