package Service;

import Model.Authtoken;
import Model.User;
import Request.LoginRequest;
import Result.LoginResult;
import dao.AuthtokenDAO;
import dao.DataAccessException;
import dao.Database;
import dao.UserDAO;

import java.sql.Connection;

/**
 * A service to handle logging a user in
 */
public class LoginService {
    private Database db ;

    public LoginService() {
        db = new Database();
    }

    /**
     * log the user in
     * @param request pass in a Login request
     * @return a Login Result
     */
    public LoginResult login(LoginRequest request) throws DataAccessException {


        LoginResult loginResult = new LoginResult();

        try {
            db.openConnection();
            UserDAO userDAO = db.getUserDAO();
            AuthtokenDAO authtokenDAO = db.getAuthtokenDAO();
            User user = new User(request);
            //check if db
            if (userDAO.ExistingUsernamePassword(user)) {
                Authtoken authtoken = new Authtoken(user);
                // need to figure out how to add personID
                String personID = userDAO.getPersonIDOfUser(user);
//                authtoken.setPersonID(personID);
                authtokenDAO.insert(authtoken);
                loginResult = new LoginResult(authtoken, personID);
                loginResult.setSuccess(true);
                db.closeConnection(true);
            }

        } catch (DataAccessException dataAccessException) {
            loginResult.setMessage(dataAccessException.getMessage());
            loginResult.setSuccess(false);

            // close db
            try {
                db.closeConnection(true);
            } catch (Exception e) {
                loginResult.setMessage(e.getMessage());
                loginResult.setSuccess(false);
                db.closeConnection(false);
            }
        }
        return loginResult;
    }
}
