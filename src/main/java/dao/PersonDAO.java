package dao;

import Model.Event;
import Model.Person;
import java.sql.*;

/**
 * A class to handle persons from the database
 */
public class PersonDAO {
    /**
     * connect to the database
     */
    private static Connection conn;// connect to the database

    /**
     * PersonDAO constructor
     * @param conn connect to the database
     */
    public PersonDAO(Connection conn) {
        this.conn = conn;
    }

    public void insert(Person person) throws DataAccessException{
        String sql = "INSERT INTO Person (AssociatedUsername, PersonID, FirstName, LastName, " +
                "Gender, FatherID, MotherID, SpouseID) VALUES(?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, person.getAssociatedUsername());
            stmt.setString(2, person.getPersonID());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getMotherID());
            stmt.setString(8, person.getSpouseID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting a person into the database");
        }

    }


    public Person find(String personID) throws DataAccessException{
        Person person;
        ResultSet rs;
        String sql = "SELECT * FROM Person WHERE personID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                person = new Person (rs.getString("AssociatedUsername"), rs.getString("PersonID"),
                        rs.getString("FirstName"), rs.getString("LastName"), rs.getString("Gender"),
                        rs.getString("FatherID"), rs.getString("MotherID"), rs.getString("SpouseID"));
                return person;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding a person in the database");
        }
    }

    /**
     * Clear all persons
     */

    public void clear() throws DataAccessException{
        String sql = "DELETE FROM Person";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the person table");
        }
    }



//
//    /**
//     * Create a new person
//     * @param person
//     */
//    public void createPerson(Person person) {}
////    boolean validate(String username, String password){return true;}
//
//    /**
//     * Find the person object by the personID
//     * @param personId pass in a person ID
//     * @return a person object
//     */
//    public Person getPersonById(String personId){
//        // get a person by personId
//        return null;}
//
//
//    /**
//     * Clear persons by a usernames
//     * @param username pass in a username
//     */
//    public void clearByUsername(String username){
//        // clear a person by a username;
//    }


}
