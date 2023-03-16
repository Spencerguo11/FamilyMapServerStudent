package dataAccessAndServiceTest;

import dataaccess.*;
import model.*;
import org.junit.*;
import result.OneEventResult;
import service.OneEventService;

import static org.junit.Assert.*;

public class OneEventServiceTest {
    private OneEventService oneEventService;

    @Before
    public void setUp() {
        oneEventService = new OneEventService();
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
        oneEventService = null;
    }


    @Test
    public void testOneEventPositive(){
        try{
            User testUser1 = new User();
            testUser1.setUsername("spencer");
            testUser1.setPassword("imanidiot");
            testUser1.setEmail("spencer11@gmail.com");
            testUser1.setFirstName("Jin");
            testUser1.setLastName("chen");
            testUser1.setGender("f");
            testUser1.setPersonID("yxy_angie");

            User testUser2 = new User(); //user 2
            testUser2.setUsername("joy");
            testUser2.setPassword("medumbdumb");
            testUser2.setEmail("fehahaha@gmail.com");
            testUser2.setFirstName("joy");
            testUser2.setLastName("Seng");
            testUser2.setGender("f");
            testUser2.setPersonID("blahblahblah");

            Event testEvent1 = new Event();
            testEvent1.setEventID("uniqueEventID");
            testEvent1.setAssociatedUsername("spencer");
            testEvent1.setPersonID("yxy_angie");
            testEvent1.setLatitude(-1.0f);
            testEvent1.setLongitude(1.0f);
            testEvent1.setCountry("Thailand");
            testEvent1.setCity("Bangkok");
            testEvent1.setEventType("Birth");
            testEvent1.setYear(2005);

            Event testEvent2 = new Event();
            testEvent2.setEventID("uniqueEventID2");
            testEvent2.setAssociatedUsername("joy");
            testEvent2.setPersonID("Fefe");
            testEvent2.setLatitude(99.0f);
            testEvent2.setLongitude(-99.0f);
            testEvent2.setCountry("China");
            testEvent2.setCity("ShangHai");
            testEvent2.setEventType("xiao long bao");
            testEvent2.setYear(2010);

            Event testEvent3 = new Event();
            testEvent3.setEventID("uniqueEventID3");
            testEvent3.setAssociatedUsername("spencer");
            testEvent3.setPersonID("yxy_angie");
            testEvent3.setLatitude(55.0f);
            testEvent3.setLongitude(-55.0f);
            testEvent3.setCountry("Utah");
            testEvent3.setCity("Provo");
            testEvent3.setEventType("Start Uni");
            testEvent3.setYear(2022);

            AuthToken testAuth1 = new AuthToken();
            testAuth1.setAuthtoken("auth123");

            testAuth1.setUsername("spencer");

            AuthToken testAuth2 = new AuthToken();
            testAuth2.setAuthtoken("bauth");

            testAuth2.setUsername("joy");
            Database db = new Database();
            db.openConnection();
            db.clearAll();
            UserDao uDao = db.getuserDao();
            PersonDao pDao = db.getpersonDao();
            EventDao eDao = db.geteventDao();
            AuthTokenDao aDao = db.getauthTokenDao();

            uDao.insert(testUser1);
            uDao.insert(testUser2);
            eDao.insert(testEvent1);
            eDao.insert(testEvent2);
            eDao.insert(testEvent3);
            aDao.insert(testAuth1);
            aDao.insert(testAuth2);
            db.closeConnection(true);

            OneEventResult expectedResult = new OneEventResult(testEvent1);
            expectedResult.setSuccess(true);

            OneEventResult outputResult = oneEventService.eventID(testEvent1.getEventID(), testAuth1.getAuthtoken());

            assertEquals(expectedResult, outputResult);


        } catch (DataAccessException e){
            assertEquals("error resetting Authtoken", e.getMessage());
        }
    }

    @Test
    public void testOneEventNegative(){
        try{
            User testUser1 = new User();
            testUser1.setUsername("spencer");
            testUser1.setPassword("imanidiot");
            testUser1.setEmail("spencer11@gmail.com");
            testUser1.setFirstName("Jin");
            testUser1.setLastName("chen");
            testUser1.setGender("f");
            testUser1.setPersonID("yxy_angie");

            User testUser2 = new User(); //user 2
            testUser2.setUsername("joy");
            testUser2.setPassword("medumbdumb");
            testUser2.setEmail("fehahaha@gmail.com");
            testUser2.setFirstName("joy");
            testUser2.setLastName("Seng");
            testUser2.setGender("f");
            testUser2.setPersonID("blahblahblah");

            Event testEvent1 = new Event();
            testEvent1.setEventID("uniqueEventID");
            testEvent1.setAssociatedUsername("spencer");
            testEvent1.setPersonID("yxy_angie");
            testEvent1.setLatitude(-1.0f);
            testEvent1.setLongitude(1.0f);
            testEvent1.setCountry("Thailand");
            testEvent1.setCity("Bangkok");
            testEvent1.setEventType("Birth");
            testEvent1.setYear(2005);

            Event testEvent2 = new Event();
            testEvent2.setEventID("uniqueEventID2");
            testEvent2.setAssociatedUsername("joy");
            testEvent2.setPersonID("Fefe");
            testEvent2.setLatitude(99.0f);
            testEvent2.setLongitude(-99.0f);
            testEvent2.setCountry("China");
            testEvent2.setCity("ShangHai");
            testEvent2.setEventType("xiao long bao");
            testEvent2.setYear(2010);

            Event testEvent3 = new Event();
            testEvent3.setEventID("uniqueEventID3");
            testEvent3.setAssociatedUsername("spencer");
            testEvent3.setPersonID("yxy_angie");
            testEvent3.setLatitude(55.0f);
            testEvent3.setLongitude(-55.0f);
            testEvent3.setCountry("Utah");
            testEvent3.setCity("Provo");
            testEvent3.setEventType("Start Uni");
            testEvent3.setYear(2022);

            AuthToken testAuth1 = new AuthToken();
            testAuth1.setAuthtoken("auth123");

            testAuth1.setUsername("spencer");

            AuthToken testAuth2 = new AuthToken();
            testAuth2.setAuthtoken("bauth");

            testAuth2.setUsername("joy");
            Database db = new Database();
            db.openConnection();
            db.clearAll();
            UserDao uDao = db.getuserDao();
            PersonDao pDao = db.getpersonDao();
            EventDao eDao = db.geteventDao();
            AuthTokenDao aDao = db.getauthTokenDao();

            uDao.insert(testUser1);
            uDao.insert(testUser2);
            eDao.insert(testEvent1);
            eDao.insert(testEvent2);
            eDao.insert(testEvent3);
            aDao.insert(testAuth1);
            aDao.insert(testAuth2);
            db.closeConnection(true);

            OneEventResult expectedResult = new OneEventResult(testEvent1);
            expectedResult.setSuccess(true);

            OneEventResult outputResult = oneEventService.eventID(testEvent1.getEventID(), testAuth1.getAuthtoken());

            assertEquals(expectedResult, outputResult);

            OneEventResult badExpectedResult = new OneEventResult(testEvent1);
            badExpectedResult.setSuccess(true);
            badExpectedResult.setMessage("AssociatedUsername of event and username of auth token do not match");

            OneEventResult badOutputResult = oneEventService.eventID(testEvent2.getEventID(), testAuth2.getAuthtoken());

            assertNotEquals(badExpectedResult, badOutputResult);

        } catch (DataAccessException e){
            assertEquals("error resetting Authtoken", e.getMessage());
        }
    }


}