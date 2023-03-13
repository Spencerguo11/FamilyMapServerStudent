package Model;

import Request.RegisterRequest;

import java.util.Objects;

/**
 * A class for person
 */
public class Person {
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



    // constructor

    /**
     *
     * @param associatedUsernameInput Input of the associated username
     * @param personIDInput Input of the person ID
     * @param firstNameInput Input of the first name
     * @param lastNameInput Input of the last name
     * @param genderInput Input the gender
     * @param fatherIDInput Input of the father ID
     * @param motherIDInput Input of the mother ID
     * @param spouseIDInput Input of the spouse ID
     */
    public Person(String associatedUsernameInput, String personIDInput, String firstNameInput, String lastNameInput,
                  String genderInput, String fatherIDInput, String motherIDInput, String spouseIDInput){
        associatedUsername = associatedUsernameInput;
        personID = personIDInput;
        firstName = firstNameInput;
        lastName = lastNameInput;
        gender = genderInput;
        fatherID = fatherIDInput;
        motherID = motherIDInput;
        spouseID = spouseIDInput;
    }

    public Person(){}

    public Person(User user) {
        this.associatedUsername = user.getUsername();
        this.personID = user.getPersonID();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.gender = user.getGender();
        this.fatherID = new String();
        this.motherID = new String();
        this.spouseID = new String();
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


    /**
     * Equals function
     * @param o An object input
     * @return A boolean result
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(associatedUsername, person.associatedUsername) && Objects.equals(personID, person.personID) && Objects.equals(firstName, person.firstName) && Objects.equals(lastName, person.lastName) && Objects.equals(gender, person.gender) && Objects.equals(fatherID, person.fatherID) && Objects.equals(motherID, person.motherID) && Objects.equals(spouseID, person.spouseID);
    }


}
