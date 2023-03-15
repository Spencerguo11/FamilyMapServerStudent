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


    /**
     * find person using personID in person table
     * @param personID
     * @return
     * @throws DataAccessException
     */
    public boolean find(String personID) throws DataAccessException {
        String sql = "select * from Person WHERE personID = '" + personID + "'";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){

            ResultSet rs = null;

            rs = stmt.executeQuery();

            if (!rs.next() ) {
                throw new DataAccessException("error finding personID");
            } else {
                return true;
            }

        }
        catch (SQLException e) {
            throw new DataAccessException("error finding personID");
        }
    }


    public void generateGenerations(Person orphan, int numGenerations, EventDao eventDao, int orphanBirthYear) throws DataAccessException { //recursive, receives person whose parent's need to be generated
        Person mother = makeMother(orphan);
        Person father = makeFather(orphan);
        updateSpouse(father, mother.getPersonID());
        updateSpouse(mother, father.getPersonID());

        int birthDateOfBoth = eventDao.generateEventDataParents(mother, father, orphanBirthYear);

        numGenerations--;
        if (numGenerations > 0){
            generateGenerations(mother, numGenerations, eventDao, birthDateOfBoth);
            generateGenerations(father, numGenerations,eventDao, birthDateOfBoth);
        }
    }

    public void updateMother(Person p, String motherID) throws DataAccessException{
        try {
            Statement stmt = null;
            try {

                String sql = "UPDATE Person\n" +
                        "SET motherID = '" + motherID + "' " +
                        "WHERE personID = '" + p.getPersonID() + "'";
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
            throw new DataAccessException("error updating mother");
        }
    }

    public void updateFather(Person p, String fatherID) throws DataAccessException{
        try {
            Statement stmt = null;
            try {

                String sql = "UPDATE Person\n" +
                        "SET fatherID = '" + fatherID + "' " +
                        "WHERE personID = '" + p.getPersonID() + "'";
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
            throw new DataAccessException("error updating father");
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

    public Person makeMother(Person orphan) throws DataAccessException {

        Random rand = new Random();
        int r = rand.nextInt(146);

        String motherID = UUID.randomUUID().toString();
        String personOfMother = orphan.getAssociatedUsername();
        String motherFirstName = femaleNames[r];
        r = rand.nextInt(149);
        String motherLastName = lastNames[r];
        String gender = "f";

        updateMother(orphan, motherID);


        Person mother = new Person();
        mother.setPersonID(motherID);
        mother.setAssociatedUsername(personOfMother);
        mother.setFirstName(motherFirstName);
        mother.setLastName(motherLastName);
        mother.setGender(gender);

        insert(mother);

        return mother;
    }

    public Person makeFather(Person orphan) throws DataAccessException {
        Random rand = new Random();
        int r = rand.nextInt(141);

        String fatherID = UUID.randomUUID().toString();
        String personOfFather = orphan.getAssociatedUsername();
        String fatherFirstName = maleNames[r];
        String fatherLastName = orphan.getLastName();
        String gender = "m";

        updateFather(orphan, fatherID);


        Person father = new Person();
        father.setPersonID(fatherID);
        father.setAssociatedUsername(personOfFather);
        father.setFirstName(fatherFirstName);
        father.setLastName(fatherLastName);
        father.setGender(gender);

        insert(father);

        return father;
    }

    public Person[] selectAllPersons(String username) throws DataAccessException{ //person model is user's person representation
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

//    public String tableToString() throws DataAccessException{
//        StringBuilder out = new StringBuilder();
//        try {
//            PreparedStatement stmt = null;
//            ResultSet rs = null;
//            try {
//                String sql = "select * from Person";
//                stmt = conn.prepareStatement(sql);
//
//                rs = stmt.executeQuery();
//                while (rs.next()) {
//                    String personID = rs.getString(1);
//                    String associatedUsername = rs.getString(2);
//                    String firstName = rs.getString(3);
//                    String lastName = rs.getString(4);
//                    String gender = rs.getString(5);
//                    String fatherID = rs.getString(6);
//                    String motherID = rs.getString(7);
//                    String spouseID = rs.getString(8);
//                    out.append((personID + "\t" + associatedUsername + "\t" + firstName + "\t" + lastName + "\t"
//                            + gender + "\t" + fatherID + "\t" + motherID + "\t" + spouseID + "\n"));
//                }
//            }
//            finally {
//                if (rs != null) {
//                    rs.close();
//                }
//                if (stmt != null) {
//                    stmt.close();
//                }
//            }
//        }
//        catch (SQLException e) {
//            throw new DataAccessException("error toString table");
//        }
//        return out.toString();
//    }

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
