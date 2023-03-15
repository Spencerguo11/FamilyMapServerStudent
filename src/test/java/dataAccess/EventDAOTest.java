////package dataAccess;
////
////import Model.Event;
////import dao.DataAccessException;
////import dao.Database;
////import dao.EventDAO;
////import org.junit.jupiter.api.AfterEach;
////import org.junit.jupiter.api.BeforeEach;
////import org.junit.jupiter.api.Test;
////
////import java.sql.Connection;
////
////import static org.junit.jupiter.api.Assertions.*;
////
//////We will use this to test that our insert method is working and failing in the right ways
////public class EventDAOTest {
////    private Database db;
////    private Event bestEvent;
////    private EventDAO eventDao;
////
////    @BeforeEach
////    public void setUp() throws DataAccessException {
////        // Here we can set up any classes or variables we will need for each test
////        // lets create a new instance of the Database class
////        db = new Database();
////        // and a new event with random data
////        bestEvent = new Event("Biking_123A", "Gale", "Gale123A",
////                35.9f, 140.1f, "Japan", "Ushiku",
////                "Biking_Around", 2016);
////
////        // Here, we'll open the connection in preparation for the test case to use it
////        db.openConnection();
////        //Then we pass that connection to the EventDAO, so it can access the database.
////        eventDao = db.getEventDAO();
////        //Let's clear the database as well so any lingering data doesn't affect our tests
////        eventDao.clear();
////    }
////
////    @AfterEach
////    public void tearDown() throws DataAccessException {
////        // Here we close the connection to the database file, so it can be opened again later.
////        // We will set commit to false because we do not want to save the changes to the database
////        // between test cases.
////        db.closeConnection(false);
////    }
////
////    @Test
////    public void insertPass() throws DataAccessException {
////        // Start by inserting an event into the database.
////        eventDao.insert(bestEvent);
////        // Let's use a find method to get the event that we just put in back out.
////        boolean compareTest = eventDao.find(bestEvent.getEventID());
////        // First lets see if our find method found anything at all. If it did then we know that we got
////        // something back from our database.
////        assertNotNull(compareTest);
////        // Now lets make sure that what we put in is the same as what we got out. If this
////        // passes then we know that our insert did put something in, and that it didn't change the
////        // data in any way.
////        // This assertion works by calling the equals method in the Event class.
////        assertEquals(bestEvent, compareTest);
////    }
////
////    @Test
////    public void insertFail() throws DataAccessException {
////        // Let's do this test again, but this time lets try to make it fail.
////        // If we call the method the first time the event will be inserted successfully.
////        eventDao.insert(bestEvent);
////
////        // However, our sql table is set up so that the column "eventID" must be unique, so trying to insert
////        // the same event again will cause the insert method to throw an exception, and we can verify this
////        // behavior by using the assertThrows assertion as shown below.
////
////        // Note: This call uses a lambda function. A lambda function runs the code that comes after
////        // the "()->", and the assertThrows assertion expects the code that ran to throw an
////        // instance of the class in the first parameter, which in this case is a DataAccessException.
////        assertThrows(DataAccessException.class, () -> eventDao.insert(bestEvent));
////    }
////
////
////}
//
//package dataAccess;
//
//
//import dataaccess.DataAccessException;
//import dataaccess.Database;
//import dataaccess.EventDao;
//import model.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//
////We will use this to test that our insert method is working and failing in the right ways
//public class EventDaoTest {
//    private Database db;
//    private EventDao eDao;
//
//    @Before
//    public void setUp() throws DataAccessException {
//        db = new Database();
//        db.openConnection();
//        db.clearAll();
//        eDao = db.getuserDao();
//    }
//
//    @After
//    public void tearDown() throws DataAccessException {
//        db.closeConnection(false);
//        eDao = null;
//        db = null;
//    }
//
//    @Test
//    public void testResetEventTable(){
//        try{
//            Event testEvent1 = new Event();
//            testEvent1.setEventID("uniqueEventID");
//            testEvent1.setAssociatedUsername("angieyxy");
//            testEvent1.setPersonID("yxy_angie");
//            testEvent1.setLatitude(-1.0f);
//            testEvent1.setLongitude(1.0f);
//            testEvent1.setCountry("Thailand");
//            testEvent1.setCity("Bangkok");
//            testEvent1.setEventType("Birth");
//            testEvent1.setYear(2005);
//
//
//            eDao.insert(testEvent1); //inserts event into event table
//
//            String answer = "uniqueEventID\tangieyxy\tyxy_angie\t-1.0\t1.0\tThailand\tBangkok\tBirth\t2005\n";
//            assertEquals(answer, eDao.tableToString()); //asserts the table isn't empty
//
//            eDao.clear(); //resets table
//
//            assertEquals("",eDao.tableToString());
//
//
//        } catch (DataAccessException e){
//            assertEquals("Throwing exceptions is bad", e.getMessage());
//        }
//    }
//
//    @Test
//    public void testInsertEventIntoTable(){
//        try{
//            Event testEvent1 = new Event();
//            testEvent1.setEventID("uniqueEventID");
//            testEvent1.setAssociatedUsername("angieyxy");
//            testEvent1.setPersonID("yxy_angie");
//            testEvent1.setLatitude(-1.0f);
//            testEvent1.setLongitude(1.0f);
//            testEvent1.setCountry("Thailand");
//            testEvent1.setCity("Bangkok");
//            testEvent1.setEventType("Birth");
//            testEvent1.setYear(2005);
//
//            String expected = "uniqueEventID\tangieyxy\tyxy_angie\t-1.0\t1.0\tThailand\tBangkok\tBirth\t2005\n";
//
//            assertNotEquals(expected, eDao.tableToString()); //asserts that the table is not hard coded just to pass the test
//            eDao.insert(testEvent1);
//            assertEquals(expected, eDao.tableToString());
//
//        } catch (DataAccessException e){
//            assertEquals("Throwing exceptions is bad", e.getMessage());
//        }
//    }
//
//    @Test
//    public void testMakeRootsEvents(){
//        try{
//            User testUser1 = new User(); //person 1
//            testUser1.setUsername("angieyxy");
//            testUser1.setPassword("imanidiot");
//            testUser1.setEmail("tokyoghoul122@gmail.com");
//            testUser1.setFirstName("Angelina");
//            testUser1.setLastName("You");
//            testUser1.setGender("f");
//            testUser1.setPersonID("yxy_angie");
//
//            Person testPerson1 = new Person(testUser1);
//            testPerson1.setSpouseID("Howard");
//            testPerson1.setMotherID("Sylvia");
//            testPerson1.setFatherID("Ryan");
//
//            int expectedNumEvents = 4;
//            int birthYear = 0;
//
//            String eventTablePreliminary = eDao.tableToString();
//            String[] eventsPreliminary = eventTablePreliminary.split("\n");
//
//            assertNotEquals(expectedNumEvents, eventsPreliminary.length);
//            assertNotEquals(birthYear,1960);
//
//            birthYear = eDao.generateRootEvent(testPerson1);
//
//            String eventTable = eDao.tableToString();
//            String[] events = eventTable.split("\n");
//
//            assertEquals(expectedNumEvents, events.length);
//            assertEquals(birthYear,1960);
//
//        } catch (DataAccessException e){
//            assertEquals("Throwing exceptions is bad", e.getMessage());
//        }
//    }
//
//    @Test
//    public void testGenerateEventDataParents(){
//        try{
//            User testUser1 = new User(); //person 1
//            testUser1.setUsername("angieyxy");
//            testUser1.setPassword("imanidiot");
//            testUser1.setEmail("tokyoghoul122@gmail.com");
//            testUser1.setFirstName("Angelina");
//            testUser1.setLastName("You");
//            testUser1.setGender("f");
//            testUser1.setPersonID("yxy_angie");
//            Person testPerson1 = new Person(testUser1);
//            testPerson1.setSpouseID("Howard");
//            testPerson1.setMotherID("Sylvia");
//            testPerson1.setFatherID("Ryan");
//
//            User testUser2 = new User(); //person 2
//            testUser2.setUsername("Felicia");
//            testUser2.setPassword("medumbdumb");
//            testUser2.setEmail("fehahaha@gmail.com");
//            testUser2.setFirstName("Felicia");
//            testUser2.setLastName("Seng");
//            testUser2.setGender("f");
//            testUser2.setPersonID("blahblahblah");
//            Person testPerson2 = new Person(testUser2);
//            testPerson2.setSpouseID("Spencer");
//            testPerson2.setFatherID("Scott");
//            testPerson2.setMotherID("Rebecca");
//
//            int expectedNumEvents = 8;
//            int exptectedBirthDateOfParents = 1934;
//            int actualBirthYearOfParents = 0;
//
//            String eventsOutput = eDao.tableToString();
//            String[] eventsArray = eventsOutput.split("\n");
//
//            assertNotEquals(expectedNumEvents, eventsArray.length);
//            assertNotEquals(exptectedBirthDateOfParents, actualBirthYearOfParents);
//
//            actualBirthYearOfParents = eDao.generateEventDataParents(testPerson2,testPerson1,1960);
//
//            eventsOutput = eDao.tableToString();
//            String[] outputArray = eventsOutput.split("\n");
//
//            assertEquals(expectedNumEvents, outputArray.length);
//            assertEquals(exptectedBirthDateOfParents, actualBirthYearOfParents);
//
//
//        } catch (DataAccessException e){
//            assertEquals("Throwing exceptions is bad", e.getMessage());
//        }
//    }
//
//    @Test
//    public void testDeleteAllEventsOfuser(){
//        try{
//            User testUser1 = new User(); //person 1
//            testUser1.setUsername("angieyxy");
//            testUser1.setPassword("imanidiot");
//            testUser1.setEmail("tokyoghoul122@gmail.com");
//            testUser1.setFirstName("Angelina");
//            testUser1.setLastName("You");
//            testUser1.setGender("m");
//            testUser1.setPersonID("yxy_angie");
//
//            Event testEvent1 = new Event();
//            testEvent1.setEventID("uniqueEventID");
//            testEvent1.setAssociatedUsername("angieyxy");
//            testEvent1.setPersonID("yxy_angie");
//            testEvent1.setLatitude(-1.0f);
//            testEvent1.setLongitude(1.0f);
//            testEvent1.setCountry("Thailand");
//            testEvent1.setCity("Bangkok");
//            testEvent1.setEventType("Birth");
//            testEvent1.setYear(2005);
//
//            Event testEvent2 = new Event();
//            testEvent2.setEventID("uniqueEventID2");
//            testEvent2.setAssociatedUsername("Felicia");
//            testEvent2.setPersonID("Fefe");
//            testEvent2.setLatitude(99.0f);
//            testEvent2.setLongitude(-99.0f);
//            testEvent2.setCountry("China");
//            testEvent2.setCity("ShangHai");
//            testEvent2.setEventType("xiao long bao");
//            testEvent2.setYear(2010);
//
//            eDao.insert(testEvent1);
//            eDao.insert(testEvent2);
//
//            String answer = "uniqueEventID2\tFelicia\tFefe\t99.0\t-99.0\tChina\tShangHai\txiao long bao\t2010\n";
//            assertNotEquals(answer, eDao.tableToString());
//
//            eDao.deleteAllEventsOfUser(testUser1);
//
//            assertEquals(answer, eDao.tableToString());
//
//        } catch (DataAccessException e){
//            assertEquals("Throwing exceptions is bad", e.getMessage());
//        }
//    }
//
//    @Test
//    public void testSelectSingleEvent(){
//        try{
//            Event testEvent1 = new Event();
//            testEvent1.setEventID("uniqueEventID");
//            testEvent1.setAssociatedUsername("angieyxy");
//            testEvent1.setPersonID("yxy_angie");
//            testEvent1.setLatitude(-1.0f);
//            testEvent1.setLongitude(1.0f);
//            testEvent1.setCountry("Thailand");
//            testEvent1.setCity("Bangkok");
//            testEvent1.setEventType("Birth");
//            testEvent1.setYear(2005);
//
//            Event testEvent2 = new Event();
//            testEvent2.setEventID("uniqueEventID2");
//            testEvent2.setAssociatedUsername("Felicia");
//            testEvent2.setPersonID("Fefe");
//            testEvent2.setLatitude(99.0f);
//            testEvent2.setLongitude(-99.0f);
//            testEvent2.setCountry("China");
//            testEvent2.setCity("ShangHai");
//            testEvent2.setEventType("xiao long bao");
//            testEvent2.setYear(2010);
//
//            eDao.insert(testEvent1);
//            eDao.insert(testEvent2);
//
//            Event output = eDao.selectSingleEvent(testEvent1.getEventID());
//
//            assertEquals(testEvent1, output); //overloaded event equals
//
//        } catch (DataAccessException e){
//            assertEquals("Throwing exceptions is bad", e.getMessage());
//        }
//    }
//
//    @Test
//    public void testDoesEventExist(){
//        try{
//            Event testEvent1 = new Event();
//            testEvent1.setEventID("uniqueEventID");
//            testEvent1.setAssociatedUsername("angieyxy");
//            testEvent1.setPersonID("yxy_angie");
//            testEvent1.setLatitude(-1.0f);
//            testEvent1.setLongitude(1.0f);
//            testEvent1.setCountry("Thailand");
//            testEvent1.setCity("Bangkok");
//            testEvent1.setEventType("Birth");
//            testEvent1.setYear(2005);
//
//            eDao.insert(testEvent1);
//
//            boolean output = eDao.find(testEvent1.getEventID());
//            assertEquals(output, true);
//
//            try {
//                output = eDao.find("BogusEventID");
//                assertEquals(true,false);
//            }catch (DataAccessException e){
//                String answer = "error finding eventID";
//                assertEquals(answer, e.getMessage());
//            }
//
//        } catch (DataAccessException e){
//            assertEquals("Throwing exceptions is bad", e.getMessage());
//        }
//    }
//
//    @Test
//    public void testSelectAllEvents(){
//        try{
//            Event testEvent1 = new Event();
//            testEvent1.setEventID("uniqueEventID");
//            testEvent1.setAssociatedUsername("angieyxy");
//            testEvent1.setPersonID("yxy_angie");
//            testEvent1.setLatitude(-1.0f);
//            testEvent1.setLongitude(1.0f);
//            testEvent1.setCountry("Thailand");
//            testEvent1.setCity("Bangkok");
//            testEvent1.setEventType("Birth");
//            testEvent1.setYear(2005);
//
//            Event testEvent2 = new Event();
//            testEvent2.setEventID("uniqueEventID2");
//            testEvent2.setAssociatedUsername("Felicia");
//            testEvent2.setPersonID("Fefe");
//            testEvent2.setLatitude(99.0f);
//            testEvent2.setLongitude(-99.0f);
//            testEvent2.setCountry("China");
//            testEvent2.setCity("ShangHai");
//            testEvent2.setEventType("xiao long bao");
//            testEvent2.setYear(2010);
//
//            Event testEvent3 = new Event();
//            testEvent3.setEventID("uniqueEventID3");
//            testEvent3.setAssociatedUsername("angieyxy");
//            testEvent3.setPersonID("yxy_angie");
//            testEvent3.setLatitude(55.0f);
//            testEvent3.setLongitude(-55.0f);
//            testEvent3.setCountry("Utah");
//            testEvent3.setCity("Provo");
//            testEvent3.setEventType("Start Uni");
//            testEvent3.setYear(2022);
//
//            eDao.insert(testEvent1);
//            eDao.insert(testEvent2);
//            eDao.insert(testEvent3);
//
//            Event[] expectedEvents = new Event[2];
//            expectedEvents[0] = testEvent1;
//            expectedEvents[1] = testEvent3;
//
//            Event[] output = eDao.selectAllEvents("angieyxy");
//
//            for (int i = 0; i < output.length; i++){
//                assertEquals(expectedEvents[i],output[i]);
//            }
//
//
//        } catch (DataAccessException e){
//            assertEquals("Throwing exceptions is bad", e.getMessage());
//        }
//    }
//
//    @Test
//    public void testTableToString(){
//        try{
//            String answer = "";
//            assertEquals(answer,eDao.tableToString());
//
//            Event testEvent1 = new Event();
//            testEvent1.setEventID("uniqueEventID");
//            testEvent1.setAssociatedUsername("angieyxy");
//            testEvent1.setPersonID("yxy_angie");
//            testEvent1.setLatitude(-1.0f);
//            testEvent1.setLongitude(1.0f);
//            testEvent1.setCountry("Thailand");
//            testEvent1.setCity("Bangkok");
//            testEvent1.setEventType("Birth");
//            testEvent1.setYear(2005);
//
//            answer = "uniqueEventID\tangieyxy\tyxy_angie\t-1.0\t1.0\tThailand\tBangkok\tBirth\t2005\n";
//            eDao.insert(testEvent1);
//            assertEquals(answer, eDao.tableToString());
//
//            Event testEvent2 = new Event();
//            testEvent2.setEventID("uniqueEventID2");
//            testEvent2.setAssociatedUsername("Felicia");
//            testEvent2.setPersonID("Fefe");
//            testEvent2.setLatitude(99.0f);
//            testEvent2.setLongitude(-99.0f);
//            testEvent2.setCountry("China");
//            testEvent2.setCity("ShangHai");
//            testEvent2.setEventType("xiao long bao");
//            testEvent2.setYear(2010);
//
//            answer = "uniqueEventID\tangieyxy\tyxy_angie\t-1.0\t1.0\tThailand\tBangkok\tBirth\t2005\n" +
//                    "uniqueEventID2\tFelicia\tFefe\t99.0\t-99.0\tChina\tShangHai\txiao long bao\t2010\n";
//            eDao.insert(testEvent2);
//            assertEquals(answer, eDao.tableToString());
//
//        } catch (DataAccessException e){
//            assertEquals("Throwing exceptions is bad", e.getMessage());
//        }
//    }
//
//}
