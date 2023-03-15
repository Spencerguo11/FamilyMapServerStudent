//package dataAccess;
//
//import Model.User;
//import dao.DataAccessException;
//import dao.Database;
//import dao.UserDAO;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.sql.Connection;
//
//import static org.junit.jupiter.api.Assertions.*;
//
////We will use this to test that our insert method is working and failing in the right ways
//public class UserDAOTest {
//    private Database db;
//    private User bestUser;
//    private UserDAO userDao;
//
//    @BeforeEach
//    public void setUp() throws DataAccessException {
//
//        db = new Database();
//
//        bestUser = new User("JJ", "123", "JJ@byu.edu", "Jay", "Lin",
//                "m", "5");
//
//
//        userDao = new UserDAO();
//
//        userDao.clear();
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
//        userDao.insert(bestUser);
//
//        boolean compareTest = userDao.find(bestUser.getUsername());
//
//        assertNotNull(compareTest);
//
//        assertEquals(bestUser, compareTest);
//    }
//
//    @Test
//    public void insertFail() throws DataAccessException {
//
//        userDao.insert(bestUser);
//
//        assertThrows(DataAccessException.class, () -> userDao.insert(bestUser));
//    }
//
//
//    @Test
//    public void findPass() throws DataAccessException {
//
//        userDao.insert(bestUser);
//
//        boolean compareTest = userDao.find(bestUser.getUsername());
//
//        assertNotNull(compareTest);
//
//        assertEquals(bestUser, compareTest);
//    }
//
//    @Test
//    public void findFail() throws DataAccessException {
//        boolean person = userDao.find(null);
//        assertNull(person);
//    }
//
//    @Test
//    public void clearPass() throws DataAccessException {
//
//        User person1 = new User("CJ", "123", "CJ@byu.edu", "Ja", "Lin",
//                "m", "1");
//        User person2 = new User("DJ", "123", "DJ@byu.edu", "Jy", "Lin",
//                "m", "2");
//        User person3 = new User("EJ", "123", "EJ@byu.edu", "J", "Lin",
//                "m", "3");
//
//        userDao.insert(person1);
//        userDao.insert(person2);
//        userDao.insert(person3);
//
//        boolean compareTest = userDao.find(person1.getUsername());
//        assertNotNull(compareTest);
//        assertEquals(person1, compareTest);
//
//        userDao.clear();
//
//        boolean failedPerson = userDao.find(person1.getUsername());
//        assertNull(failedPerson);
//
//    }
//
//
//
//
//}
