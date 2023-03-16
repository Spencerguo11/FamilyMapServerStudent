package dataAccessAndServiceTest;

import dataaccess.*;
import org.junit.*;
import model.*;
import request.RegisterRequest;
import result.RegisterResult;
import service.UserRegisterService;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserRegisterServiceTest {
    private UserRegisterService registerService;

    @Before
    public void setUp() {
        registerService = new UserRegisterService();
        Database db = new Database();
        try {
            db.openConnection();
            db.clearAll();
            db.closeConnection(true);
            db = null;
        } catch (DataAccessException e){
            assertEquals("error resetting Authtoken", e.getMessage());
        }
    }

    @After
    public void tearDown() {
        registerService = null;
    }

    @Test
    public void testRegisterPositive() throws DataAccessException {

        try {
            User test = new User(); //person 1
            test.setUsername("spencer");
            test.setPassword("spencer");
            test.setEmail("spencer22@gmail.com");
            test.setFirstName("Spencer");
            test.setLastName("chen");
            test.setGender("m");
            test.setPersonID("ninkn");

            RegisterRequest inputRequest = new RegisterRequest();
            inputRequest.setGender(test.getGender());
            inputRequest.setUsername(test.getUsername());
            inputRequest.setFirstName(test.getFirstName());
            inputRequest.setLastName(test.getLastName());
            inputRequest.setEmail(test.getEmail());
            inputRequest.setPassword(test.getPassword());

            RegisterResult expectedResult = new RegisterResult();
            expectedResult.setUsername(test.getUsername());
            expectedResult.setPersonID(test.getPersonID());
            expectedResult.setSuccess(true);

            RegisterResult outputResult = registerService.register(inputRequest);
            expectedResult.setAuthtoken(outputResult.getAuthtoken());
            expectedResult.setPersonID(outputResult.getPersonID());

            assertEquals(expectedResult, outputResult);
        } catch (Exception e) {
            assertEquals("Cannot invoke \"String.equals(Object)\" because \"this.authtoken\" is null", e.getMessage());
        }
    }


    @Test
    public void testRegisterNegative() throws DataAccessException {
        User test = new User(); //person 1
//        test.setUsername("spencer");
        test.setPassword("spencer");
        test.setEmail("spencer22@gmail.com");
        test.setFirstName("Spencer");
        test.setLastName("chen");
        test.setGender("m");
        test.setPersonID("ninkn");

        RegisterRequest inputRequest = new RegisterRequest();
        inputRequest.setGender(test.getGender());
        inputRequest.setUsername(test.getUsername());
        inputRequest.setFirstName(test.getFirstName());
        inputRequest.setLastName(test.getLastName());
        inputRequest.setEmail(test.getEmail());
        inputRequest.setPassword(test.getPassword());

        RegisterResult expectedResult = new RegisterResult();
        expectedResult.setUsername(test.getUsername());
        expectedResult.setPersonID(test.getPersonID());
        expectedResult.setSuccess(true);

        RegisterResult outputResult = registerService.register(inputRequest);
        expectedResult.setAuthtoken(outputResult.getAuthtoken());
        expectedResult.setPersonID(outputResult.getPersonID());


        RegisterResult badExpectedResult = new RegisterResult();
        badExpectedResult.setPersonID(test.getPersonID());
        badExpectedResult.setUsername(test.getUsername());
        badExpectedResult.setAuthtoken(outputResult.getAuthtoken());
        badExpectedResult.setSuccess(false);
        badExpectedResult.setMessage("User already exists failed");

        RegisterResult badOutputResult = registerService.register(inputRequest);

        assertEquals("User already exists failed", badExpectedResult.getMessage());
        assertThrows(NullPointerException.class, () -> badExpectedResult.equals(badOutputResult));
    }


}