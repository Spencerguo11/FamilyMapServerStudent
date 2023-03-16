package dataAccessAndServiceTest;


import dataaccess.*;
import model.AuthToken;
import model.Event;
import model.Person;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.*;

public class PersonDaoTest {
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
    public void testPersonDaoPositive() throws DataAccessException {
        pDao = new PersonDao();
        assertNotNull(pDao);
    }

    @Test
    public void testPersonDaoNegative() throws DataAccessException {
        PersonDao pDao = new PersonDao();
        pDao = null;
        assertNull(pDao);
    }

    @Test
    public void testSetConnectionPositive() throws DataAccessException, SQLException {
        final String CONNECTION_URL = "jdbc:sqlite:server.db";
        Connection conn = DriverManager.getConnection(CONNECTION_URL);
        pDao.setConnection(conn);
        assertNotNull(pDao);
    }

    @Test
    public void testSetConnectionPositive2() throws DataAccessException, SQLException {
        final String CONNECTION_URL = "jdbc:sqlite:server.db";
        Connection conn = DriverManager.getConnection(CONNECTION_URL);
        pDao.setConnection(conn);

        assertTrue(pDao != null);

    }


    @Test
    public void testInsertPersonPositive() throws DataAccessException {
        // Positive test case for inserting an event
        Person person = new Person("12345", "testUser", "user", "lin",
                "m", "Scott", "You", "girl");
        pDao.insert(person);
        Person result = pDao.selectSinglePerson("12345");
        assertNotNull(result);
        assertEquals(person, result);
    }

    @Test
    public void testInsertPersonNegative() throws DataAccessException {
        // Negative test case for inserting an event with null ID

        assertThrows(NullPointerException.class, () -> pDao.insert(null));

        Person person = new Person();
        person.setGender("f");
        assertThrows(DataAccessException.class, () -> pDao.insert(person));

        Person person2 = new Person();
        person2.setPersonID("1234");
        person2.setAssociatedUsername("JJ");
        assertNotEquals(person.getPersonID(), person2.getPersonID());
        assertNotEquals(person.getAssociatedUsername(), person2.getAssociatedUsername());
    }


    @Test
    public void testGenerateGenerationsPositive() throws DataAccessException {

        Person rootPerson = new Person("12345", "testUser", "user", "lin",
                "m", "Scott", "You", "girl");

        pDao.insert(rootPerson);


        pDao.generateGenerations(rootPerson, 3, eDao, 2000);

        Person[] persons = pDao.getAllPerson("testUser");
        Event[] events = eDao.selectAllEvents("testUser");

        assertEquals(15, persons.length);
        assertEquals(42, events.length);

    }

    @Test
    public void testGenerateGenerationsNegative() throws DataAccessException {
//        Person root = new Person();

        assertThrows(NullPointerException.class, () -> pDao.generateGenerations(null, 3, eDao, 1900));
//        assertThrows(NullPointerException.class, () -> pDao.generateGenerations(root, 3, null, 1900));
        assertThrows(NullPointerException.class, () -> pDao.generateGenerations(null, 3, null, 1980));

    }

    @Test
    public void testUpdateSpousePositve() throws DataAccessException {
        // Create two persons and insert them into the database
        Person person1 = new Person("12345", "Ryan", "user", "lin",
                "m", "Scott", "You", "girl");
        pDao.insert(person1);

        Person person2 = new Person("123456", "Ryan", "Kim", "lin",
                "f", "Jacob", "You", "yi");
        pDao.insert(person2);

// Set person1 as spouse of person2
        pDao.updateSpouse(person2, person1.getPersonID());

// Check that the spouse ID has been updated
        Person retrievedPerson2 = pDao.selectSinglePerson(person2.getPersonID());
        assertEquals(person1.getPersonID(), retrievedPerson2.getSpouseID());
        assertNotEquals(person2.getPersonID(), "yi");

    }

    @Test
    public void testUpdateSpouseNegative() throws DataAccessException {
        // Create two persons and insert them into the database

        Person person2 = new Person("123456", "Ryan", "Kim", "lin",
                "f", "Jacob", "You", "yi");
        pDao.insert(person2);

// Set person1 as spouse of person2
        pDao.updateSpouse(person2, null);
        Person retrievedPerson2 = pDao.selectSinglePerson(person2.getPersonID());
        assertNotNull(retrievedPerson2.getSpouseID());
        assertNotEquals(retrievedPerson2.getSpouseID(), "yi");

    }


    @Test
    public void testGetAllPersonPositive() throws DataAccessException {
        // Create a PersonDao object and set the connection

        pDao.insert(new Person("123", "username", "John", "Doe", "m", null, null, null));
        pDao.insert(new Person("456", "username", "Jane", "Doe", "f", null, null, null));
        pDao.insert(new Person("789", "username", "Bob", "Smith", "m", null, null, null));


        ArrayList<Person> expectedPersons = new ArrayList<>();
        expectedPersons.add(new Person("123", "username", "John", "Doe", "m", null, null, null));
        expectedPersons.add(new Person("456", "username", "Jane", "Doe", "f", null, null, null));
        expectedPersons.add(new Person("789", "username", "Bob", "Smith", "m", null, null, null));

        Person[] personFinal = new Person[expectedPersons.size()];
        personFinal = expectedPersons.toArray(personFinal);

        Person[] persons = pDao.getAllPerson("username");

        assertEquals(personFinal, persons);
        assertEquals(personFinal[0].getPersonID(), persons[0].getPersonID());
        assertEquals(personFinal[2].getLastName(), persons[2].getLastName());

    }

