package dataAccessAndServiceTest;


import dataaccess.*;
import model.Event;
import model.Person;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class EventDaoTest {
    private Database database;
    private EventDao eDao;
    private UserDao uDao;
    private PersonDao pDao;

    @Before
    public void setUp() throws DataAccessException {
        database = new Database();
        database.openConnection();
        database.clearAll();
        eDao = database.geteventDao();
        uDao = database.getuserDao();
        pDao = database.getpersonDao();
    }

    @After
    public void tearDown() throws DataAccessException {
        database.closeConnection(false);
        eDao = null;
        uDao = null;
        pDao = null;
        database = null;
    }


    @Test
    public void testEventDaoPositive() throws DataAccessException{
        eDao = new EventDao();
        assertNotNull(eDao);
    }

    @Test
    public void testEventDaoNegative() throws DataAccessException{
        EventDao eDao = new EventDao();
        eDao = null;
        assertNull(eDao);
    }

    @Test
    public void testSetConnectionPositive() throws DataAccessException, SQLException {
        final String CONNECTION_URL = "jdbc:sqlite:server.db";
        Connection conn = DriverManager.getConnection(CONNECTION_URL);
        eDao.setConnection(conn);
        assertNotNull(eDao);
    }

    @Test
    public void testSetConnectionPositive2() throws DataAccessException, SQLException {
        final String CONNECTION_URL = "jdbc:sqlite:server.db";
        Connection conn = DriverManager.getConnection(CONNECTION_URL);
        eDao.setConnection(conn);

        assertTrue(eDao != null);

    }

    @Test
    public void testInsertEventPositive() throws DataAccessException {
        // Positive test case for inserting an event
        Event event = new Event("12345", "testUser", "testPerson", 20.0f, 30.0f,
                "United States", "Provo", "Birth", 2000);
        eDao.insert(event);
        Event retrievedEvent = eDao.selectSingleEvent("12345");
        assertNotNull(retrievedEvent);
        assertEquals(event, retrievedEvent);
    }

    @Test
    public void testInsertEventNegative() throws DataAccessException {
        // Negative test case for inserting an event with null ID
        Event event = new Event(null, "testUser", "testPerson", 20.0f, 30.0f,
                "United States", "Provo", "Birth", 2000);
        assertThrows(DataAccessException.class, () -> eDao.insert(event));

        Event event2 = new Event(null, "testUser", "testPerson", 20.0f, 30.0f,
                "United States", "OREM", "Birth", 2010);
        assertThrows(DataAccessException.class, () -> eDao.insert(event2));
    }


    @Test
    public void testFindPositive() throws DataAccessException {

        Event event = new Event();
        event.setEventID("1234");
        event.setAssociatedUsername("username");
        event.setPersonID("ID123");
        event.setLatitude(35.6f);
        event.setLongitude(-115.1f);
        event.setCountry("United States");
        event.setCity("Las Vegas");
        event.setEventType("birth");
        event.setYear(1999);
        eDao.insert(event);
        assertTrue(eDao.find("1234"));
        assertThrows(DataAccessException.class, () -> eDao.find("12"));

    }

    @Test
    public void testFindNegative() throws DataAccessException {

        Event event = new Event();
        event.setEventID("1234");
        event.setAssociatedUsername("username");
        event.setPersonID("ID123");
        event.setLatitude(35.6f);
        event.setLongitude(-115.1f);
        event.setCountry("United States");
        event.setCity("Las Vegas");
        event.setEventType("birth");
        event.setYear(1999);
        eDao.insert(event);

        assertThrows(DataAccessException.class, () -> eDao.find("5678"));

        Event event2 = new Event();
        event.setEventID("12345");
        event.setAssociatedUsername("username");
        event.setPersonID("ID123");
        event.setLatitude(35.6f);
        event.setLongitude(-115.1f);
        event.setCountry("United States");
        event.setCity("Las Vegas");
        event.setEventType("birth");
        event.setYear(1999);
        eDao.insert(event);

        assertThrows(DataAccessException.class, () -> eDao.find("2345"));

    }

    @Test
    public void testClearPositive() throws DataAccessException {
        // Positive test case for clearing all events
        Event event1 = new Event("12345", "testUser", "testPerson", 20.0f, 30.0f,
                "United States", "Provo", "Birth", 2000);
        Event event2 = new Event("67890", "testUser", "testPerson", 25.0f, 35.0f,
                "United States", "Salt Lake City", "Death", 2020);
        eDao.insert(event1);
        eDao.insert(event2);
        eDao.clear();
        Event retrievedEvent1 = eDao.selectSingleEvent("12345");
        Event retrievedEvent2 = eDao.selectSingleEvent("67890");
        assertNotEquals(retrievedEvent1.getEventID(), event1.getEventID());
        assertNotEquals(retrievedEvent2.getEventID(), event1.getEventID());
    }

    @Test
    public void testClearPositive2() throws DataAccessException {
        Event event1 = new Event("12345", "testUser", "testPerson", 20.0f, 30.0f,
                "United States", "Provo", "Birth", 2000);
        Event event2 = new Event("67890", "testUser", "testPerson", 25.0f, 35.0f,
                "United States", "Salt Lake City", "Death", 2020);
        eDao.insert(event1);
        eDao.insert(event2);
        eDao.clear();
        Event retrievedEvent1 = eDao.selectSingleEvent("12345");
        Event retrievedEvent2 = eDao.selectSingleEvent("67890");
        assertNotEquals(retrievedEvent1.getCity(), event1.getCity());
        assertNotEquals(retrievedEvent2.getCity(), event1.getCity());
    }

    @Test
    public void testDeleteAllEventsOfUserPositive() throws DataAccessException {
        // Setup: create a new user and some events associated with that user
        User user = new User("testUser", "password", "email", "firstName", "lastName", "m", "123");

        uDao.insert(user);

        Event event1 = new Event("event1", "testUser", "person1", 42.0f, -111.0f, "USA", "Logan", "birth", 1990);
        Event event2 = new Event("event2", "testUser", "person2", 40.0f, -112.0f, "USA", "Provo", "marriage", 2010);
        eDao.insert(event1);
        eDao.insert(event2);

        // Exercise: call the deleteAllEventsOfUser method
        eDao.deleteAllEventsOfUser(user);

        // Verify: check that the events associated with the user were deleted
        Event[] events = eDao.selectAllEvents(user.getUsername());
        assertEquals(0, events.length);
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> events[0].getEventID());
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> events[1].getEventID());
    }

    @Test
    public void testDeleteAllEventsOfUserPositive2() throws DataAccessException {
        User user = new User("testUser", "password", "email", "firstName", "lastName", "m", "123");

        uDao.insert(user);

        Event event1 = new Event("event1", "testUser", "person1", 42.0f, -111.0f, "USA", "Logan", "birth", 1990);
        Event event2 = new Event("event2", "testUser", "person2", 40.0f, -112.0f, "USA", "Provo", "marriage", 2010);
        eDao.insert(event1);
        eDao.insert(event2);

        // Exercise: call the deleteAllEventsOfUser method
        eDao.deleteAllEventsOfUser(user);

        // Verify: check that the events associated with the user were deleted
//        Event[] events = eDao.selectAllEvents(user.getUsername());
        assertThrows(DataAccessException.class, () -> eDao.find("event1"));
        assertThrows(DataAccessException.class, () -> eDao.find("event2"));


    }



    @Test
    public void testSelectSingleEventPositive() throws DataAccessException {
        // Positive test case for getting an existing event
        Event event = new Event("12345", "testUser", "testPerson", 20.0f, 30.0f,
                "United States", "Provo", "Birth", 2000);
        eDao.insert(event);
        Event retrievedEvent = eDao.selectSingleEvent("12345");
        assertNotNull(retrievedEvent);
        assertEquals(event, retrievedEvent);
    }

    @Test
    public void testSelectSingleEventNegative() throws DataAccessException {
        // Negative test case for getting a non-existing event
        assertThrows(DataAccessException.class, () -> eDao.find("non-existing-id"));
    }

    @Test
    public void testSelectAllEventsPositive() throws DataAccessException{
        eDao.clear();
        Event event1 = new Event("event1", "testUser", "person1", 42.0f, -111.0f, "USA", "Logan", "birth", 1990);
        Event event2 = new Event("event2", "testUser", "person2", 40.0f, -112.0f, "USA", "Provo", "marriage", 2010);
        eDao.insert(event1);
        eDao.insert(event2);

        Event[] events = eDao.selectAllEvents("testUser");
        assertEquals(2, events.length);

    }
    @Test
    public void testSelectAllEventsNegative() throws DataAccessException{
        eDao.clear();
        Event event1 = new Event("event1", "testUser", "person1", 42.0f, -111.0f, "USA", "Logan", "birth", 1990);
        Event event2 = new Event("event2", "testUser", "person2", 40.0f, -112.0f, "USA", "Provo", "marriage", 2010);
        eDao.insert(event1);
        eDao.insert(event2);

        Event[] events = eDao.selectAllEvents("testUser1");
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> eDao.find(events[1].getEventID() + "1"));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> eDao.find(events[1].getEventID() + "2"));
    }



    @Test
    public void testGenerateEventsNegative() throws DataAccessException {
        // Set up test data
        EventDao eventDao = new EventDao();

        // Attempt to generate events for null person
        assertThrows(NullPointerException.class, () -> eventDao.generateEvents(null));


    }

    @Test
    public void testGenerateEventsPositive() throws DataAccessException {
        // Set up test data
        Person root = new Person();
        root.setPersonID("123");
        root.setAssociatedUsername("user123");

        // Generate events
        int numEvents = eDao.generateEvents(root);

        // Check that a birth event was generated
        Event[] events = eDao.selectAllEvents("user123");
        boolean birthEventFound = false;
        boolean generateThreeEvents = false;
        for (Event event : events) {
            if (event.getEventType().equals("Birth")) {
                birthEventFound = true;
                break;
            }
        }
        assertTrue(birthEventFound);

        if (events.length == 3){
            generateThreeEvents = true;
        }
        assertTrue(generateThreeEvents);
    }

    @Test
    public void testGenerateParentsEventandYearPositive() throws DataAccessException {
        // Set up test data
        eDao.clear();
        Person father = new Person();
        father.setPersonID("123");
        father.setAssociatedUsername("user123");

        Person mother = new Person();
        mother.setPersonID("124");
        mother.setAssociatedUsername("user123");


        int rootYear = 1990;
        int parentBirhtYear = eDao.generateParentsEventandYear(father, mother, rootYear);

        assertEquals(parentBirhtYear, rootYear-45);
        boolean moreThanFiveEvents = false;
        if(eDao.selectAllEvents("user123").length > 5){
            moreThanFiveEvents = true;
        }

        assertTrue(moreThanFiveEvents);


    }

    @Test
    public void testGenerateParentsEventandYearNegative() throws DataAccessException {
        // Set up test data
        Person mother = new Person();
        Person father = new Person();
//
        assertThrows(DataAccessException.class, () -> eDao.generateParentsEventandYear(mother, null, 1990));
        assertThrows(NullPointerException.class, () -> eDao.generateParentsEventandYear(null, father, 1990));
        assertNull(mother.getPersonID());
        assertNull(father.getPersonID());

    }



}
