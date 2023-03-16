package dataAccessAndServiceTest;

import dataaccess.*;
import org.junit.*;
import model.*;
import result.EventResult;
import service.EventService;

import static org.junit.Assert.*;

public class EventServiceTest{
    private EventService eventService;
    private Database db;
    @Before
    public void setUp() throws DataAccessException {
        eventService = new EventService();
        db = new Database();
        db.openConnection();
        db.clearAll();
    }

    @After
    public void tearDown() throws DataAccessException {
        eventService = null;
        db.closeConnection(true);
        db = null;
    }

    @Test
    public void testEventServicePositive() {
        try{
            User testUser1 = new User();
            testUser1.setUsername("nianfidn");
            testUser1.setPassword("nniadf");
            testUser1.setEmail("nianfidn34@gmail.com");
            testUser1.setFirstName("Lina");
            testUser1.setLastName("Chen");
            testUser1.setGender("f");
            testUser1.setPersonID("yyds123");

            User testUser2 = new User(); //user 2
            testUser2.setUsername("Jin");
            testUser2.setPassword("asfsagsag");
            testUser2.setEmail("Jin123@gmail.com");
            testUser2.setFirstName("Jin");
            testUser2.setLastName("Song");
            testUser2.setGender("f");
            testUser2.setPersonID("adfdsagb");

            Event testEvent1 = new Event();
            testEvent1.setEventID("ID1");
            testEvent1.setAssociatedUsername("nianfidn");
            testEvent1.setPersonID("yyds123");
            testEvent1.setLatitude(-1.0f);
            testEvent1.setLongitude(1.0f);
            testEvent1.setCountry("US");
            testEvent1.setCity("LA");
            testEvent1.setEventType("bike");
            testEvent1.setYear(2002);

            Event testEvent2 = new Event();
            testEvent2.setEventID("ID2");
            testEvent2.setAssociatedUsername("Jin");
            testEvent2.setPersonID("Fefe");
            testEvent2.setLatitude(49.0f);
            testEvent2.setLongitude(-79.0f);
            testEvent2.setCountry("Japan");
            testEvent2.setCity("Somewhere");
            testEvent2.setEventType("Cook");
            testEvent2.setYear(2008);

            Event testEvent3 = new Event();
            testEvent3.setEventID("ID3");
            testEvent3.setAssociatedUsername("nianfidn");
            testEvent3.setPersonID("yyds123");
            testEvent3.setLatitude(65.0f);
            testEvent3.setLongitude(-85.0f);
            testEvent3.setCountry("Idaho");
            testEvent3.setCity("Rexburg");
            testEvent3.setEventType("Graduation");
            testEvent3.setYear(2017);

            AuthToken testAuth1 = new AuthToken();
            testAuth1.setAuthtoken("123");
            testAuth1.setUsername("nianfidn");

            AuthToken testAuth2 = new AuthToken();
            testAuth2.setAuthtoken("234");
            testAuth2.setUsername("Jin");
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

            EventResult expectedResponse = new EventResult();
            Event[] input = new Event[2];
            input[0] = testEvent1;
            input[1] = testEvent3;
            expectedResponse.setSuccess(true);
            expectedResponse.setData(input);

            EventResult outputResponse = eventService.event(testAuth1.getAuthtoken());

            assertEquals(expectedResponse, outputResponse);


            EventResult expectedBadResponse = new EventResult();
            expectedBadResponse.setSuccess(false);
            expectedBadResponse.setMessage("no such authToken");

            EventResult badOutputresponse = eventService.event("bogus");

            assertNotEquals(expectedBadResponse, badOutputresponse);

        } catch (DataAccessException e){
            assertEquals("error resetting Authtoken", e.getMessage());
        }

    }


    @Test
    public void testEventServiceNegative() throws DataAccessException {
            User testUser1 = new User();
            testUser1.setUsername("nianfidn");
            testUser1.setPassword("nniadf");
            testUser1.setEmail("nianfidn34@gmail.com");
            testUser1.setFirstName("Lina");
            testUser1.setLastName("Chen");
            testUser1.setGender("f");
            testUser1.setPersonID("yyds123");

            User testUser2 = new User(); //user 2
            testUser2.setUsername("Jin");
            testUser2.setPassword("asfsagsag");
            testUser2.setEmail("Jin123@gmail.com");
            testUser2.setFirstName("Jin");
            testUser2.setLastName("Song");
            testUser2.setGender("f");
            testUser2.setPersonID("adfdsagb");

            Event testEvent1 = new Event();
            testEvent1.setEventID("ID1");
            testEvent1.setAssociatedUsername("nianfidn");
            testEvent1.setPersonID("yyds123");
            testEvent1.setLatitude(-1.0f);
            testEvent1.setLongitude(1.0f);
            testEvent1.setCountry("US");
            testEvent1.setCity("LA");
            testEvent1.setEventType("bike");
            testEvent1.setYear(2002);

            Event testEvent2 = new Event();
            testEvent2.setEventID("ID2");
            testEvent2.setAssociatedUsername("Jin");
            testEvent2.setPersonID("Fefe");
            testEvent2.setLatitude(49.0f);
            testEvent2.setLongitude(-79.0f);
            testEvent2.setCountry("Japan");
            testEvent2.setCity("Somewhere");
            testEvent2.setEventType("Cook");
            testEvent2.setYear(2008);

            Event testEvent3 = new Event();
            testEvent3.setEventID("ID3");
            testEvent3.setAssociatedUsername("nianfidn");
            testEvent3.setPersonID("yyds123");
            testEvent3.setLatitude(65.0f);
            testEvent3.setLongitude(-85.0f);
            testEvent3.setCountry("Idaho");
            testEvent3.setCity("Rexburg");
            testEvent3.setEventType("Graduation");
            testEvent3.setYear(2017);

            AuthToken testAuth1 = new AuthToken();
            testAuth1.setAuthtoken("123");
            testAuth1.setUsername("nianfidn");

            AuthToken testAuth2 = new AuthToken();
            testAuth2.setAuthtoken("234");
            testAuth2.setUsername("Jin");

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

            EventResult expectedResponse = new EventResult();
            Event[] input = new Event[2];
            input[0] = testEvent1;
            input[1] = testEvent3;
            expectedResponse.setSuccess(true);
            expectedResponse.setData(input);

            EventResult outputResponse = eventService.event(testAuth1.getAuthtoken());

            assertNotEquals(expectedResponse, outputResponse);


            EventResult expectedBadResponse = new EventResult();
            expectedBadResponse.setSuccess(false);
            expectedBadResponse.setMessage("no such authToken");

            EventResult badOutputresponse = eventService.event("bogus");

            assertNotEquals(expectedBadResponse, badOutputresponse);



    }

}