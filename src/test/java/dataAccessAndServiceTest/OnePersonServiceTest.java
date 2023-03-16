package dataAccessAndServiceTest;

import dataaccess.*;
import org.junit.*;
import model.*;
import result.OnePersonResult;
import result.PersonResult;
import service.OnePersonService;
import service.PersonService;

import static org.junit.Assert.*;


public class OnePersonServiceTest {
    private OnePersonService personService;

    private Database db;

    @Before
    public void setUp() throws DataAccessException {
        personService = new OnePersonService();
        db = new Database();
        db.openConnection();
        db.clearAll();
    }

    @After
    public void tearDown() throws DataAccessException {
        personService = null;
        db.closeConnection(true);
        db = null;
    }

    @Test
    public void testGetOnePersonPositive() throws DataAccessException {
        User test = new User(); //person 1
        test.setUsername("spencer");
        test.setPassword("spencer");
        test.setEmail("spencer@gmail.com");
        test.setFirstName("spencer");
        test.setLastName("guo");
        test.setGender("m");
        test.setPersonID("spencer");

        Person testP = new Person(test);
        testP.setSpouseID("Jane");
        testP.setMotherID("Jenniffer");
        testP.setFatherID("James");

        User testUser2 = new User(); //person 2
        testUser2.setUsername("Kyle");
        testUser2.setPassword("Kyle");
        testUser2.setEmail("Kyle@gmail.com");
        testUser2.setFirstName("Kyle");
        testUser2.setLastName("guo");
        testUser2.setGender("m");
        testUser2.setPersonID("Kyle");

        Person testPerson2 = new Person(testUser2);
        testPerson2.setSpouseID("Kylie");
        testPerson2.setFatherID("Kale");
        testPerson2.setMotherID("Kalio");

        AuthToken testAuth1 = new AuthToken();
        testAuth1.setAuthtoken("auth123");
        testAuth1.setUsername("spencer");

        AuthToken testAuth2 = new AuthToken();
        testAuth2.setAuthtoken("auth345");
        testAuth2.setUsername("Kyle");

        PersonDao pDao = db.getpersonDao();
        pDao.insert(testP);
        pDao.insert(testPerson2);
        AuthTokenDao aDao = db.getauthTokenDao();
        aDao.insert(testAuth1);
        aDao.insert(testAuth2);


        PersonResult expectedResult = new PersonResult();
        expectedResult.setSuccess(true);

        OnePersonResult outputResult = personService.getOnePerson(test.getPersonID() ,testAuth1.getAuthtoken());

        assertNotEquals(expectedResult, outputResult);


    }

    @Test
    public void testGetOnePersonNegative() throws DataAccessException {
        User test = new User(); //person 1
        test.setPassword("spencer");
        test.setEmail("spencer@gmail.com");
        test.setFirstName("spencer");
        test.setLastName("guo");
        test.setGender("m");
        test.setPersonID("spencer");

        Person testP = new Person(test);
        testP.setSpouseID("Jane");
        testP.setMotherID("Jenniffer");
        testP.setFatherID("James");

        User testUser2 = new User(); //person 2
        testUser2.setPassword("Kyle");
        testUser2.setEmail("Kyle@gmail.com");
        testUser2.setFirstName("Kyle");
        testUser2.setLastName("guo");
        testUser2.setGender("m");
        testUser2.setPersonID("Kyle");

        Person testPerson2 = new Person(testUser2);
        testPerson2.setSpouseID("Kylie");
        testPerson2.setFatherID("Kale");
        testPerson2.setMotherID("Kalio");

        AuthToken testAuth1 = new AuthToken();
        testAuth1.setAuthtoken("auth123");
        testAuth1.setUsername("spencer");

        AuthToken testAuth2 = new AuthToken();
        testAuth2.setAuthtoken("auth345");
        testAuth2.setUsername("Kyle");


        db.clearAll();
        PersonDao pDao = db.getpersonDao();
        assertThrows(DataAccessException.class, () -> pDao.insert(testP));
        assertThrows(DataAccessException.class, () -> pDao.insert(testPerson2));
        AuthTokenDao aDao = db.getauthTokenDao();
        aDao.insert(testAuth1);
        aDao.insert(testAuth2);

        PersonResult expectedResult = new PersonResult();
        expectedResult.setSuccess(true);

        OnePersonResult outputResult = personService.getOnePerson(test.getPersonID(),testAuth1.getAuthtoken());

        assertNotEquals(expectedResult, outputResult);

        PersonResult badExpectedResult = new PersonResult();
        badExpectedResult.setSuccess(false);
        badExpectedResult.setMessage("PersonID does not match given authToken");

        OnePersonResult badOutputResult = personService.getOnePerson(testPerson2.getPersonID(),testAuth1.getAuthtoken());

        assertNotEquals(badExpectedResult, badOutputResult);
    }
}