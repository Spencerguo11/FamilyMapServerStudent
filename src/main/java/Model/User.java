package Model;

import java.util.Objects;

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
    public User(String usernameInput, String passwordInput, String emailInput, String firstNameInput, String lastNameInput, String genderInput){
        username = usernameInput;
        password = passwordInput;
        email = emailInput;
        firstName = firstNameInput;
        lastName = lastNameInput;
        gender = genderInput;
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
        return Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(email, user.email) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(gender, user.gender);
    }
}
