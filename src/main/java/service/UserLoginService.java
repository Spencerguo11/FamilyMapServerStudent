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

    private Database database;

    public UserLoginService () {database = new Database();}

    public LoginResult login(LoginRequest input) throws DataAccessException {
        LoginResult loginResult = new LoginResult();
        UserDao userDao = database.getuserDao();
        AuthTokenDao authTokenDao = database.getauthTokenDao();

        try{
            database.openConnection();
            User user = new User(input);

            if (userDao.validateUsernamePassword(user)) {
                AuthToken returnAuth = new AuthToken(user);
                authTokenDao.insert(returnAuth);
                loginResult = new LoginResult(returnAuth, userDao.getPersonIDOfUser(user));
                loginResult.setSuccess(true);
                database.closeConnection(true);
            }

        } catch ( DataAccessException d){
            loginResult.setMessage(d.getMessage());
            loginResult.setSuccess(false);
            database.closeConnection(false);

        }
        return loginResult;
    }

}
