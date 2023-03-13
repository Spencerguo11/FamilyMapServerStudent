package dao;

import JsonClasses.NameData;
import JsonClasses.Loader;
import Model.Person;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * A class to handle persons from the database
 */
public class PersonDAO {
    /**
     * connect to the database
     */
    private Connection conn;// connect to the database
    private String[] maleList;
    private String[] femaleList;
    private String[] lastNames;

    public PersonDAO() {


        maleList = new String[145];
        femaleList = new String[147];
        lastNames = new String[152];

        NameData femaleNameData = Loader.decodeNames("json/fnames.json");
        NameData maleNameData = Loader.decodeNames("json/mnames.json");
        NameData lastNameData = Loader.decodeNames("json/snames.json");

        for (int i = 0; i < maleNameData.getLength(); i++) {
            maleList[i] = maleNameData.get(i);
        }

        for (int i = 0; i < femaleNameData.getLength(); i++) {
            femaleList[i] = femaleNameData.get(i);
        }

        for (int i = 0; i < lastNameData.getLength(); i++) {
            lastNames[i] = lastNameData.get(i);
        }

    }

    public void setConnection(Connection conn) {
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

//    public void clear() throws DataAccessException {
//        try {
//            Statement stmt = null;
//            try {
//                stmt = conn.createStatement();
//
//                stmt.executeUpdate("drop table if exists Person");
//                stmt.executeUpdate("create table Person (personID VARCHAR(255) NOT NULL PRIMARY KEY, associatedUsername VARCHAR(255) NOT NULL, firstName VARCHAR(255) NOT NULL, lastName VARCHAR(255) NOT NULL, " +
//                        "gender CHAR(1) NOT NULL, fatherID VARCHAR(255), motherID VARCHAR(255), spouseID VARCHAR(255), CONSTRAINT person_info UNIQUE (personID))");
//
//            }
//
//            finally {
//                if (stmt != null) {
//                    stmt.close();
//                    stmt = null;
//                }
//            }
//        }
//        catch (SQLException e) {
//            throw new DataAccessException("error resetting table");
//        }
//    }
    public void clear() throws DataAccessException{
        String sql = "DELETE FROM Person;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the user table");
        }
    }

    public void clearByUsername(String username) throws DataAccessException{
        // clear people by a username;
        String sql = "DELETE FROM Person WHERE associatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting an event into the database");
        }
    }

    public List<Person> listAllPeopleByUsername(String username) throws DataAccessException{
        List<Person> personList = new ArrayList<>();
        ResultSet rs;
        String sql = "SELECT * FROM Person WHERE associatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            while(rs.next()) {

                Person person = new Person (rs.getString("AssociatedUsername"), rs.getString("PersonID"),
                        rs.getString("FirstName"), rs.getString("LastName"), rs.getString("Gender"),
                        rs.getString("FatherID"), rs.getString("MotherID"), rs.getString("SpouseID"));

                personList.add(person);

            }
            return personList;
        }catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting an event into the database");
        }
    }


    public void generateAllGenerations(Person person, int gen, EventDAO eventDAO, int birthYear) throws DataAccessException {
        //create mom and dad
        Person mother = makeParent(person, "mom");
        Person father = makeParent(person, "dad");

        updateSpouseID(father, mother.getPersonID());
        updateSpouseID(mother, father.getPersonID());

        int parentBirthYear = eventDAO.generateParentEvents(father,mother,birthYear);
        gen--;
        if (gen > 0) {
            generateAllGenerations(mother, gen, eventDAO,parentBirthYear);
            generateAllGenerations(father,gen,eventDAO,parentBirthYear);
        }

    }

    public void updateSpouseID(Person person, String spouseID) throws DataAccessException {
        try {
            Statement stmt = null;
            try {

                String sql = "UPDATE Person\n" +
                        "SET spouseID = '" + spouseID + "' " +
                        "WHERE personID = '" + person.getPersonID() + "'";
                stmt = conn.createStatement();
                stmt.executeUpdate(sql);
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
        catch (SQLException e) {
            throw new DataAccessException("error updating spouse");
        }
    }

    private Person makeParent(Person person, String parentType) throws DataAccessException {
        if (parentType.equals("mom")) {
            Random rand = new Random();
            int r = rand.nextInt(146);

            String motherID = UUID.randomUUID().toString();
            String associatedUsernameOfmother = person.getAssociatedUsername();
            String motherFirstName = femaleList[r];
            r = rand.nextInt(149);
            String motherLastName = lastNames[r];


            //Updates person's mother
            updateParent(person, motherID, "mother");
            //Make mother model
            Person mother = new Person();
            mother.setPersonID(motherID);
            mother.setAssociatedUsername(associatedUsernameOfmother);
            mother.setFirstName(motherFirstName);
            mother.setLastName(motherLastName);
            mother.setGender("f");

            insert(mother);

            return mother;
        } else {

            Random rand = new Random();
            int r = rand.nextInt(146);

            String fatherID = UUID.randomUUID().toString();
            String associatedUsernameOfFather = person.getAssociatedUsername();
            String fatherFirstName = maleList[r];
            r = rand.nextInt(149);
            String fatherLastName = lastNames[r];

            //Updates person's father
            updateParent(person, fatherID, "father");
            //Make father model
            Person father = new Person();
            father.setPersonID(fatherID);
            father.setAssociatedUsername(associatedUsernameOfFather);
            father.setFirstName(fatherFirstName);
            father.setLastName(fatherLastName);
            father.setGender("m");

            insert(father);

            return father;

        }
    }

    private void updateParent(Person person, String parentID, String parentType) throws DataAccessException {
        try {
            Statement stmt = null;
            try {

                String sql = "UPDATE Person\n" +
                        "SET " + parentType.toLowerCase() + "ID = '" + parentID + "' " + // modified
                        "WHERE personID = '" + person.getPersonID() + "'";
                stmt = conn.createStatement();
                stmt.executeUpdate(sql);
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
        catch (SQLException e) {
            throw new DataAccessException("error updating parent");
        }
    }
}
