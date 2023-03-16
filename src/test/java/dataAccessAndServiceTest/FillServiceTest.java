package dataAccessAndServiceTest;

import dataaccess.*;
import org.junit.*;
import model.*;
import result.FillResult;
import service.FillService;

import java.util.Random;

import static org.junit.Assert.*;

public class FillServiceTest {

    private FillService fillService;

    @Before
    public void setUp() {
        fillService = new FillService();
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
        fillService = null;
    }

    @Test
    public void testFillServicePositive(){
        try{
            User testUser1 = new User();
            testUser1.setUsername("spencer");
            testUser1.setPassword("spencer");
            testUser1.setEmail("spencer@gmail.com");
            testUser1.setFirstName("spencer");
            testUser1.setLastName("guo");
            testUser1.setGender("f");
            testUser1.setPersonID("spencer_xy");
            Database db = new Database();
            db.openConnection();
            db.closeConnection(true);
            UserDao uDao = db.getuserDao();
            uDao.insert(testUser1);
            db.closeConnection(true);

            Random rand = new Random();
            int r = rand.nextInt(5);
            double numG = (double) r;
            double numPeople = (Math.pow(2.0, (numG + 1.0)) - 1.0);
            int finalNumPeople = (int) numPeople; //final
            int finalNumEvents = (finalNumPeople * 4);


            FillResult expectedResult = new FillResult();
            expectedResult.setSuccess(true);
            expectedResult.setUsername(testUser1.getUsername());
            expectedResult.setEvents(finalNumEvents);
            expectedResult.setPersons(finalNumPeople);

            FillResult outputResult = fillService.fill(testUser1.getUsername(), r);
            outputResult.setUsername(testUser1.getUsername());
            assertEquals(expectedResult, outputResult);

            FillResult goodResult = new FillResult();
            goodResult.setUsername("yo");
            goodResult.setSuccess(false);
            goodResult.setMessage("no such username");

            FillResult badOutputResult = fillService.fill(testUser1.getUsername(), r);
            badOutputResult.setUsername(testUser1.getUsername());

            assertNotEquals(goodResult, badOutputResult);
            assertEquals("no such username", goodResult.getMessage());

        } catch (DataAccessException e){
            assertEquals("error inserting user", e.getMessage());
        }
    }

    @Test
    public void testFillServiceNegative() throws DataAccessException {
        User testUser1 = new User();
        // don't set username
        testUser1.setPassword("spencer");
        testUser1.setEmail("spencer@gmail.com");
        testUser1.setFirstName("spencer");
        testUser1.setLastName("guo");
        testUser1.setGender("f");
        Database db = new Database();
        db.openConnection();
        db.closeConnection(true);
        UserDao uDao = db.getuserDao();
        assertThrows(DataAccessException.class, () -> uDao.insert(testUser1));

        Random rand = new Random();
        int r = rand.nextInt(5);
        double numG = (double) r;
        double numPeople = (Math.pow(2.0, (numG + 1.0)) - 1.0);
        int finalNumPeople = (int) numPeople; //final
        int finalNumEvents = (finalNumPeople * 4);


        FillResult expectedResult = new FillResult();
        expectedResult.setSuccess(true);
        expectedResult.setUsername(testUser1.getUsername());
        expectedResult.setEvents(finalNumEvents);
        expectedResult.setPersons(finalNumPeople);

        FillResult outputResult = fillService.fill(testUser1.getUsername(), r);
        outputResult.setUsername(testUser1.getUsername());
        assertNotEquals(expectedResult, outputResult);

        FillResult badFillExpectedResult = new FillResult();
        badFillExpectedResult.setUsername("yo");
        badFillExpectedResult.setSuccess(false);
        badFillExpectedResult.setMessage("no such username");

        FillResult badOutputResult = fillService.fill(testUser1.getUsername(), r);
        badOutputResult.setUsername(testUser1.getUsername());

        assertNotEquals(badFillExpectedResult, badOutputResult);
        assertEquals("no such username", badFillExpectedResult.getMessage());

    }
}