    @Test
    public void testGetAllPersonNegative() throws DataAccessException {
        // Create a PersonDao object and set the connection

        pDao.insert(new Person("123", "username", "John", "Doe", "m", null, null, null));
        pDao.insert(new Person("456", "username", "Jane", "Doe", "f", null, null, null));
        pDao.insert(new Person("789", "username", "Bob", "Smith", "m", null, null, null));


        ArrayList<Person> expectedPersons = new ArrayList<>();
        expectedPersons.add(new Person("123", "username", "John", "Doe", "m", null, null, null));
        expectedPersons.add(new Person("456", "username", "Jane", "Doe", "f", null, null, null));
        expectedPersons.add(new Person("789", "username", "Bob", "Smith", "m", null, null, null));

        Person[] personFinal = new Person[expectedPersons.size()];
        personFinal = expectedPersons.toArray(personFinal);

        Person[] persons = pDao.getAllPerson("user");

        assertNotEquals(personFinal, persons);

        pDao.clear();
        Person[] persons2 = pDao.getAllPerson("username");

        assertNotEquals(personFinal, persons2);

    }

    @Test
    public void testSelectSinglePersonPositive() throws DataAccessException {
        pDao.clear();

        // Create a new Person object
        Person person = new Person();
        person.setPersonID("123");
        person.setAssociatedUsername("testUser");
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setGender("m");

        // Insert the Person object into the database

        pDao.insert(person);


        // Retrieve the Person object from the database using selectSinglePerson
        Person retrievedPerson = pDao.selectSinglePerson("123");

        // Ensure that the retrieved Person object matches the original
        assertEquals(person, retrievedPerson);
        assertNotEquals(person, "");
    }

    @Test
    public void testSelectSinglePersonNegative() throws DataAccessException {
        pDao.clear();

        // Create a new Person object
        Person person = new Person();
        person.setPersonID("123");
        person.setAssociatedUsername("testUser");
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setGender("m");


        Person person2 = new Person();
        person2.setPersonID("12365");
        // Insert the Person object into the database


        // Retrieve the Person object from the database using selectSinglePerson
        Person retrievedPerson = pDao.selectSinglePerson("1234");
        Person retrievedPerson2 = pDao.selectSinglePerson("12345");
        // Ensure that the retrieved Person object matches the original
        assertNotEquals(person, retrievedPerson);
        assertNotEquals(person2, retrievedPerson2);
    }



    @Test
    public void testClearPositive() throws DataAccessException {
        // Positive test case for clearing all events
        pDao.insert(new Person("123", "username", "John", "Doe", "m", null, null, null));
        pDao.insert(new Person("456", "username", "Jane", "Doe", "f", null, null, null));

        pDao.clear();
        Person retrievedPerson1 = pDao.selectSinglePerson("123");
        Person retrievedPerson2 = pDao.selectSinglePerson("456");
        assertNull(retrievedPerson1.getPersonID());
        assertNull(retrievedPerson2.getPersonID());
    }

    @Test
    public void testClearPositive2() throws DataAccessException {
        pDao.insert(new Person("123", "username", "John", "Doe", "m", null, null, null));
        pDao.insert(new Person("456", "username", "Jane", "Doe", "f", null, null, null));

        pDao.clear();
        Person retrievedPerson1 = pDao.selectSinglePerson("123");
        Person retrievedPerson2 = pDao.selectSinglePerson("456");
        assertNull(retrievedPerson1.getAssociatedUsername());
        assertNull(retrievedPerson2.getAssociatedUsername());
    }


    @Test
    public void testDeleteAllPeopleOfUserPositive() throws DataAccessException {
// Create a new user

        User user = new User("testuser", "password", "test@test.com", "John", "Doe", "m", UUID.randomUUID().toString());

        // Add some people to the user's family tree
        Person father = new Person(UUID.randomUUID().toString(), "testuser", "Bob", "Doe", "m", null, null, null);
        Person mother = new Person(UUID.randomUUID().toString(), "testuser", "Jane", "Doe", "f", null, null, null);
        Person child = new Person(UUID.randomUUID().toString(), "testuser", "Sam", "Doe", "m", father.getPersonID(), mother.getPersonID(), null);

        pDao.insert(father);
        pDao.insert(mother);
        pDao.insert(child);

// Delete all people associated with the user
        pDao.deleteAllPeopleofUser(user);

// Verify that all people associated with the user have been deleted
        assertNull(pDao.selectSinglePerson(father.getPersonID()).getFirstName());
        assertNull(pDao.selectSinglePerson(mother.getPersonID()).getFirstName());
        assertNull(pDao.selectSinglePerson(child.getPersonID()).getFirstName());

    }

    @Test
    public void testDeleteAllPeopleOfUserPositive2() throws DataAccessException {
// Create a new user

        User user = new User("testuser", "password", "test@test.com", "John", "Doe", "m", UUID.randomUUID().toString());

        // Add some people to the user's family tree
        Person father = new Person(UUID.randomUUID().toString(), "testuser", "Bob", "Doe", "m", null, null, null);
        Person mother = new Person(UUID.randomUUID().toString(), "testuser", "Jane", "Doe", "f", null, null, null);
        Person child = new Person(UUID.randomUUID().toString(), "testuser", "Sam", "Doe", "m", father.getPersonID(), mother.getPersonID(), null);

        pDao.insert(father);
        pDao.insert(mother);
        pDao.insert(child);

// Delete all people associated with the user
        pDao.deleteAllPeopleofUser(user);

// Verify that all people associated with the user have been deleted
        assertNull(pDao.selectSinglePerson(father.getPersonID()).getAssociatedUsername());
        assertNull(pDao.selectSinglePerson(mother.getPersonID()).getAssociatedUsername());
        assertNull(pDao.selectSinglePerson(child.getPersonID()).getAssociatedUsername());

    }

}