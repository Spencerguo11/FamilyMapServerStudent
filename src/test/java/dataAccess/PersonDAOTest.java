//package dataAccess;
//
//import Model.Person;
//import dao.DataAccessException;
//import dao.Database;
//import dao.PersonDAO;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.sql.Connection;
//
//import static org.junit.jupiter.api.Assertions.*;
//
////We will use this to test that our insert method is working and failing in the right ways
//public class PersonDAOTest {
//    private Database db;
//    private Person bestPerson;
//    private PersonDAO pDao;
//
//    @BeforeEach
//    public void setUp() throws DataAccessException {
//
//        db = new Database();
//
//        bestPerson = new Person("User", "123456789", "Kyle", "Kim",
//                "m", "123", "456", "789");
//
//
//        Connection conn = db.getConnection();
//
//        pDao = new PersonDAO();
//
//        pDao.clear();
//    }
//
//    @AfterEach
//    public void tearDown() throws DataAccessException {
//
//        db.closeConnection(false);
//    }
//
//    @Test
//    public void insertPass() throws DataAccessException {
//
//        pDao.insert(bestPerson);
//
//        boolean compareTest = pDao.find(bestPerson.getPersonID());
//
//        assertNotNull(compareTest);
//
//        assertEquals(bestPerson, compareTest);
//    }
//
//    @Test
//    public void insertFail() throws DataAccessException {
//
//        pDao.insert(bestPerson);
//
//        assertThrows(DataAccessException.class, () -> pDao.insert(bestPerson));
//    }
//
//
//    @Test
//    public void findPass() throws DataAccessException {
//
//        pDao.insert(bestPerson);
//
//        boolean compareTest = pDao.find(bestPerson.getPersonID());
//
//        assertNotNull(compareTest);
//
//        assertEquals(bestPerson, compareTest);
//    }
//
//    @Test
//    public void findFail() throws DataAccessException {
//        boolean person = pDao.find(null);
//        assertNull(person);
//    }
//
//    @Test
//    public void clearPass() throws DataAccessException {
//
//        Person person1 = new Person("User", "1", "Kyle", "Kim",
//                "m", "123", "456", "789");
//        Person person2 = new Person("User", "2", "Jane", "Kim",
//                "m", "123", "456", "789");
//        Person person3 = new Person("User", "3", "Kai", "Kim",
//                "m", "123", "456", "789");
//
//        pDao.insert(person1);
//        pDao.insert(person2);
//        pDao.insert(person3);
//
//        boolean compareTest = pDao.find(person1.getPersonID());
//        assertNotNull(compareTest);
//        assertEquals(person1, compareTest);
//
//        pDao.clear();
//
//        boolean failedPerson = pDao.find(person1.getPersonID());
//        assertNull(failedPerson);
//
//    }
//
//
//
//
//
//}
