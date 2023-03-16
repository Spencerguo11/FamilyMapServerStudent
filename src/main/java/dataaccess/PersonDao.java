package dataaccess;

import Decode.Loader;
import Decode.NameData;
import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class PersonDao {
    private Connection conn;
    private String[] maleNames;
    private String[] femaleNames;
    private String[] lastNames;



    public PersonDao() {
        femaleNames = new String[147];
        maleNames = new String[142];
        lastNames = new String[150];

        NameData temp = Loader.decodeNames("json/fnames.json"); //gson.fromJson(new FileReader("fnames.json"), StringArray.class);
        NameData temp2 = Loader.decodeNames("json/mnames.json"); //gson.fromJson(new FileReader("mnames.json"), StringArray.class);
        NameData temp3 = Loader.decodeNames("json/snames.json"); //  gson.fromJson(new FileReader("snames.json"), StringArray.class);

        for (int i = 0; i < 147; i++){
            femaleNames[i] = temp.get(i);
        }

        for (int i = 0; i < 142; i++){
            maleNames[i] = temp2.get(i);
        }

        for (int i = 0; i < 150; i++){
            lastNames[i] = temp3.get(i);
        }
    }

    public void setConnection(Connection c) throws DataAccessException {
        conn = c;
    }

    /**
     * Insert new person onto table
     * @param person
     * @throws DataAccessException
     */
    public void insert(Person person) throws DataAccessException {
        String sql = "insert into Person (personID, associatedUsername, firstName, lastName, gender, fatherID, motherID, spouseID) values (?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1,person.getPersonID());
            stmt.setString(2,person.getAssociatedUsername());
            stmt.setString(3,person.getFirstName());
            stmt.setString(4,person.getLastName());
            if(person.getGender().length() != 1 || (!person.getGender().equals("m") && !person.getGender().equals("f"))){
                throw new DataAccessException("error formatting gender input");
            }
            stmt.setString(5,person.getGender());
            stmt.setString(6,person.getFatherID());
            stmt.setString(7,person.getMotherID());
            stmt.setString(8,person.getSpouseID());

            if (stmt.executeUpdate() != 1) {
                throw new DataAccessException("error inserting person");

            }
        }
        catch (SQLException e) {
            throw new DataAccessException("error inserting person");
        }
    }


    public void generateGenerations(Person rootPerson, int numGenerations, EventDao eventDao, int rootYear) throws DataAccessException { //recursive, receives person whose parent's need to be generated
        Person mother = makeParent(rootPerson, "mom");
        Person father = makeParent(rootPerson, "father");
        updateSpouse(father, mother.getPersonID());
        updateSpouse(mother, father.getPersonID());

        int parentYear = eventDao.generateParentsEventandYear(mother, father, rootYear);

        numGenerations--;
        if (numGenerations > 0){
            generateGenerations(mother, numGenerations, eventDao, parentYear);
            generateGenerations(father, numGenerations,eventDao, parentYear);
        }
    }


    public void updateSpouse(Person person, String spouseID) throws DataAccessException{
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
            int r = rand.nextInt(147);

            String motherID = UUID.randomUUID().toString();
            String associatedUsernameOfmother = person.getAssociatedUsername();
            String motherFirstName = femaleNames[r];
            r = rand.nextInt(147);
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
            int r = rand.nextInt(142);

            String fatherID = UUID.randomUUID().toString();
            String associatedUsernameOfFather = person.getAssociatedUsername();
            String fatherFirstName = maleNames[r];
            r = rand.nextInt(142);
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

    public Person[] getAllPerson(String username) throws DataAccessException{ //person model is user's person representation
        ArrayList<Person> personList = new ArrayList<Person>();
        String sql = "select * from Person WHERE associatedUsername = '" + username + "'";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){

            ResultSet rs = null;

            rs = stmt.executeQuery();
            while (rs.next()) {
                Person person = new Person();
                person.setPersonID(rs.getString(1));
                person.setAssociatedUsername(rs.getString(2));
                person.setFirstName(rs.getString(3));
                person.setLastName(rs.getString(4));
                person.setGender(rs.getString(5));
                person.setFatherID(rs.getString(6));
                person.setMotherID(rs.getString(7));
                person.setSpouseID(rs.getString(8));
                personList.add(person);
                person = null;
            }


        }
        catch (SQLException e) {
            throw new DataAccessException("error selecting all Person");
        }
        Person[] personFinal = new Person[personList.size()];
        personFinal = personList.toArray(personFinal);
        return personFinal;
    }

    public Person selectSinglePerson(String personID) throws DataAccessException{
        Person out = new Person();
        String sql = "select * from Person WHERE personID = '" + personID +"'";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){

            ResultSet rs = null;

            rs = stmt.executeQuery();
            while (rs.next()) {
                out.setPersonID(rs.getString(1));
                out.setAssociatedUsername(rs.getString(2));
                out.setFirstName(rs.getString(3));
                out.setLastName(rs.getString(4));
                out.setGender(rs.getString(5));
                out.setFatherID(rs.getString(6));
                out.setMotherID(rs.getString(7));
                out.setSpouseID(rs.getString(8));
            }

        }
        catch (SQLException e) {
            throw new DataAccessException("error getting single person");
        }
        return out;
    }

    /**
     * clear person table
     * @throws DataAccessException
     */
    public void clear() throws DataAccessException {
        try {
            Statement stmt = null;

            stmt = conn.createStatement();

            stmt.executeUpdate("drop table if exists Person");
            stmt.executeUpdate("create table Person (personID VARCHAR(50) NOT NULL PRIMARY KEY, associatedUsername VARCHAR(50) NOT NULL, firstName VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
                    "gender CHAR(1) NOT NULL, fatherID VARCHAR(50), motherID VARCHAR(50), spouseID VARCHAR(50), CONSTRAINT person_info UNIQUE (personID))");


        }
        catch (SQLException e) {
            throw new DataAccessException("error resetting table");
        }
    }


    public void deleteAllPeopleofUser(User user) throws DataAccessException {
        try {
            Statement stmt = null;

            stmt = conn.createStatement();

            stmt.executeUpdate("DELETE FROM Person WHERE associatedUsername = '" + user.getUsername() + "'");


        }
        catch (SQLException e) {
            throw new DataAccessException("error deleting all Person of user");
        }
    }

}
