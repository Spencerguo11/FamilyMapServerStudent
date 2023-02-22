package Request;

/**
 * A class for creating a register request
 */
public class RegisterRequest {
    /**
     * the username of a register request
     */
    private String username; // the username of a register request
    /**
     * the password of a register request
     */
    private String password; // the password of a register request
    /**
     * the email of a register request
     */
    private String email; // the email of a register request
    /**
     * the first name of a register request
     */
    private String firstName; // the first name of a register request
    /**
     * the last name of a register request
     */
    private String lastName; // the last name of a register request
    /**
     * the gender of a register request
     */
    private String gender; // the gender of a register request

    // constructor

    /**
     * RegisterRequest constructor
     * @param usernameInput Input of username
     * @param passwordInput Input of password
     * @param emailInput Input of email
     * @param firstNameInput Input of first name
     * @param lastNameInput Input of last name
     * @param genderInput Input of gender
     */
    public RegisterRequest(String usernameInput, String passwordInput, String emailInput,
                           String firstNameInput, String lastNameInput, String genderInput){
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
}
