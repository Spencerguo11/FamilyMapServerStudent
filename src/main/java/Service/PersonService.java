package Service;


import Model.Person;

import Result.PersonResult;
import dao.AuthtokenDAO;
import dao.DataAccessException;
import dao.Database;
import dao.PersonDAO;

import java.sql.Connection;
import java.util.List;

/**
 * A class to handle getting a person information from database
 */
public class PersonService {
    private Database db ;


    public PersonService(){db = new Database();}
    /**
     * Get all person's information
     * @param authtoken An string of token
     * @return An PersonResult object
     */
    public PersonResult getAllPersonInfo(String authtoken) throws DataAccessException {

        PersonResult personResult = new PersonResult();

        try{
            db.openConnection();

            PersonDAO personDAO = db.getPersonDAO();
            AuthtokenDAO authtokenDAO = db.getAuthtokenDAO();

            String username = authtokenDAO.findUsernameByAuthToken(authtoken);
            List<Person> persons = personDAO.listAllPeopleByUsername(username);

            personResult = new PersonResult(persons);

            personResult.setSuccess(true);
            db.closeConnection(true);

        } catch (DataAccessException dataAccessException){
            personResult.setMessage(dataAccessException.getMessage());
            personResult.setSuccess(false);
        }

        return personResult;

    }

    /**
     * Find a person with a person ID
     * @param personID a personID
     * @param authtoken An string of token
     * @return An personResult object
     */
    public PersonResult getAPersonbyPersonID(String personID, String authtoken) throws DataAccessException{
        db.openConnection();

        PersonResult personResult = new PersonResult();

        try{
            db.openConnection();
            PersonDAO personDAO = db.getPersonDAO();
            AuthtokenDAO authtokenDAO = db.getAuthtokenDAO();

            String username = authtokenDAO.findUsernameByAuthToken(authtoken);
            List<Person> persons = personDAO.listAllPeopleByUsername(username);
            Person targetPerson = null;
            for(int i = 0; i < persons.size(); i++){
                if(persons.get(i).getPersonID().equals(personID)){
                    targetPerson = persons.get(i);
                }
            }

            if(targetPerson != null) {
                personResult = new PersonResult(targetPerson, true);
            } else{
                personResult.setMessage("Cannot find this person");
                personResult.setSuccess(false);
            }

            db.closeConnection(true);

        } catch (DataAccessException dataAccessException){
            personResult.setMessage(dataAccessException.getMessage());
            personResult.setSuccess(false);
        }

        return personResult;
    }

    public boolean validateToken(String authToken) {
        try {
            db.openConnection();
            AuthtokenDAO authtokenDAO = db.getAuthtokenDAO();
            return authtokenDAO.validate(authToken);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
