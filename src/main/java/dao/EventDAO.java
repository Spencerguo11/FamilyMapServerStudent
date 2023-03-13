package dao;

import JsonClasses.Loader;
import JsonClasses.Location;
import JsonClasses.LocationData;
import Model.Event;
import Model.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * A class to handle events from the database
 */
public class EventDAO {
    /**
     * connect to the database
     */
    private Connection conn; // connect to the database

    private LocationData locationData;

    public EventDAO() {
        this.locationData = Loader.decodeLocations("json/locations.json");
    }

    public void setConnection(Connection conn){this.conn = conn;}

    /**
     * Insert information for an event
     * @param event pass in a event class object
     * @throws DataAccessException throws data access exceptions
     */
    public void insert(Event event) throws DataAccessException {
        //We can structure our string to be similar to a sql command, but if we insert question
        //marks we can change them later with help from the statement
        String sql = "INSERT INTO Event (EventID, AssociatedUsername, PersonID, Latitude, Longitude, " +
                "Country, City, EventType, Year) VALUES(?,?,?,?,?,?,?,?,?);";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, event.getEventID());
            stmt.setString(2, event.getAssociatedUsername());
            stmt.setString(3, event.getPersonID());
            stmt.setFloat(4, event.getLatitude());
            stmt.setFloat(5, event.getLongitude());
            stmt.setString(6, event.getCountry());
            stmt.setString(7, event.getCity());
            stmt.setString(8, event.getEventType());
            stmt.setInt(9, event.getYear());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting an event into the database");
        }
    }


    /**
     * Find a specific event
     * @param eventID pass in a event ID
     * @return a event object
     * @throws DataAccessException throws data access exceptions
     */
    public Event find(String eventID) throws DataAccessException {
        Event event;
        ResultSet rs;
        String sql = "SELECT * FROM Event WHERE EventID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, eventID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                event = new Event(rs.getString("EventID"), rs.getString("AssociatedUsername"),
                        rs.getString("PersonID"), rs.getFloat("Latitude"), rs.getFloat("Longitude"),
                        rs.getString("Country"), rs.getString("City"), rs.getString("EventType"),
                        rs.getInt("Year"));
                return event;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding an event in the database");
        }

    }


    /**
     * Clear all events
     * @throws DataAccessException throws data access exceptions
     */
