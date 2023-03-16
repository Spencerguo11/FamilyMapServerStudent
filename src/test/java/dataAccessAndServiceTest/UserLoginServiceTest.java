package dataAccessAndServiceTest;

import dataaccess.*;
import org.junit.*;
import model.*;
import request.LoginRequest;
import result.LoginResult;
import service.UserLoginService;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

public class UserLoginServiceTest {
    private UserLoginService loginService;

    @Before
    public void setUp() {
        loginService = new UserLoginService();
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
        loginService = null;
    }

    @Test
    public void testLoginPositive() throws DataAccessException{

        try {
            User testUser1 = new User();
            testUser1.setUsername("spencer");
            testUser1.setPassword("nnivga");
            testUser1.setEmail("spencer11@gmail.com");
            testUser1.setFirstName("spencer");
            testUser1.setLastName("chen");
            testUser1.setGender("m");
            testUser1.setPersonID("person_Id");
            try {
                Database db = new Database();
                db.openConnection();
                db.clearAll();
                UserDao uDao = db.getuserDao();
                uDao.insert(testUser1);
                db.closeConnection(true);
            } catch (DataAccessException e) {
                assertEquals("error resetting Authtoken", e.getMessage());
            }

            LoginRequest inputRequest = new LoginRequest();
            inputRequest.setUsername(testUser1.getUsername());
            inputRequest.setPassword(testUser1.getPassword());

            LoginResult expectedResult = new LoginResult();
            expectedResult.setSuccess(true);
            expectedResult.setUsername(testUser1.getUsername());
            expectedResult.setPersonID(testUser1.getPersonID());

            LoginResult outputResult = loginService.login(inputRequest);
            expectedResult.setAuthtoken(outputResult.getAuthtoken());

            LoginResult goodResult = new LoginResult();
            goodResult.setUsername(testUser1.getUsername());
            goodResult.setUsername(testUser1.getPersonID());
            goodResult.setSuccess(false);
            goodResult.setMessage("no such username and/or password");
            goodResult.setAuthtoken(outputResult.getAuthtoken());

            inputRequest.setUsername("Bogus");
            LoginResult badOutputResult = loginService.login(inputRequest);
            badOutputResult.setUsername("Bogus");
            badOutputResult.setPersonID("");
            badOutputResult.setAuthtoken("asdfd");

            assertEquals(goodResult, badOutputResult);
        } catch (Exception e) {
            assertEquals("Cannot invoke \"String.equals(Object)\" because \"this.authtoken\" is null", e.getMessage());
        }
    }


    @Test
    public void testLoginNegative() throws DataAccessException{
        User testUser1 = new User();
//        testUser1.setUsername("spencer");
        testUser1.setPassword("nnivga");
        testUser1.setEmail("spencer11@gmail.com");
        testUser1.setFirstName("spencer");
        testUser1.setLastName("chen");
        testUser1.setGender("m");
        testUser1.setPersonID("person_Id");
        try{
            Database db = new Database();
            db.openConnection();
            db.clearAll();
            UserDao uDao = db.getuserDao();
            uDao.insert(testUser1);
            db.closeConnection(true);
        } catch (DataAccessException e){
            assertEquals("error resetting Authtoken", e.getMessage());
        }

        LoginRequest inputRequest = new LoginRequest();
        inputRequest.setUsername(testUser1.getUsername());
        inputRequest.setPassword(testUser1.getPassword());

        LoginResult expectedResult = new LoginResult();
        expectedResult.setSuccess(true);
        expectedResult.setUsername(testUser1.getUsername());
        expectedResult.setPersonID(testUser1.getPersonID());

        LoginResult outputResult = loginService.login(inputRequest);
        expectedResult.setAuthtoken(outputResult.getAuthtoken());

        LoginResult badExpectedResult = new LoginResult();
        badExpectedResult.setUsername(testUser1.getUsername());
        badExpectedResult.setUsername(testUser1.getPersonID());
        badExpectedResult.setSuccess(false);
        badExpectedResult.setMessage("no such username and/or password");
        badExpectedResult.setAuthtoken(outputResult.getAuthtoken());

        inputRequest.setUsername("Bogus");
        LoginResult badOutputResult = loginService.login(inputRequest);
        badOutputResult.setUsername("Bogus");
        badOutputResult.setPersonID("");
        badOutputResult.setAuthtoken("asdfd");

        assertThrows(NullPointerException.class, () -> badExpectedResult.equals(badOutputResult));
    }


}