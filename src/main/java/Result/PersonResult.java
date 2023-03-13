package Result;
import Model.Person;

import java.util.List;

/**
 * A class to handle person information results
 */
public class PersonResult {
    /**
     * A list of person objects
     */
    private List<Person> persons;
    /**
     * A message to return
     */
    private String message;
    /**
     * A boolean variable
     */
    private boolean success;


    /**
     * the username that is associated with a person
     */
    private String associatedUsername; // the username that is associated with a person

    /**
     * the id number of a person
     */
    private String personID; // the id number of a person

    /**
     * the first name of a person
     */
    private String firstName; //the first name of a person

    /**
     * the last name of a person
     */
    private String lastName;//the last name of a person

    /**
     * the gender of a person
     */
    private String gender; //the gender of a person

    /**
     * the id number of the father of a person
     */
    private String fatherID; //the id number of the father of a person

    /**
     * the id number of the mother of a person
     */
    private String motherID; //the id number of the mother of a person

    /**
     * the id number of the spouse of a person
     */
    private String spouseID; //the id number of the spouse of a person




    /**
     * PersonResult constructor
     * @param personsInput Input of a list of persons
     * @param messageInput Input of a message
     * @param successsInput A boolean input
     */
    // constructor
    public PersonResult(List<Person> personsInput, String messageInput, boolean successsInput){
        persons = personsInput;
        message = messageInput;
        success = successsInput;
    }

    /**
     * Another personResult constructor
     * @param associatedUsernameInput Input of the associated username
     * @param personIDInput Input of the person ID
     * @param firstNameInput Input of the first name
     * @param lastNameInput Input of the last name
     * @param genderInput Input the gender
     * @param fatherIDInput Input of the father ID
     * @param motherIDInput Input of the mother ID
     * @param spouseIDInput Input of the spouse ID
     * @param successInput a boolean input
     */
    public PersonResult(String associatedUsernameInput, String personIDInput, String firstNameInput, String lastNameInput,
                        String genderInput, String fatherIDInput, String motherIDInput, String spouseIDInput, boolean successInput){
        associatedUsername = associatedUsernameInput;
        personID = personIDInput;
        firstName = firstNameInput;
        lastName = lastNameInput;
        gender = genderInput;
        fatherID = fatherIDInput;
        motherID = motherIDInput;
        spouseID = spouseIDInput;
        success = successInput;
    }

    public PersonResult(){}


    public PersonResult(List<Person> personsInput){
        persons = personsInput;
    }

    public PersonResult(Person person, boolean successInput){
        associatedUsername = person.getAssociatedUsername();
        personID = person.getPersonID();
        firstName = person.getFirstName();
        lastName = person.getLastName();
        gender = person.getGender();
        fatherID = person.getFatherID();
        motherID = person.getMotherID();
        spouseID = person.getSpouseID();
        success = successInput;
    }


    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFatherID() {
        return fatherID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }


    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public String getMessage() {return message;}

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

}
