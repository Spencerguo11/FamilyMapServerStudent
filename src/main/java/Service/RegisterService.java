package Service;

import Model.Authtoken;
import Model.Person;
import Model.User;
import Request.RegisterRequest;
import Result.LoginResult;
import Result.RegisterResult;
import dao.*;

import java.sql.Connection;
import java.util.UUID;

/**
 * A service to handle registering a new account
 */
public class RegisterService {
    private Database db ;

    public RegisterService() {
        db = new Database();
    }


    /**
     * Register a new account
     * @param request pass in a register request
     * @return a Register Result
     */
    public RegisterResult register(RegisterRequest request)throws DataAccessException {
        Connection conn = db.getConnection();

        RegisterResult registerResult = new RegisterResult();

        try {
            db.openConnection();
            UserDAO userDAO = db.getUserDAO();
            AuthtokenDAO authtokenDAO = db.getAuthtokenDAO();
            PersonDAO personDAO = db.getPersonDAO();
            EventDAO eventDAO = db.getEventDAO();

            if (userDAO.find(request.getUsername()) == null) {

                User user = new User(request);

                userDAO.insert(user);

                Person person = new Person(user);

                personDAO.insert(person);


                int birthYear = eventDAO.generateBaseEvent(person);
                // generate 4 gens
                personDAO.generateAllGenerations(person, 4, eventDAO, birthYear);

                Authtoken authtoken_obj = new Authtoken(user);
                authtokenDAO.insert(authtoken_obj);
                registerResult = new RegisterResult(authtoken_obj);
                registerResult.setSuccess(true);


            }
            db.closeConnection(true);
        } catch (DataAccessException dataAccessException){
            registerResult.setMessage(dataAccessException.getMessage());
            registerResult.setSuccess(false);
        }

//        db.closeConnection(true);
        return registerResult;
    }

}
