package dataAccessAndServiceTest;

import dataaccess.*;
import org.junit.*;
import model.*;
import result.PersonResult;
import service.PersonService;

import static org.junit.Assert.*;

public class PersonServiceTest {
    private PersonService personService;

    @Before
    public void setUp() {
        personService = new PersonService();
        Database db = new Database();
        try {
            db.openConnection();
            db.clearAll();
            db.closeConnection(true);
            db = null;
        } catch (DataAccessException e){
            assertEquals("throwing excpetions is bad", e.getMessage());
        }
    }

    @After
    public void tearDown() {
        personService = null;
    }

    @Test
    public void testPersonPositive() throws DataAccessException {
        User testUser1 = new User(); //person 1
        testUser1.setUsername("Spencer");
        testUser1.setPassword("Spencer21");
        testUser1.setEmail("spencer11@gmail.com");
        testUser1.setFirstName("Jin");
        testUser1.setLastName("chen");
        testUser1.setGender("f");
        testUser1.setPersonID("Spencer21312");

        Person testP = new Person(testUser1);
        testP.setSpouseID("Jane");
        testP.setMotherID("Sylvia");
        testP.setFatherID("Ryan");

        User testUser2 = new User(); //person 2
        testUser2.setUsername("Kyle");
        testUser2.setPassword("knkakfag");
        testUser2.setEmail("Kyle23@gmail.com");
        testUser2.setFirstName("Kyle");
        testUser2.setLastName("Seong");
        testUser2.setGender("f");
        testUser2.setPersonID("buabfiag");

        Person testP1 = new Person(testUser2);
        testP1.setSpouseID("Spencer");
        testP1.setFatherID("Scott");
        testP1.setMotherID("Rebecca");

        Person testPerson3 = new Person(); //person 3
        testPerson3.setAssociatedUsername("Spencer");
        testPerson3.setPersonID("Ryan");
        testPerson3.setFatherID("Bob");
        testPerson3.setSpouseID("Sarah");
        testPerson3.setMotherID("Noelle");
        testPerson3.setFirstName("Josh");
        testPerson3.setLastName("Wang");
        testPerson3.setGender("m");

        AuthToken testAuth1 = new AuthToken();
        testAuth1.setAuthtoken("auth123");

        testAuth1.setUsername("Spencer");

        AuthToken testAuth2 = new AuthToken();
        testAuth2.setAuthtoken("bauth");

        testAuth2.setUsername("Kyle");

        try{
            Database db = new Database();
            db.openConnection();
            db.clearAll();
            UserDao uDao = db.getuserDao();
            uDao.insert(testUser1);
            uDao.insert(testUser2);
            PersonDao pDao = db.getpersonDao();
            pDao.insert(testP);
            pDao.insert(testP1);
            pDao.insert(testPerson3);
            AuthTokenDao aDao = db.getauthTokenDao();
            aDao.insert(testAuth1);
            aDao.insert(testAuth2);
            db.closeConnection(true);
        } catch (DataAccessException e){
            assertEquals("Throwsing exceptions is bad", e.getMessage());
        }
        Person[] inputPersons = new Person[2];
        inputPersons[0] = testP;
        inputPersons[1] = testPerson3;

        PersonResult expectedResponse = new PersonResult();
        expectedResponse.setData(inputPersons);
        expectedResponse.setSuccess(true);

        PersonResult outputResponse = personService.person(testAuth1.getAuthtoken());

        assertEquals(expectedResponse, outputResponse);

        PersonResult badExpectedResponse = new PersonResult();
        badExpectedResponse.setSuccess(false);
        badExpectedResponse.setMessage("Nonexistent Authtoken");

        PersonResult badOutputResponse = personService.person("bogus");

        assertNotEquals(badExpectedResponse, badOutputResponse);
    }


    @Test
    public void testPersonNegative() throws DataAccessException {
        User testUser1 = new User(); //person 1
        testUser1.setPassword("Spencer21");
        testUser1.setEmail("spencer11@gmail.com");
        testUser1.setFirstName("Jin");
        testUser1.setLastName("chen");
        testUser1.setGender("f");
        testUser1.setPersonID("Spencer21312");

        Person testP = new Person(testUser1);
        testP.setSpouseID("Jane");
        testP.setMotherID("Sylvia");
        testP.setFatherID("Ryan");

        User testUser2 = new User(); //person 2
        testUser2.setPassword("knkakfag");
        testUser2.setEmail("Kyle23@gmail.com");
        testUser2.setFirstName("Kyle");
        testUser2.setLastName("Song");
        testUser2.setGender("f");
        testUser2.setPersonID("buabfiag");

        Person testP1 = new Person(testUser2);
        testP1.setSpouseID("Spencer");
        testP1.setFatherID("Scott");
        testP1.setMotherID("Rebecca");

        Person testPerson3 = new Person(); //person 3
        testPerson3.setAssociatedUsername("Spencer");
        testPerson3.setPersonID("Ryan");
        testPerson3.setFatherID("Bob");
        testPerson3.setSpouseID("Sarah");
        testPerson3.setMotherID("Noelle");
        testPerson3.setFirstName("Josh");
        testPerson3.setLastName("Wang");
        testPerson3.setGender("m");

        AuthToken testAuth1 = new AuthToken();
        testAuth1.setAuthtoken("auth123");

        testAuth1.setUsername("Spencer");

        AuthToken testAuth2 = new AuthToken();
        testAuth2.setAuthtoken("bauth");

        testAuth2.setUsername("Kyle");

        try{
            Database db = new Database();
            db.openConnection();
            db.clearAll();
            UserDao uDao = db.getuserDao();
            uDao.insert(testUser1);
            uDao.insert(testUser2);
            PersonDao pDao = db.getpersonDao();
            pDao.insert(testP);
            pDao.insert(testP1);
            pDao.insert(testPerson3);
            AuthTokenDao aDao = db.getauthTokenDao();
            aDao.insert(testAuth1);
            aDao.insert(testAuth2);
            db.closeConnection(true);
        } catch (DataAccessException e){
            assertEquals("error inserting user", e.getMessage());
        }
        Person[] inputPersons = new Person[2];
        inputPersons[0] = testP;
        inputPersons[1] = testPerson3;

        PersonResult expectedResponse = new PersonResult();
        expectedResponse.setData(inputPersons);
        expectedResponse.setSuccess(true);

        PersonResult outputResponse = personService.person(testAuth1.getAuthtoken());

        assertNotEquals(expectedResponse, outputResponse);

        PersonResult badExpectedResponse = new PersonResult();
        badExpectedResponse.setSuccess(false);
        badExpectedResponse.setMessage("Nonexistent Authtoken");

        PersonResult badOutputResponse = personService.person("bogus");

        assertNotEquals(badExpectedResponse, badOutputResponse);
    }


}