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
//    private Database database;
//    private Person bestPerson;
//    private PersonDAO personDao;
//
//    @BeforeEach
//    public void setUp() throws DataAccessException {
//
//        database = new Database();
//
//        bestPerson = new Person("User", "123456789", "Kyle", "Kim",
//                "m", "123", "456", "789");
//
//
//        Connection conn = database.getConnection();
//
//        personDao = new PersonDAO();
//
//        personDao.clear();
//    }
//
//    @AfterEach
//    public void tearDown() throws DataAccessException {
//
//        database.closeConnection(false);
//    }
//
//    @Test
//    public void insertPass() throws DataAccessException {
//
//        personDao.insert(bestPerson);
//
//        boolean compareTest = personDao.find(bestPerson.getPersonID());
//
//        assertNotNull(compareTest);
//
//        assertEquals(bestPerson, compareTest);
//    }
//
//    @Test
//    public void insertFail() throws DataAccessException {
//
//        personDao.insert(bestPerson);
//
//        assertThrows(DataAccessException.class, () -> personDao.insert(bestPerson));
//    }
//
//
//    @Test
//    public void findPass() throws DataAccessException {
//
//        personDao.insert(bestPerson);
//
//        boolean compareTest = personDao.find(bestPerson.getPersonID());
//
//        assertNotNull(compareTest);
//
//        assertEquals(bestPerson, compareTest);
//    }
//
//    @Test
//    public void findFail() throws DataAccessException {
//        boolean person = personDao.find(null);
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
//        personDao.insert(person1);
//        personDao.insert(person2);
//        personDao.insert(person3);
//
//        boolean compareTest = personDao.find(person1.getPersonID());
//        assertNotNull(compareTest);
//        assertEquals(person1, compareTest);
//
//        personDao.clear();
//
//        boolean failedPerson = personDao.find(person1.getPersonID());
//        assertNull(failedPerson);
//
//    }
//
//
//
//
//
//}
