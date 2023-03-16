package dataAccessAndServiceTest;


import dataaccess.*;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class UserDaoTest {
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
        UserDao userDao = new UserDao();
        assertNotNull(userDao);
    }

    @Test
    public void testPersonDaoNegative() throws DataAccessException {

        UserDao userDao = new UserDao();
        userDao = null;
        assertNull(userDao);
    }

    @Test
    public void testSetConnectionPositive() throws DataAccessException, SQLException {
        final String CONNECTION_URL = "jdbc:sqlite:server.db";
        Connection conn = DriverManager.getConnection(CONNECTION_URL);
        uDao.setConnection(conn);
        assertNotNull(uDao);
    }

    @Test
    public void testSetConnectionPositive2() throws DataAccessException, SQLException {
        final String CONNECTION_URL = "jdbc:sqlite:server.db";
        Connection conn = DriverManager.getConnection(CONNECTION_URL);
        uDao.setConnection(conn);

        assertTrue(uDao != null);

    }


    @Test
    public void testInsertPersonPositive() throws DataAccessException {
        uDao.clear();
        // Create a valid User object
        User user = new User();
        user.setUsername("johndoe");
        user.setPassword("password");
        user.setEmail("johndoe@example.com");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setGender("M");
        user.setPersonID("12345678");


// Insert the user into the database
        uDao.insert(user);

        assertEquals(user.getEmail(), uDao.getUser("johndoe").getEmail());
        assertEquals(user.getPassword(), uDao.getUser("johndoe").getPassword());
        assertEquals(user.getGender(), uDao.getUser("johndoe").getGender());
        uDao.deleteUser(user);

    }

    @Test
    public void testInsertUserNegative() throws DataAccessException {
        uDao.clear();
        assertThrows(NullPointerException.class, () -> uDao.insert(null));
    }



    @Test
    public void testClearPositive() throws DataAccessException {
        uDao.clear();
        User user1 = new User("testUser", "testPassword", "testEmail", "testFirstName", "testLastName", "M", "testPersonID");
        User user2 = new User("testUser2", "testPassword2", "testEmail4", "testFirstName2", "testLastName4", "f", "testPersonID00");


        uDao.insert(user1);
        uDao.insert(user2);

        uDao.clear();
        User retrievedUser1 = uDao.getUser("testUser");
        User retrievedUser2 = uDao.getUser("testUser2");
        assertNull(retrievedUser1.getPersonID());
        assertNull(retrievedUser2.getPersonID());

    }

    @Test
    public void testClearPositive2() throws DataAccessException {
        uDao.clear();
        User user1 = new User("testUser", "testPassword", "testEmail", "testFirstName", "testLastName", "M", "testPersonID");
        User user2 = new User("testUser2", "testPassword2", "testEmail4", "testFirstName2", "testLastName4", "f", "testPersonID00");


        uDao.insert(user1);
        uDao.insert(user2);

        uDao.clear();
        User retrievedUser1 = uDao.getUser("testUser");
        User retrievedUser2 = uDao.getUser("testUser2");
        assertNull(retrievedUser1.getGender());
        assertNull(retrievedUser2.getGender());
        assertNull(retrievedUser1.getEmail());
        assertNull(retrievedUser2.getEmail());
    }

    @Test
    public void testValidateUsernamePasswordPositive() throws DataAccessException {
        uDao.clear();
        String username = "JohnDoe";
        String password = "password123";
        User user = new User(username, password, "testEmail4", "testFirstName2", "testLastName4", "f", "testPersonID00");
        uDao.insert(user);

        boolean result = uDao.validateUsernamePassword(user);
        assertTrue(result);
    }


    @Test
    public void testValidateUsernamePasswordNegative() throws DataAccessException {
        uDao.clear();
        String username = "";
        String password = "password123";
        User user = new User(username, password, "testEmail4", "testFirstName2", "testLastName4", "f", "testPersonID00");

        assertThrows(DataAccessException.class, () -> uDao.validateUsernamePassword(user));

    }

    @Test
    public void testGetUserPositive() throws DataAccessException {
        uDao.clear();

        User user = new User("JohnDoe", "password123", "testEmail4", "testFirstName2", "testLastName4", "f", "testPersonID00");

        uDao.insert(user);

        User retrivedUser = uDao.getUser("JohnDoe");
        // Assert
        assertNotNull(retrivedUser);
        assertEquals(user.getFirstName(), retrivedUser.getFirstName());
        assertEquals(user.getLastName(), retrivedUser.getLastName());
        assertEquals(user.getEmail(), retrivedUser.getEmail());
    }


    @Test
    public void testGetUserNegative() throws DataAccessException {
        uDao.clear();
        // Arrange
        String username = "yi";

        // Act
        User actualUser = uDao.getUser(username);

        // Assert
        assertNull(actualUser.getGender());
        assertNull(actualUser.getEmail());
        assertNull(actualUser.getPersonID());
        assertNull(actualUser.getFirstName());

    }



    @Test
    public void testGetPersonIDOfUserPositive() throws DataAccessException {
        // Arrange

        User user = new User("testuser", "password", "testuser@test.com", "Test", "User", "M", "person123");

        // Insert the test user into the database
        uDao.insert(user);

        // Act
        String personID = uDao.getPersonIDOfUser(user);

        // Assert
        assertEquals("person123", personID);
        uDao.deleteUser(user);
    }

    @Test
    public void testGetPersonIDOfUserNegative() throws DataAccessException {
        User user = new User(null, "password", "testuser@test.com", "Test", "User", "M", "person123");

        assertThrows(DataAccessException.class, () -> uDao.insert(user));

        String personID = uDao.getPersonIDOfUser(user);

        assertNotEquals("person123", personID);
        uDao.deleteUser(user);
        assertThrows(NullPointerException.class, () -> uDao.getPersonIDOfUser(null));

    }


    @Test
    public void testDeleteUserPositive() throws DataAccessException {


        // Insert a new user
        User user = new User("testUser", "testPassword", "testEmail", "testFirstName", "testLastName", "M", "testPersonID");
        uDao.insert(user);

        // Delete the user
        uDao.deleteUser(user);

        // Check if the user was deleted successfully
        String personID = uDao.getPersonIDOfUser(user);
        assertEquals("", personID);
        assertNotEquals(user, uDao.getUser("testUser"));


    }

    @Test
    public void testDeleteUserPositive2() throws DataAccessException {


        // Insert a new user
        User user = new User("testUser", "testPassword", "testEmail", "testFirstName", "testLastName", "M", "testPersonID");
        uDao.insert(user);

        // Delete the user
        uDao.deleteUser(user);

        // Check if the user was deleted successfully
        User retrivedUser = uDao.getUser("testUser");
        assertNull(retrivedUser.getPersonID());
        assertNull(retrivedUser.getPassword());
        assertNull(retrivedUser.getEmail());
    }








}
