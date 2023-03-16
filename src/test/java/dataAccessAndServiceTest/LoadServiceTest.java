package dataAccessAndServiceTest;

import dataaccess.*;
import org.junit.*;
import model.*;
import request.LoadRequest;
import result.LoadResult;
import service.LoadService;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LoadServiceTest {
    private LoadService loadService;

    @Before
    public void setUp() {
        loadService = new LoadService();
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
        loadService = null;
    }

    @Test
    public void testLoadPositive() throws DataAccessException {
        User testUser1 = new User();
        testUser1.setUsername("spencer");
        testUser1.setPassword("spencer11");
        testUser1.setEmail("spencer112@gmail.com");
        testUser1.setFirstName("Angelina");
        testUser1.setLastName("You");
        testUser1.setGender("f");
        testUser1.setPersonID("yxy_angie");

        Person testPerson1 = new Person(testUser1);
        testPerson1.setSpouseID("Howard");
        testPerson1.setMotherID("Sylvia");
        testPerson1.setFatherID("Ryan");

        User testUser2 = new User(); //user 2
        testUser2.setUsername("Angel");
        testUser2.setPassword("medumbdumb");
        testUser2.setEmail("angel11@gmail.com");
        testUser2.setFirstName("Angel");
        testUser2.setLastName("Song");
        testUser2.setGender("f");
        testUser2.setPersonID("123Angel");

        Person testPerson2 = new Person(testUser2);
        testPerson2.setSpouseID("Spencer");
        testPerson2.setFatherID("Scott");
        testPerson2.setMotherID("Rebecca");

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
        testEvent2.setAssociatedUsername("Angel");
        testEvent2.setPersonID("Fefe");
        testEvent2.setLatitude(99.0f);
        testEvent2.setLongitude(-99.0f);
        testEvent2.setCountry("China");
        testEvent2.setCity("ShangHai");
        testEvent2.setEventType("Jia");
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


        User[] usersInput = new User[2];
        usersInput[0] = testUser1;
        usersInput[1] = testUser2;
        Person[] personsInput = new Person[2];
        personsInput[0] = testPerson1;
        personsInput[1] = testPerson2;
        Event[] eventsInput = new Event[3];
        eventsInput[0] = testEvent1;
        eventsInput[1] = testEvent2;
        eventsInput[2] = testEvent3;
        LoadRequest inputRequest = new LoadRequest();
        inputRequest.setUsers(usersInput);
        inputRequest.setEvents(eventsInput);
        inputRequest.setPersons(personsInput);

        LoadResult expectedResult = new LoadResult();
        expectedResult.setSuccess(true);
        expectedResult.setNumEvents(3);
        expectedResult.setNumPersons(2);
        expectedResult.setNumUsers(2);

        LoadResult outputResult = loadService.load(inputRequest);

        assertNotEquals(expectedResult, outputResult);
    }


    @Test
    public void testLoadNegative() throws DataAccessException {
        User testUser1 = new User();
//        testUser1.setUsername("spencer");
        testUser1.setPassword("spencer11");
        testUser1.setEmail("spencer112@gmail.com");
        testUser1.setFirstName("Angelina");
        testUser1.setLastName("You");
        testUser1.setGender("f");
        testUser1.setPersonID("yxy_angie");

        Person testPerson1 = new Person(testUser1);
        testPerson1.setSpouseID("Howard");
        testPerson1.setMotherID("Sylvia");
        testPerson1.setFatherID("Ryan");

        User testUser2 = new User(); //user 2
        testUser2.setUsername("Angel");
        testUser2.setPassword("medumbdumb");
        testUser2.setEmail("angel11@gmail.com");
        testUser2.setFirstName("Angel");
        testUser2.setLastName("Song");
        testUser2.setGender("f");
        testUser2.setPersonID("123Angel");

        Person testPerson2 = new Person(testUser2);
        testPerson2.setSpouseID("Spencer");
        testPerson2.setFatherID("Scott");
        testPerson2.setMotherID("Rebecca");

        Event testEvent1 = new Event();
        testEvent1.setEventID("ID");
        testEvent1.setAssociatedUsername("spencer");
        testEvent1.setPersonID("yxy_angie");
        testEvent1.setLatitude(-1.0f);
        testEvent1.setLongitude(1.0f);
        testEvent1.setCountry("Thailand");
        testEvent1.setCity("Bangkok");
        testEvent1.setEventType("Birth");
        testEvent1.setYear(2005);

        Event testEvent2 = new Event();
        testEvent2.setEventID("ID2");
        testEvent2.setAssociatedUsername("Angel");
        testEvent2.setPersonID("Fefe");
        testEvent2.setLatitude(99.0f);
        testEvent2.setLongitude(-99.0f);
        testEvent2.setCountry("China");
        testEvent2.setCity("ShangHai");
        testEvent2.setEventType("Jia");
        testEvent2.setYear(2010);

        Event testEvent3 = new Event();
        testEvent3.setEventID("ID3");
        testEvent3.setAssociatedUsername("spencer");
        testEvent3.setPersonID("yxy_angie");
        testEvent3.setLatitude(55.0f);
        testEvent3.setLongitude(-55.0f);
        testEvent3.setCountry("Utah");
        testEvent3.setCity("Provo");
        testEvent3.setEventType("Start Univeristy");
        testEvent3.setYear(2015);


        User[] usersInput = new User[2];
        usersInput[0] = testUser1;
        usersInput[1] = testUser2;
        Person[] personsInput = new Person[2];
        personsInput[0] = testPerson1;
        personsInput[1] = testPerson2;
        Event[] eventsInput = new Event[3];
        eventsInput[0] = testEvent1;
        eventsInput[1] = testEvent2;
        eventsInput[2] = testEvent3;
        LoadRequest inputRequest = new LoadRequest();
        inputRequest.setUsers(usersInput);
        inputRequest.setEvents(eventsInput);
        inputRequest.setPersons(personsInput);

        LoadResult expectedResult = new LoadResult();
        expectedResult.setSuccess(true);
        expectedResult.setNumEvents(3);
        expectedResult.setNumPersons(2);
        expectedResult.setNumUsers(2);

        LoadResult outputResult = loadService.load(inputRequest);

        assertNotEquals(expectedResult, outputResult);

    }

}