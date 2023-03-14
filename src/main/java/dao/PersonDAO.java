//package dao;
//
//import JsonClasses.NameData;
//import JsonClasses.Loader;
//import Model.Person;
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//import java.util.UUID;
//
///**
// * A class to handle persons from the database
// */
//public class PersonDAO {
//    /**
//     * connect to the database
//     */
//    private Connection conn;// connect to the database
//    private String[] maleList;
//    private String[] femaleList;
//    private String[] lastNames;
//
//    public PersonDAO() {
//
//
//        maleList = new String[145];
//        femaleList = new String[147];
//        lastNames = new String[152];
//
//        NameData femaleNameData = Loader.decodeNames("json/fnames.json");
//        NameData maleNameData = Loader.decodeNames("json/mnames.json");
//        NameData lastNameData = Loader.decodeNames("json/snames.json");
//
//        for (int i = 0; i < maleNameData.getLength(); i++) {
//            maleList[i] = maleNameData.get(i);
//        }
//
//        for (int i = 0; i < femaleNameData.getLength(); i++) {
//            femaleList[i] = femaleNameData.get(i);
//        }
//
//        for (int i = 0; i < lastNameData.getLength(); i++) {
//            lastNames[i] = lastNameData.get(i);
//        }
//
//    }
//
//    public void setConnection(Connection conn) {
//        this.conn = conn;
//    }
//
//    public void insert(Person person) throws DataAccessException{
//        String sql = "INSERT INTO Person (AssociatedUsername, PersonID, FirstName, LastName, " +
//                "Gender, FatherID, MotherID, SpouseID) VALUES(?,?,?,?,?,?,?,?)";
//        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//            //Using the statements built-in set(type) functions we can pick the question mark we want
//            //to fill in and give it a proper value. The first argument corresponds to the first
//            //question mark found in our sql String
//            stmt.setString(1, person.getAssociatedUsername());
//            stmt.setString(2, person.getPersonID());
//            stmt.setString(3, person.getFirstName());
//            stmt.setString(4, person.getLastName());
//            stmt.setString(5, person.getGender());
//            stmt.setString(6, person.getFatherID());
//            stmt.setString(7, person.getMotherID());
//            stmt.setString(8, person.getSpouseID());
//
//            stmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw new DataAccessException("Error encountered while inserting a person into the database");
//        }
//
//    }
//
//
//    public Person find(String personID) throws DataAccessException{
//        Person person;
//        ResultSet rs;
//        String sql = "SELECT * FROM Person WHERE personID = ?;";
//        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setString(1, personID);
//            rs = stmt.executeQuery();
//            if (rs.next()) {
//                person = new Person (rs.getString("AssociatedUsername"), rs.getString("PersonID"),
//                        rs.getString("FirstName"), rs.getString("LastName"), rs.getString("Gender"),
//                        rs.getString("FatherID"), rs.getString("MotherID"), rs.getString("SpouseID"));
//                return person;
//            } else {
//                return null;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw new DataAccessException("Error encountered while finding a person in the database");
//        }
//    }
//
//    /**
//     * Clear all persons
//     */
//
////    public void clear() throws DataAccessException {
////        try {
////            Statement stmt = null;
////            try {
////                stmt = conn.createStatement();
////
////                stmt.executeUpdate("drop table if exists Person");
////                stmt.executeUpdate("create table Person (personID VARCHAR(255) NOT NULL PRIMARY KEY, associatedUsername VARCHAR(255) NOT NULL, firstName VARCHAR(255) NOT NULL, lastName VARCHAR(255) NOT NULL, " +
////                        "gender CHAR(1) NOT NULL, fatherID VARCHAR(255), motherID VARCHAR(255), spouseID VARCHAR(255), CONSTRAINT person_info UNIQUE (personID))");
////
////            }
////
////            finally {
////                if (stmt != null) {
////                    stmt.close();
////                    stmt = null;
////                }
////            }
////        }
////        catch (SQLException e) {
////            throw new DataAccessException("error resetting table");
////        }
////    }
//    public void clear() throws DataAccessException{
//        String sql = "DELETE FROM Person;";
//        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw new DataAccessException("Error encountered while clearing the user table");
//        }
//    }
//
//    public void clearByUsername(String username) throws DataAccessException{
//        // clear people by a username;
//        String sql = "DELETE FROM Person WHERE associatedUsername = ?;";
//        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setString(1, username);
//            stmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw new DataAccessException("Error encountered while inserting an event into the database");
//        }
//    }
//
//    public List<Person> listAllPeopleByUsername(String username) throws DataAccessException{
//        List<Person> personList = new ArrayList<>();
//        ResultSet rs;
//        String sql = "SELECT * FROM Person WHERE associatedUsername = ?;";
//        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setString(1, username);
//            rs = stmt.executeQuery();
//            while(rs.next()) {
//
//                Person person = new Person (rs.getString("AssociatedUsername"), rs.getString("PersonID"),
//                        rs.getString("FirstName"), rs.getString("LastName"), rs.getString("Gender"),
//                        rs.getString("FatherID"), rs.getString("MotherID"), rs.getString("SpouseID"));
//
//                personList.add(person);
//
//            }
//            return personList;
//        }catch (SQLException e) {
//            e.printStackTrace();
//            throw new DataAccessException("Error encountered while inserting an event into the database");
//        }
//    }
//
//
//    public void generateAllGenerations(Person person, int gen, EventDAO eventDAO, int birthYear) throws DataAccessException {
//        //create mom and dad
//        Person mother = makeParent(person, "mom");
//        Person father = makeParent(person, "dad");
//
//        updateSpouseID(father, mother.getPersonID());
//        updateSpouseID(mother, father.getPersonID());
//
//        int parentBirthYear = eventDAO.generateEventDataParents(father,mother,birthYear);
//        gen--;
//        if (gen > 0) {
//            generateAllGenerations(mother, gen, eventDAO,parentBirthYear);
//            generateAllGenerations(father,gen,eventDAO,parentBirthYear);
//        }
//
//    }
//
//    public void updateSpouseID(Person person, String spouseID) throws DataAccessException {
//        try {
//            Statement stmt = null;
//            try {
//
//                String sql = "UPDATE Person\n" +
//                        "SET spouseID = '" + spouseID + "' " +
//                        "WHERE personID = '" + person.getPersonID() + "'";
//                stmt = conn.createStatement();
//                stmt.executeUpdate(sql);
//            }
//            finally {
//                if (stmt != null) {
//                    stmt.close();
//                }
//            }
//        }
//        catch (SQLException e) {
//            throw new DataAccessException("error updating spouse");
//        }
//    }
//
//    private Person makeParent(Person person, String parentType) throws DataAccessException {
//        if (parentType.equals("mom")) {
//            Random rand = new Random();
//            int r = rand.nextInt(146);
//
//            String motherID = UUID.randomUUID().toString();
//            String associatedUsernameOfmother = person.getAssociatedUsername();
//            String motherFirstName = femaleList[r];
//            r = rand.nextInt(149);
//            String motherLastName = lastNames[r];
//
//
//            //Updates person's mother
//            updateParent(person, motherID, "mother");
//            //Make mother model
//            Person mother = new Person();
//            mother.setPersonID(motherID);
//            mother.setAssociatedUsername(associatedUsernameOfmother);
//            mother.setFirstName(motherFirstName);
//            mother.setLastName(motherLastName);
//            mother.setGender("f");
//
//            insert(mother);
//
//            return mother;
//        } else {
//
//            Random rand = new Random();
//            int r = rand.nextInt(146);
//
//            String fatherID = UUID.randomUUID().toString();
//            String associatedUsernameOfFather = person.getAssociatedUsername();
//            String fatherFirstName = maleList[r];
//            r = rand.nextInt(149);
//            String fatherLastName = lastNames[r];
//
//            //Updates person's father
//            updateParent(person, fatherID, "father");
//            //Make father model
//            Person father = new Person();
//            father.setPersonID(fatherID);
//            father.setAssociatedUsername(associatedUsernameOfFather);
//            father.setFirstName(fatherFirstName);
//            father.setLastName(fatherLastName);
//            father.setGender("m");
//
//            insert(father);
//
//            return father;
//
//        }
//    }
//
//    private void updateParent(Person person, String parentID, String parentType) throws DataAccessException {
//        try {
//            Statement stmt = null;
//            try {
//
//                String sql = "UPDATE Person\n" +
//                        "SET " + parentType.toLowerCase() + "ID = '" + parentID + "' " + // modified
//                        "WHERE personID = '" + person.getPersonID() + "'";
//                stmt = conn.createStatement();
//                stmt.executeUpdate(sql);
//            }
//            finally {
//                if (stmt != null) {
//                    stmt.close();
//                }
//            }
//        }
//        catch (SQLException e) {
//            throw new DataAccessException("error updating parent");
//        }
//    }
//}