//    public void clear() throws DataAccessException {
//        try {
//            Statement stmt = null;
//            try {
//                stmt = conn.createStatement();
//
//                stmt.executeUpdate("drop table if exists Event");
//                stmt.executeUpdate("create table Event (eventID VARCHAR(255) NOT NULL PRIMARY KEY, associatedUsername VARCHAR(255) NOT NULL, peronID VARCHAR(255) NOT NULL, latitude REAL NOT NULL, " +
//                        "longitude REAL NOT NULL, country VARCHAR(255) NOT NULL, city VARCHAR(255) NOT NULL, eventType VARCHAR(255) NOT NULL, eventYear INT NOT NULL, CONSTRAINT event_info UNIQUE (eventID))");
//            }
//            finally {
//                if (stmt != null) {
//                    stmt.close();
//                    stmt = null;
//                }
//            }
//        }
//        catch (SQLException e) {
//            throw new DataAccessException("error clearing table");
//        }
//    }

    public void clear() throws DataAccessException{
        String sql = "DELETE FROM Event;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the user table");
        }
    }
    /**
     * Clear events by a username
     * @param username pass in a username
     */
    public void clearByUsername(String username) throws DataAccessException{
        // clear events by a username;
        String sql = "DELETE FROM Event WHERE associatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting an event into the database");
        }
    }

    public List<Event> listAllEventsByUsername(String username) throws DataAccessException{
        List<Event> eventList = new ArrayList<>();
        ResultSet rs;
        String sql = "SELECT * FROM Event WHERE associatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            while(rs.next()) {

                Event event = new Event(rs.getString("EventID"), rs.getString("AssociatedUsername"),
                        rs.getString("PersonID"), rs.getFloat("Latitude"), rs.getFloat("Longitude"),
                        rs.getString("Country"), rs.getString("City"), rs.getString("EventType"),
                        rs.getInt("Year"));
                eventList.add(event);

            }
            return eventList;
        }catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting an event into the database");
        }
    }

    public int generateBaseEvent(Person root) throws DataAccessException {
        int rootBirthYear = 1900;

        //making root's birth
        Event birth = new Event();
        Random rand = new Random();

        birth.setEventID(UUID.randomUUID().toString());
        birth.setAssociatedUsername(root.getAssociatedUsername());
        birth.setPersonID(root.getPersonID());
        int r = rand.nextInt(977);
        Location randLocation = locationData.getLocations()[r];
        birth.setLatitude(randLocation.getLatitude());
        birth.setLongitude(randLocation.getLongitude());
        birth.setCountry(randLocation.getCountry());
        birth.setCity(randLocation.getCity());
        birth.setEventType("Birth");
        birth.setYear(rootBirthYear);

        insert(birth); //inserts birth into database

        //Making root's baptism
        Event baptism = new Event();
        baptism.setEventID(UUID.randomUUID().toString());
        baptism.setAssociatedUsername(root.getAssociatedUsername());
        baptism.setPersonID(root.getPersonID());
        r = rand.nextInt(977);
        randLocation = locationData.getLocations()[r];
        baptism.setLatitude(randLocation.getLatitude());
        baptism.setLongitude(randLocation.getLongitude());
        baptism.setCountry(randLocation.getCountry());
        baptism.setCity(randLocation.getCity());
        baptism.setEventType("Baptism");
        baptism.setYear(rootBirthYear + 15);  //He's a convert

        insert(baptism);
        

        //Making root's puppy
        Event graduation = new Event();
        graduation.setEventID(UUID.randomUUID().toString());
        graduation.setAssociatedUsername(root.getAssociatedUsername());
        graduation.setPersonID(root.getPersonID());
        r = rand.nextInt(977);
        randLocation = locationData.getLocations()[r];
        graduation.setLatitude(randLocation.getLatitude());
        graduation.setLongitude(randLocation.getLongitude());
        graduation.setCountry(randLocation.getCountry());
        graduation.setCity(randLocation.getCity());
        graduation.setEventType("Graduated College");
        graduation.setYear(rootBirthYear + 24);

        insert(graduation);

        return rootBirthYear;
    }


    public int generateParentEvents(Person father, Person mother, int birthYear) throws DataAccessException {
        Event birth = new Event();
        Random rand = new Random();
        int parentsBirthDate = birthYear - 26;

        birth.setEventID(UUID.randomUUID().toString());
        birth.setAssociatedUsername(mother.getAssociatedUsername());
        birth.setPersonID(mother.getPersonID());
        int r = rand.nextInt(977);
        Location randLocation = locationData.getLocations()[r];
        birth.setLatitude(randLocation.getLatitude());
        birth.setLongitude(randLocation.getLongitude());
        birth.setCountry(randLocation.getCountry());
        birth.setCity(randLocation.getCity());
        birth.setEventType("Birth");
        birth.setYear(parentsBirthDate);

        insert(birth); //inserted mother's birth

        birth.setEventID(UUID.randomUUID().toString()); //Making father's birth
        birth.setAssociatedUsername(father.getAssociatedUsername());
        birth.setPersonID(father.getPersonID());
        r = rand.nextInt(977);
        randLocation = locationData.getLocations()[r];
        birth.setLatitude(randLocation.getLatitude());
        birth.setLongitude(randLocation.getLongitude());
        birth.setCountry(randLocation.getCountry());
        birth.setCity(randLocation.getCity());
        birth.setEventType("Birth");
        birth.setYear(parentsBirthDate);

        insert(birth); //inserts father's birth

        Event death = new Event();  //making mothers death

        death.setEventID(UUID.randomUUID().toString());
        death.setAssociatedUsername(mother.getAssociatedUsername());
        death.setPersonID(mother.getPersonID());
        r = rand.nextInt(977);
        randLocation = locationData.getLocations()[r];
        death.setLatitude(randLocation.getLatitude());
        death.setLongitude(randLocation.getLongitude());
        death.setCountry(randLocation.getCountry());
        death.setCity(randLocation.getCity());
        death.setEventType("Death");
        death.setYear(birthYear + 56);

        insert(death); //inserts mothers death

        death.setEventID(UUID.randomUUID().toString()); //makes father's death
        death.setAssociatedUsername(father.getAssociatedUsername());
        death.setPersonID(father.getPersonID());
        r = rand.nextInt(977);
        randLocation = locationData.getLocations()[r];
        death.setLatitude(randLocation.getLatitude());
        death.setLongitude(randLocation.getLongitude());
        death.setCountry(randLocation.getCountry());
        death.setCity(randLocation.getCity());
        death.setEventType("Death");
        death.setYear(birthYear + 54);

        insert(death); //inserts father's death into database

        Event marriage = new Event();  //making marriage event for mother

        marriage.setEventID(UUID.randomUUID().toString());
        marriage.setAssociatedUsername(mother.getAssociatedUsername());
        marriage.setPersonID(mother.getPersonID());
        r = rand.nextInt(977);
        randLocation = locationData.getLocations()[r];
        marriage.setLatitude(randLocation.getLatitude());
        marriage.setLongitude(randLocation.getLongitude());
        marriage.setCountry(randLocation.getCountry());
        marriage.setCity(randLocation.getCity());
        marriage.setEventType("Marriage");
        marriage.setYear(parentsBirthDate + 21);

        insert(marriage); //inserts marriage in for mother

        marriage.setEventID(UUID.randomUUID().toString());
        marriage.setAssociatedUsername(father.getAssociatedUsername());
        marriage.setPersonID(father.getPersonID());
        marriage.setLatitude(randLocation.getLatitude());
        marriage.setLongitude(randLocation.getLongitude());
        marriage.setCountry(randLocation.getCountry());
        marriage.setCity(randLocation.getCity());
        marriage.setEventType("Marriage");
        marriage.setYear(parentsBirthDate + 21);

        insert(marriage); //inserts marriage in for father

        return parentsBirthDate;
    }
}
