package Service;

import Model.Authtoken;
import Model.Person;
import Result.PersonResult;

/**
 * A class to handle getting a person information from database
 */
public class PersonService {
    /**
     * Get all person's information
     * @param authtoken An string of token
     * @return An PersonResult object
     */
    public PersonResult getAllPersonInfo(String authtoken){
        return null;
    }

    /**
     * Find a person with a person ID
     * @param personID An ID of a person
     * @param authtoken An string of token
     * @return An personResult object
     */
    public PersonResult getAPersonbyPersonID(String personID, String authtoken){
        return null;
    }

}
