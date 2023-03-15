package service;

import dataaccess.AuthTokenDao;
import dataaccess.DataAccessException;
import dataaccess.Database;
import dataaccess.UserDao;
import model.AuthToken;
import model.User;
import request.LoginRequest;
import result.LoginResult;

public class UserLoginService {


    /**
     * logs returning user in using LoginRequest
     * @param input
     * @return authtoken
     */

    private Database db;

    public UserLoginService () {db = new Database();}

    public LoginResult login(LoginRequest input) {
        LoginResult loginResult = new LoginResult();
        UserDao userDao = db.getuserDao();
        AuthTokenDao authTokenDao = db.getauthTokenDao();

        try{
            db.openConnection();
            User user = new User(input);

            if (userDao.ExistingUsernamePassword(user)) { //yes, the username and password are valid
                AuthToken returnAuth = new AuthToken(user);

                authTokenDao.insert(returnAuth);

                loginResult = new LoginResult(returnAuth, userDao.getPersonIDOfUser(user));
                loginResult.setSuccess(true);

                db.closeConnection(true);
            }

        } catch ( DataAccessException d){
            loginResult.setMessage(d.getMessage());
            loginResult.setSuccess(false);
            try{
                db.closeConnection(false);
            }catch (DataAccessException e){
                loginResult.setSuccess(false);
                loginResult.setMessage(e.getMessage());
            }
        }
        return loginResult;
    }

}
