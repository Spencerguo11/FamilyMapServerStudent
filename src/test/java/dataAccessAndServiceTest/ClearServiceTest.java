package dataAccessAndServiceTest;

import dataaccess.*;
import model.*;
import org.junit.*;
import result.ClearResult;
import service.ClearService;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ClearServiceTest {
    private ClearService clearService;

    @Before
    public void setUp() throws DataAccessException {
        clearService = new ClearService();
    }

    @After
    public void tearDown() throws DataAccessException {
        clearService = null;
    }

    @Test
    public void testClearServicePositive(){
        try{
            User testUser1 = new User();
            testUser1.setUsername("iaifdin");
            testUser1.setPassword("njninc");
            testUser1.setEmail("iaifdin22@gmail.com");
            testUser1.setFirstName("Jacob");
            testUser1.setLastName("Lin");
            testUser1.setGender("m");
            testUser1.setPersonID("jijainfd");

            User testUser2 = new User(); //user 2
            testUser2.setUsername("nikanfia");
            testUser2.setPassword("nkqwkkji");
            testUser2.setEmail("nikanfia65@gmail.com");
            testUser2.setFirstName("Jess");
            testUser2.setLastName("Song");
            testUser2.setGender("f");
            testUser2.setPersonID("nianifd");

            Event testEvent1 = new Event();
            testEvent1.setEventID("ID1");
            testEvent1.setAssociatedUsername("iaifdin");
            testEvent1.setPersonID("jijainfd");
            testEvent1.setLatitude(-1.0f);
            testEvent1.setLongitude(1.0f);
            testEvent1.setCountry("Taiwan");
            testEvent1.setCity("TaoYuan");
            testEvent1.setEventType("Birth");
            testEvent1.setYear(2007);

            Event testEvent2 = new Event();
            testEvent2.setEventID("ID2");
            testEvent2.setAssociatedUsername("nikanfia");
            testEvent2.setPersonID("Fefe");
            testEvent2.setLatitude(99.0f);
            testEvent2.setLongitude(-99.0f);
            testEvent2.setCountry("China");
            testEvent2.setCity("Beijing");
            testEvent2.setEventType("Graduation");
            testEvent2.setYear(2013);

            Person testPerson1 = new Person(testUser1);
            testPerson1.setSpouseID("Jin");
            testPerson1.setMotherID("Joy");
            testPerson1.setFatherID("Feng");

            Person testPerson2 = new Person(testUser2);
            testPerson2.setSpouseID("Scott");
            testPerson2.setFatherID("Kyle");
            testPerson2.setMotherID("Gen");

            AuthToken testAuth1 = new AuthToken();
            testAuth1.setAuthtoken("123");

            testAuth1.setUsername("iaifdin");;

            AuthToken testAuth2 = new AuthToken();
            testAuth2.setAuthtoken("234");

            testAuth2.setUsername("nikanfia");
            Database db = new Database();
            db.openConnection();
            db.clearAll();
            UserDao uDao = db.getuserDao();
            PersonDao pDao = db.getpersonDao();
            EventDao eDao = db.geteventDao();
            AuthTokenDao aDao = db.getauthTokenDao();

            uDao.insert(testUser1);
            uDao.insert(testUser2);
            pDao.insert(testPerson1);
            pDao.insert(testPerson2);
            eDao.insert(testEvent1);
            eDao.insert(testEvent2);
            aDao.insert(testAuth1);
            aDao.insert(testAuth2);
            db.closeConnection(true);

            ClearResult expectedResponse = new ClearResult();
            expectedResponse.setMessage("Clear succeeded");

            ClearResult output = clearService.clear();

            assertEquals(expectedResponse.getMessage(), output.getMessage());

            Database db2 = new Database();
            db.openConnection();
            db.clearAll();
            UserDao uDao2 = db.getuserDao();
            PersonDao pDao2 = db.getpersonDao();
            EventDao eDao2 = db.geteventDao();
            AuthTokenDao aDao2 = db.getauthTokenDao();


            db.closeConnection(true);


        } catch (DataAccessException e){
            assertEquals("error resetting Authtoken", e.getMessage());
        }


    }


    @Test
    public void testClearServicePositive2(){
        try{
            User testUser1 = new User();
            testUser1.setUsername("iaifdin");
            testUser1.setPassword("njninc");
            testUser1.setEmail("iaifdin22@gmail.com");
            testUser1.setFirstName("Jacob");
            testUser1.setLastName("Lin");
            testUser1.setGender("m");
            testUser1.setPersonID("jijainfd");

            User testUser2 = new User(); //user 2
            testUser2.setUsername("nikanfia");
            testUser2.setPassword("nkqwkkji");
            testUser2.setEmail("nikanfia65@gmail.com");
            testUser2.setFirstName("Jess");
            testUser2.setLastName("Song");
            testUser2.setGender("f");
            testUser2.setPersonID("nianifd");

            Event testEvent1 = new Event();
            testEvent1.setEventID("ID1");
            testEvent1.setAssociatedUsername("iaifdin");
            testEvent1.setPersonID("jijainfd");
            testEvent1.setLatitude(-1.0f);
            testEvent1.setLongitude(1.0f);
            testEvent1.setCountry("Taiwan");
            testEvent1.setCity("TaoYuan");
            testEvent1.setEventType("Birth");
            testEvent1.setYear(2007);

            Event testEvent2 = new Event();
            testEvent2.setEventID("ID2");
            testEvent2.setAssociatedUsername("nikanfia");
            testEvent2.setPersonID("Fefe");
            testEvent2.setLatitude(99.0f);
            testEvent2.setLongitude(-99.0f);
            testEvent2.setCountry("China");
            testEvent2.setCity("Beijing");
            testEvent2.setEventType("Graduation");
            testEvent2.setYear(2013);

            Person testPerson1 = new Person(testUser1);
            testPerson1.setSpouseID("Jin");
            testPerson1.setMotherID("Joy");
            testPerson1.setFatherID("Feng");

            Person testPerson2 = new Person(testUser2);
            testPerson2.setSpouseID("Scott");
            testPerson2.setFatherID("Kyle");
            testPerson2.setMotherID("Gen");

            AuthToken testAuth1 = new AuthToken();
            testAuth1.setAuthtoken("123");

            testAuth1.setUsername("iaifdin");;

            AuthToken testAuth2 = new AuthToken();
            testAuth2.setAuthtoken("234");

            testAuth2.setUsername("nikanfia");
            Database db = new Database();
            db.openConnection();
            db.clearAll();
            UserDao uDao = db.getuserDao();
            PersonDao pDao = db.getpersonDao();
            EventDao eDao = db.geteventDao();
            AuthTokenDao aDao = db.getauthTokenDao();

            uDao.insert(testUser1);
            uDao.insert(testUser2);
            pDao.insert(testPerson1);
            pDao.insert(testPerson2);
            eDao.insert(testEvent1);
            eDao.insert(testEvent2);
            aDao.insert(testAuth1);
            aDao.insert(testAuth2);
            db.closeConnection(true);

            ClearResult expectedResponse = new ClearResult();
            expectedResponse.setMessage("Clear succeeded");

            ClearResult output = clearService.clear();

            assertEquals(expectedResponse.getMessage(), output.getMessage());

            Database db2 = new Database();
            db.openConnection();
            db.clearAll();
            UserDao uDao2 = db.getuserDao();
            PersonDao pDao2 = db.getpersonDao();
            EventDao eDao2 = db.geteventDao();
            AuthTokenDao aDao2 = db.getauthTokenDao();


            db.closeConnection(true);


        } catch (DataAccessException e){
            assertEquals("error resetting Authtoken", e.getMessage());
        }


    }

}