package dao;


import JsonClasses.Loader;
import JsonClasses.NameData;
import Model.Person;
import Model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class PersonDAO {
    private Connection conn;
    private String[] maleNames;
    private String[] femaleNames;
    private String[] lastNames;



    public PersonDAO() {
        maleNames = new String[145];
        femaleNames = new String[147];
        lastNames = new String[152];

        NameData temp = Loader.decodeNames("json/fnames.json"); //gson.fromJson(new FileReader("fnames.json"), StringArray.class);
        NameData temp2 = Loader.decodeNames("json/mnames.json"); //gson.fromJson(new FileReader("mnames.json"), StringArray.class);
        NameData temp3 = Loader.decodeNames("json/snames.json"); //  gson.fromJson(new FileReader("snames.json"), StringArray.class);

        for (int i = 0; i < temp.getLength(); i++){
            femaleNames[i] = temp.get(i);
        }

        for (int i = 0; i < temp2.getLength(); i++){
            maleNames[i] = temp2.get(i);
        }

        for (int i = 0; i < temp3.getLength(); i++){
            lastNames[i] = temp3.get(i);
        }
    }

    public void setConnection(Connection c) throws DataAccessException{
        conn = c;
    }

    /**
     * Insert new person onto table
     * @param person
     * @throws DataAccessException
     */
    public void insert(Person person) throws DataAccessException {
        try {
            PreparedStatement stmt = null;
            try {

                String sql = "insert into Person (personID, associatedUsername, firstName, lastName, gender, fatherID, motherID, spouseID) values (?,?,?,?,?,?,?,?)";
                stmt = conn.prepareStatement(sql);

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
            finally {
                if (stmt != null) {
                    stmt.close();
                }
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
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "select * from Person WHERE personID = '" + personID + "'";
                stmt = conn.prepareStatement(sql);

                rs = stmt.executeQuery();

                if (!rs.next() ) {
                    throw new DataAccessException("error finding personID");
                } else {
                    return true;
                }
            }
            finally {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
        catch (SQLException e) {
            throw new DataAccessException("error finding personID");
        }
    }


    public void generateGenerations(Person orphan, int numGenerations, EventDAO eDao, int orphanBirthYear) throws DataAccessException { //recursive, receives person whose parent's need to be generated
        Person mother = makeMother(orphan); //makes orphan's mother
        Person father = makeFather(orphan); //makes orphan's father
        updateSpouse(father, mother.getPersonID()); //adds mother to be father's spouse
        updateSpouse(mother, father.getPersonID());  //adds father to be mother's spouse

        //Now make events for parents, root's events were taken care of in register, in other cases, orphan would be a father or mother already.

        int birthDateOfBoth = eDao.generateEventDataParents(mother, father, orphanBirthYear); //Generates events for parents


        numGenerations--;
        if (numGenerations > 0){
            generateGenerations(mother, numGenerations, eDao, birthDateOfBoth);
            generateGenerations(father, numGenerations,eDao, birthDateOfBoth);
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

    public Person makeMother(Person orphan) throws DataAccessException{
        //Orphan will always have a personID already, even user
        Random rand = new Random();
        int r = rand.nextInt(146);

        String motherID = UUID.randomUUID().toString();
        String descedantOfMother = orphan.getAssociatedUsername();
        String motherFirstName = femaleNames[r];
        r = rand.nextInt(151);
        String motherLastName = lastNames[r];
        String gender = "f";

        //Updates orphan's mother
        updateMother(orphan, motherID);

        //Make mother model
        Person mother = new Person();
        mother.setPersonID(motherID);
        mother.setAssociatedUsername(descedantOfMother);
        mother.setFirstName(motherFirstName);
        mother.setLastName(motherLastName);
        mother.setGender(gender);

        //insert mothermodel into table
        insert(mother);

        return mother;
    }

    public Person makeFather(Person orphan) throws DataAccessException{
        //Orphan will always have a personID already, even user
        Random rand = new Random();
        int r = rand.nextInt(147);

        String fatherID = UUID.randomUUID().toString();
        String descedantOfFather = orphan.getAssociatedUsername();
        String fatherFirstName = maleNames[r];
        String fatherLastName = orphan.getLastName();
        String gender = "m";

        //Updates orphan's father
        updateFather(orphan, fatherID);

        //Make father model
        Person father = new Person();
        father.setPersonID(fatherID);
        father.setAssociatedUsername(descedantOfFather);
        father.setFirstName(fatherFirstName);
        father.setLastName(fatherLastName);
        father.setGender(gender);

        //insert father model into table
        insert(father);

        return father;
    }

    public Person[] selectAllPersons(String username) throws DataAccessException{ //person model is user's person representation
        ArrayList<Person> personList = new ArrayList<Person>();

        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "select * from Person WHERE associatedUsername = '" + username + "'";
                stmt = conn.prepareStatement(sql);

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
            finally {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
        catch (SQLException e) {
            throw new DataAccessException("error selecting all people");
        }
        Person[] personFinal = new Person[personList.size()];
        personFinal = personList.toArray(personFinal);
        return personFinal;
    }

    public Person selectSinglePerson(String personID) throws DataAccessException{
        Person out = new Person();

        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "select * from Person WHERE personID = '" + personID +"'";
                stmt = conn.prepareStatement(sql);

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
            finally {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
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
    public void clear() throws DataAccessException
    {
        try {
            Statement stmt = null;
            try {
                stmt = conn.createStatement();

                stmt.executeUpdate("drop table if exists Person");
                stmt.executeUpdate("create table Person (personID VARCHAR(50) NOT NULL PRIMARY KEY, associatedUsername VARCHAR(50) NOT NULL, firstName VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
                        "gender CHAR(1) NOT NULL, fatherID VARCHAR(50), motherID VARCHAR(50), spouseID VARCHAR(50), CONSTRAINT person_info UNIQUE (personID))");

            }

            finally {
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
            }
        }
        catch (SQLException e) {
            throw new DataAccessException("error resetting table");
        }
    }

    public String tableToString() throws DataAccessException{
        StringBuilder out = new StringBuilder();
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "select * from Person";
                stmt = conn.prepareStatement(sql);

                rs = stmt.executeQuery();
                while (rs.next()) {
                    String personID = rs.getString(1);
                    String associatedUsername = rs.getString(2);
                    String firstName = rs.getString(3);
                    String lastName = rs.getString(4);
                    String gender = rs.getString(5);
                    String fatherID = rs.getString(6);
                    String motherID = rs.getString(7);
                    String spouseID = rs.getString(8);
                    out.append((personID + "\t" + associatedUsername + "\t" + firstName + "\t" + lastName + "\t"
                            + gender + "\t" + fatherID + "\t" + motherID + "\t" + spouseID + "\n"));
                }
            }
            finally {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
        catch (SQLException e) {
            throw new DataAccessException("error toString table");
        }
        return out.toString();
    }

    public void deleteAllPeopleofUser(User user) throws DataAccessException{
        try {
            Statement stmt = null;
            try {
                stmt = conn.createStatement();

                stmt.executeUpdate("DELETE FROM Person WHERE associatedUsername = '" + user.getUsername() + "'");

            }
            finally {
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
            }
        }
        catch (SQLException e) {
            throw new DataAccessException("error deleting all people of user");
        }
    }

}
