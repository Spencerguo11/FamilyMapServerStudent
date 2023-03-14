package dataaccess;

import Decode.Decoder;
import Decode.StringArray;
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

        StringArray temp = Decoder.decodeNames("json/fnames.json"); //gson.fromJson(new FileReader("fnames.json"), StringArray.class);
        StringArray temp2 = Decoder.decodeNames("json/mnames.json"); //gson.fromJson(new FileReader("mnames.json"), StringArray.class);
        StringArray temp3 = Decoder.decodeNames("json/snames.json"); //  gson.fromJson(new FileReader("snames.json"), StringArray.class);

        for (int i = 0; i < 147; i++){
            femaleNames[i] = temp.getValueAt(i);
        }

        for (int i = 0; i < 142; i++){
            maleNames[i] = temp2.getValueAt(i);
        }

        for (int i = 0; i < 150; i++){
            lastNames[i] = temp3.getValueAt(i);
        }
    }

    public void setConnection(Connection c) throws Database.DatabaseException {
        conn = c;
    }

    /**
     * Insert new person onto table
     * @param person
     * @throws DataAccessException
     */
    public void insert(Person person) throws Database.DatabaseException {
        try {
            PreparedStatement stmt = null;
            try {

                String sql = "insert into people (personID, descendant, firstName, lastName, gender, father, mother, spouse) values (?,?,?,?,?,?,?,?)";
                stmt = conn.prepareStatement(sql);

                stmt.setString(1,person.getPersonID());
                stmt.setString(2,person.getAssociatedUsername());
                stmt.setString(3,person.getFirstName());
                stmt.setString(4,person.getLastName());
                if(person.getGender().length() != 1 || (!person.getGender().equals("m") && !person.getGender().equals("f"))){
                    throw new Database.DatabaseException("error formatting gender input");
                }
                stmt.setString(5,person.getGender());
                stmt.setString(6,person.getFatherID());
                stmt.setString(7,person.getMotherID());
                stmt.setString(8,person.getSpouseID());

                if (stmt.executeUpdate() != 1) {
                    throw new Database.DatabaseException("error inserting person");
                }
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
        catch (SQLException e) {
            throw new Database.DatabaseException("error inserting person");
        }
    }


    /**
     * find person using personID in person table
     * @param personID
     * @return
     * @throws DataAccessException
     */
    public boolean find(String personID) throws Database.DatabaseException {
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "select * from people WHERE personID = '" + personID + "'";
                stmt = conn.prepareStatement(sql);

                rs = stmt.executeQuery();

                if (!rs.next() ) {
                    throw new Database.DatabaseException("error finding personID");
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
            throw new Database.DatabaseException("error finding personID");
        }
    }


    public void generateGenerations(Person orphan, int numGenerations, EventDao eDao, int orphanBirthYear) throws Database.DatabaseException { //recursive, receives person whose parent's need to be generated
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

    public void updateMother(Person p, String motherID) throws Database.DatabaseException{
        try {
            Statement stmt = null;
            try {

                String sql = "UPDATE people\n" +
                        "SET mother = '" + motherID + "' " +
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
            throw new Database.DatabaseException("error updating mother");
        }
    }

    public void updateFather(Person p, String fatherID) throws Database.DatabaseException{
        try {
            Statement stmt = null;
            try {

                String sql = "UPDATE people\n" +
                        "SET father = '" + fatherID + "' " +
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
            throw new Database.DatabaseException("error updating father");
        }
    }

    public void updateSpouse(Person person, String spouseID) throws Database.DatabaseException{
        try {
            Statement stmt = null;
            try {

                String sql = "UPDATE people\n" +
                        "SET spouse = '" + spouseID + "' " +
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
            throw new Database.DatabaseException("error updating spouse");
        }
    }

    public Person makeMother(Person orphan) throws Database.DatabaseException {
        //Orphan will always have a personID already, even user
        Random rand = new Random();
        int r = rand.nextInt(146);

        String motherID = UUID.randomUUID().toString();
        String descedantOfMother = orphan.getAssociatedUsername();
        String motherFirstName = femaleNames[r];
        r = rand.nextInt(149);
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

    public Person makeFather(Person orphan) throws Database.DatabaseException {
        //Orphan will always have a personID already, even user
        Random rand = new Random();
        int r = rand.nextInt(141);

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

    public Person[] selectAllPersons(String username) throws Database.DatabaseException{ //person model is user's person representation
        ArrayList<Person> personList = new ArrayList<Person>();

        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "select * from people WHERE descendant = '" + username + "'";
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
            throw new Database.DatabaseException("error selecting all people");
        }
        Person[] personFinal = new Person[personList.size()];
        personFinal = personList.toArray(personFinal);
        return personFinal;
    }

    public Person selectSinglePerson(String personID) throws Database.DatabaseException{
        Person out = new Person();

        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "select * from people WHERE personID = '" + personID +"'";
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
            throw new Database.DatabaseException("error getting single person");
        }
        return out;
    }

    /**
     * clear person table
     * @throws DataAccessException
     */
    public void clear() throws Database.DatabaseException
    {
        try {
            Statement stmt = null;
            try {
                stmt = conn.createStatement();

                stmt.executeUpdate("drop table if exists people");
                stmt.executeUpdate("create table people (personID VARCHAR(50) NOT NULL PRIMARY KEY, descendant VARCHAR(50) NOT NULL, firstName VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
                        "gender CHAR(1) NOT NULL, father VARCHAR(50), mother VARCHAR(50), spouse VARCHAR(50), CONSTRAINT person_info UNIQUE (personID))");

            }

            finally {
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
            }
        }
        catch (SQLException e) {
            throw new Database.DatabaseException("error resetting table");
        }
    }

    public String tableToString() throws Database.DatabaseException{
        StringBuilder out = new StringBuilder();
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "select * from people";
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
            throw new Database.DatabaseException("error toString table");
        }
        return out.toString();
    }

    public void deleteAllPeopleofUser(User user) throws Database.DatabaseException {
        try {
            Statement stmt = null;
            try {
                stmt = conn.createStatement();

                stmt.executeUpdate("DELETE FROM people WHERE descendant = '" + user.getUsername() + "'");

            }
            finally {
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
            }
        }
        catch (SQLException e) {
            throw new Database.DatabaseException("error deleting all people of user");
        }
    }

}
