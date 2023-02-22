package dao;

import Model.Person;
import java.sql.*;

/**
 * A class to handle persons from the database
 */
public class PersonDAO {
    /**
     * connect to the database
     */
    private final Connection conn;// connect to the database

    /**
     * PersonDAO constructor
     * @param conn connect to the database
     */
    public PersonDAO(Connection conn) {
        this.conn = conn;
    }


    /**
     * Create a new person
     * @param person
     */
    public void createPerson(Person person) {}
//    boolean validate(String username, String password){return true;}

    /**
     * Find the person object by the personID
     * @param personId pass in a person ID
     * @return a person object
     */
    public Person getPersonById(String personId){
        // get a person by personId
        return null;}

    /**
     * Clear all persons
     */

    public void clear(){
        // clear all persons
    }

    /**
     * Clear persons by a usernames
     * @param username pass in a username
     */
    public void clearByUsername(String username){
        // clear a person by a username;
    }
}
