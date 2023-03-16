package dataAccessAndServiceTest;


import dataaccess.*;
import model.AuthToken;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class AuthtokenDaoTest {
    private Database database;
    private EventDao eDao;
    private UserDao uDao;
    private PersonDao pDao;
    private AuthTokenDao aDao;

    @Before
    public void setUp() throws DataAccessException {
        database = new Database();
        database.openConnection();
        database.clearAll();
        eDao = database.geteventDao();
        uDao = database.getuserDao();
        pDao = database.getpersonDao();
        aDao = database.getauthTokenDao();
    }

    @After
    public void tearDown() throws DataAccessException {
        database.closeConnection(false);
        eDao = null;
        uDao = null;
        pDao = null;
        aDao = null;
        database = null;
    }


    @Test
    public void testAuthtokenDaoPositive() throws DataAccessException {
        AuthTokenDao aDao = new AuthTokenDao();
        assertNotNull(aDao);
    }

    @Test
    public void testAuthtokenDaoNegative() throws DataAccessException {

        AuthTokenDao authotkenDao = new AuthTokenDao();
        authotkenDao = null;
        assertNull(authotkenDao);
    }

    @Test
    public void testSetConnectionPositive() throws DataAccessException, SQLException {
        final String CONNECTION_URL = "jdbc:sqlite:server.db";
        Connection conn = DriverManager.getConnection(CONNECTION_URL);
        aDao.setConnection(conn);
        assertNotNull(aDao);
    }

    @Test
    public void testSetConnectionPositive2() throws DataAccessException, SQLException {
        final String CONNECTION_URL = "jdbc:sqlite:server.db";
        Connection conn = DriverManager.getConnection(CONNECTION_URL);
        aDao.setConnection(conn);

        assertTrue(aDao != null);

    }


    @Test
    public void testInsertTokenPositive() throws DataAccessException {
        aDao.clear();
        // Create a valid User object
        AuthToken authToken = new AuthToken();
        authToken.setAuthtoken("1234");
        authToken.setUsername("JJ");

        aDao.insert(authToken);

        assertEquals(authToken.getAuthtoken(), aDao.getAuthToken("1234").getAuthtoken());
        assertEquals(authToken.getUsername(), aDao.getAuthToken("1234").getUsername());

    }

    @Test
    public void testInsertTokenNegative() throws DataAccessException {
        aDao.clear();
        assertThrows(NullPointerException.class, () -> aDao.insert(null));
        // Create a invalid User object
        AuthToken authToken = new AuthToken();
        aDao.insert(authToken);

        AuthToken goodAuth = new AuthToken();
        goodAuth.setAuthtoken("1234");
        goodAuth.setUsername("JJ");
        assertNotEquals(authToken.getAuthtoken(), goodAuth.getAuthtoken());
        assertNotEquals(authToken.getUsername(), goodAuth.getUsername());
    }



    @Test
    public void testClearPositive() throws DataAccessException {
        aDao.clear();
        AuthToken authToken1 = new AuthToken();
        authToken1.setAuthtoken("1234");
        authToken1.setUsername("JJ");

        AuthToken authToken2 = new AuthToken();
        authToken2.setAuthtoken("12324");
        authToken2.setUsername("CJ");

        aDao.insert(authToken1);
        aDao.insert(authToken2);

        aDao.clear();
        AuthToken retrievedAuth1 = aDao.getAuthToken("1234");
        AuthToken retrievedAuth2 = aDao.getAuthToken("12324");
        assertEquals("",retrievedAuth1.getUsername());
        assertEquals("",retrievedAuth2.getUsername());

    }

    @Test
    public void testClearPositive2() throws DataAccessException {
        aDao.clear();
        AuthToken authToken1 = new AuthToken();
        authToken1.setAuthtoken("1234");
        authToken1.setUsername("JJ");

        AuthToken authToken2 = new AuthToken();
        authToken2.setAuthtoken("12324");
        authToken2.setUsername("CJ");

        aDao.insert(authToken1);
        aDao.insert(authToken2);

        aDao.clear();
        AuthToken retrievedAuth1 = aDao.getAuthToken("1234");
        AuthToken retrievedAuth2 = aDao.getAuthToken("12324");
        assertEquals("",retrievedAuth1.getAuthtoken());
        assertEquals("",retrievedAuth2.getAuthtoken());
    }

    @Test
    public void testValidatePositive() throws DataAccessException {
        aDao.clear();

        AuthToken authToken2 = new AuthToken();
        authToken2.setAuthtoken("12324");
        authToken2.setUsername("CJ");

        aDao.insert(authToken2);

        boolean result = aDao.validate("12324");
        assertTrue(result);

//        boolean result2 = aDao.validate("12323T4");
        assertThrows(DataAccessException.class, () -> aDao.validate("12323T4"));
        assertThrows(DataAccessException.class, () -> aDao.validate("123223433T4"));

    }


    @Test
    public void testValidateNegative() throws DataAccessException {
        uDao.clear();
        AuthToken authToken2 = new AuthToken();
        authToken2.setAuthtoken("12324");
        authToken2.setUsername("CJ");

        AuthToken authToken3 = new AuthToken();
        authToken3.setAuthtoken("123245");
        authToken2.setUsername("EJ");

        assertThrows(DataAccessException.class, () -> aDao.validate("12324"));
        assertThrows(DataAccessException.class, () -> aDao.validate("123245"));

    }

    @Test
    public void testGetAuthtokenPositive() throws DataAccessException {


        uDao.clear();
        AuthToken authToken1 = new AuthToken();
        authToken1.setAuthtoken("1234");
        authToken1.setUsername("JJ");

        AuthToken authToken2 = new AuthToken();
        authToken2.setAuthtoken("12324");
        authToken2.setUsername("CJ");

        aDao.insert(authToken1);
        aDao.insert(authToken2);


        AuthToken retrievedAuth1 = aDao.getAuthToken("1234");
        AuthToken retrievedAuth2 = aDao.getAuthToken("12324");
        assertEquals("1234",retrievedAuth1.getAuthtoken());
        assertEquals("12324",retrievedAuth2.getAuthtoken());

    }


    @Test
    public void testGetAuthtokenrNegative() throws DataAccessException {
        uDao.clear();
        // Arrange
        String auth = "yijijij";

        // Act
        AuthToken authToken = aDao.getAuthToken(auth);

        // Assert
        assertEquals("", authToken.getAuthtoken());
        assertEquals("", authToken.getUsername());


    }




}
