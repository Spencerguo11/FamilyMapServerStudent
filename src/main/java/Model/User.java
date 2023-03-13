package Model;

import Request.LoginRequest;
import Request.RegisterRequest;

import java.util.Objects;
import java.util.UUID;

/**
 * A class for user
 */
public class User {
    /**
     * the username that is associated with a user
     */
    private String username; // the username that is associated with a user

    /**
     * the password that is associated with a user
     */
    private String password; // the password that is associated with a user

    /**
     * the email that is associated with a user
     */
    private String email; // the email that is associated with a user

    /**
     * the first name that is associated with a user
     */
    private String firstName; // the first name that is associated with a user

    /**
     * the last name that is associated with a user
     */
    private String lastName; // the last name that is associated with a user

    /**
     * the gender that is associated with a user
     */
    private String gender; // the gender that is associated with a user

    private String personID;

    // constructor

    /**
     * User constructor
     * @param usernameInput input of the username
     * @param passwordInput input of the password
     * @param emailInput input of the email
     * @param firstNameInput input of the first name
     * @param lastNameInput input of the last name
     * @param genderInput input of the gender
     */
    public User(String usernameInput, String passwordInput, String emailInput, String firstNameInput, String lastNameInput, String genderInput, String personIDInput){
        username = usernameInput;
        password = passwordInput;
        email = emailInput;
        firstName = firstNameInput;
        lastName = lastNameInput;
        gender = genderInput;
        personID = personIDInput;
    }

    public User(String usernameInput, String passwordInput){
        username = usernameInput;
        password = passwordInput;
    }

    public User(){}

    public User(RegisterRequest request) {
        this.username = request.getUsername();
        this.password = request.getPassword();
        this.email = request.getEmail();
        this.firstName = request.getFirstName();
        this.lastName = request.getLastName();
        this.gender = request.getGender();
        this.personID = UUID.randomUUID().toString();
    }

    public User(LoginRequest request) {
        this.username = request.getUsername();
        this.password = request.getPassword();
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
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
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(email, user.email) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(gender, user.gender) && Objects.equals(personID, user.personID);
    }
}
