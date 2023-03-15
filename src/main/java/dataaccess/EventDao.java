package dataaccess;

import Decode.*;
import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class EventDao {
    private Connection conn;
    private LocationData locationData;

    public EventDao() {
        locationData = Loader.decodeLocations("json/locations.json");
    }

    public void setConnection(Connection c) throws DataAccessException{
        conn = c;
    }

    /**
     * insert new event onto event table
     * @param event
     * @throws DataAccessException
     */
    public void insert(Event event) throws DataAccessException {
        String sql = "insert into Event (eventID, associatedUsername, peronID, latitude, longitude, country, city, eventType, year) values (?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, event.getEventID());
            stmt.setString(2, event.getAssociatedUsername());
            stmt.setString(3, event.getPersonID());
            stmt.setFloat(4, event.getLatitude());
            stmt.setFloat(5, event.getLongitude());
            stmt.setString(6, event.getCountry());
            stmt.setString(7, event.getCity());
            stmt.setString(8, event.getEventType());
            stmt.setInt(9, event.getYear());

            if (stmt.executeUpdate() != 1) {
                throw new DataAccessException("error inserting");
            }

        }catch (SQLException e) {
            throw new DataAccessException("error inserting");
        }
    }

    /**
     * find event on event table using eventID
     * @param eventID
     * @return
     * @throws DataAccessException
     */
    public boolean find(String eventID) throws DataAccessException {
        String sql = "select * from Event WHERE eventID = '" + eventID + "'";;
        try (PreparedStatement stmt = conn.prepareStatement(sql)){

            ResultSet rs = null;
            rs = stmt.executeQuery();

            if (!rs.next() ) {
                throw new DataAccessException("error finding eventID");
            } else {
                return true;
            }

        }
        catch (SQLException e) {
            throw new DataAccessException("error finding eventID");
        }
    }

    /**
     * clear event table
     * @throws DataAccessException
     */
    public void clear() throws DataAccessException {
        try {
            Statement stmt = null;

            stmt = conn.createStatement();

            stmt.executeUpdate("drop table if exists Event");
            stmt.executeUpdate("create table Event (eventID VARCHAR(50) NOT NULL PRIMARY KEY, associatedUsername VARCHAR(50) NOT NULL, peronID VARCHAR(50) NOT NULL, latitude REAL NOT NULL, " +
                    "longitude REAL NOT NULL, country VARCHAR(50) NOT NULL, city VARCHAR(50) NOT NULL, eventType VARCHAR(50) NOT NULL, year INT NOT NULL, CONSTRAINT event_info UNIQUE (eventID))");


        }
        catch (SQLException e) {
            throw new DataAccessException("error clearing table");
        }
    }

    public void deleteAllEventsOfUser(User user) throws DataAccessException {
        try {
            Statement stmt = null;

            stmt = conn.createStatement();

            stmt.executeUpdate("DELETE FROM Event WHERE associatedUsername = '" + user.getUsername() + "'");

        }
        catch (SQLException e) {
            throw new DataAccessException("error deleting all events of user");
        }
    }

    public Event selectSingleEvent(String eventID) throws DataAccessException {
        Event event = new Event();
        String sql = "select * from Event WHERE eventID = '" + eventID +"'";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            ResultSet rs = null;

            rs = stmt.executeQuery();
            while (rs.next()) {
                event.setEventID(rs.getString(1));
                event.setAssociatedUsername(rs.getString(2));
                event.setPersonID(rs.getString(3));
                event.setLatitude(rs.getFloat(4));
                event.setLongitude(rs.getFloat(5));
                event.setCountry(rs.getString(6));
                event.setCity(rs.getString(7));
                event.setEventType(rs.getString(8));
                event.setYear(rs.getInt(9));

            }

        }
        catch (SQLException e) {
            throw new DataAccessException("error getting single event");
        }
        return event;
    }

    public Event[] selectAllEvents(String username) throws DataAccessException {
        ArrayList<Event> eventList = new ArrayList<>();
        String sql = "select * from Event WHERE associatedUsername = '" + username + "'";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            ResultSet rs = null;

            rs = stmt.executeQuery();
            while (rs.next()) {
                Event event = new Event();
                event.setEventID(rs.getString(1));
                event.setAssociatedUsername(rs.getString(2));
                event.setPersonID(rs.getString(3));
                event.setLatitude(rs.getFloat(4));
                event.setLongitude(rs.getFloat(5));
                event.setCountry(rs.getString(6));
                event.setCity(rs.getString(7));
                event.setEventType(rs.getString(8));
                event.setYear(rs.getInt(9));
                eventList.add(event);

            }

        }
        catch (SQLException e) {
            throw new DataAccessException("error selecting all events");
        }
        Event[] eventFinal = new Event[eventList.size()];
        eventFinal = eventList.toArray(eventFinal);
        return eventFinal;
    }

    public int generateRootEvent(Person root) throws DataAccessException {
        int rootBirthYear = 1900;

        //making root's birth
        Event birth = new Event();
        Random rand = new Random();

        birth.setEventID(UUID.randomUUID().toString());
        birth.setAssociatedUsername(root.getAssociatedUsername());
        birth.setPersonID(root.getPersonID());
        int r = rand.nextInt(977);
        IndividualLocation randLocation = locationData.getLocations()[r];
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

        //Making root's adventure
        Event adventure = new Event();
        adventure.setEventID(UUID.randomUUID().toString());
        adventure.setAssociatedUsername(root.getAssociatedUsername());
        adventure.setPersonID(root.getPersonID());
        r = rand.nextInt(977);
        randLocation = locationData.getLocations()[r];
        adventure.setLatitude(randLocation.getLatitude());
        adventure.setLongitude(randLocation.getLongitude());
        adventure.setCountry(randLocation.getCountry());
        adventure.setCity(randLocation.getCity());
        adventure.setEventType("Adventure");
        adventure.setYear(rootBirthYear + 16);

        insert(adventure);

        //Making root's puppy
        Event purchase = new Event();
        purchase.setEventID(UUID.randomUUID().toString());
        purchase.setAssociatedUsername(root.getAssociatedUsername());
        purchase.setPersonID(root.getPersonID());
        r = rand.nextInt(977);
        randLocation = locationData.getLocations()[r];
        purchase.setLatitude(randLocation.getLatitude());
        purchase.setLongitude(randLocation.getLongitude());
        purchase.setCountry(randLocation.getCountry());
        purchase.setCity(randLocation.getCity());
        purchase.setEventType("Bought a puppy");
        purchase.setYear(rootBirthYear + 10);

        insert(purchase);

        return rootBirthYear;
    }

    public int generateEventDataParents(Person mother, Person father, int orphanBirthYear) throws DataAccessException { //not recursive but will make 4 events for the given person, for now just birth

        Event birth = new Event(); //making mothers's birth
        Random rand = new Random();
        int parentsBirthDate = orphanBirthYear - 26;

        birth.setEventID(UUID.randomUUID().toString());
        birth.setAssociatedUsername(mother.getAssociatedUsername());
        birth.setPersonID(mother.getPersonID());
        int r = rand.nextInt(977);
        IndividualLocation randLocation = locationData.getLocations()[r];
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
        death.setYear(orphanBirthYear + 56);

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
        death.setYear(orphanBirthYear + 54);

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

        Event boughtHouse = new Event(); //making bought house for mother

        boughtHouse.setEventID(UUID.randomUUID().toString());
        boughtHouse.setAssociatedUsername(mother.getAssociatedUsername());
        boughtHouse.setPersonID(mother.getPersonID());
        r = rand.nextInt(977);
        randLocation = locationData.getLocations()[r];
        boughtHouse.setLatitude(randLocation.getLatitude());
        boughtHouse.setLongitude(randLocation.getLongitude());
        boughtHouse.setCountry(randLocation.getCountry());
        boughtHouse.setCity(randLocation.getCity());
        boughtHouse.setEventType("Bought house");
        boughtHouse.setYear(parentsBirthDate + 23);

        insert(boughtHouse); //inserting bought house

        Event mission = new Event(); //making mission for father

        mission.setEventID(UUID.randomUUID().toString());
        mission.setAssociatedUsername(father.getAssociatedUsername());
        mission.setPersonID(father.getPersonID());
        r = rand.nextInt(977);
        randLocation = locationData.getLocations()[r];
        mission.setLatitude(randLocation.getLatitude());
        mission.setLongitude(randLocation.getLongitude());
        mission.setCountry(randLocation.getCountry());
        mission.setCity(randLocation.getCity());
        mission.setEventType("Served Mission");
        mission.setYear(parentsBirthDate + 19);

        insert(mission); //inserting mission

        return parentsBirthDate;
    }


//    public String tableToString() throws DataAccessException {
//        StringBuilder out = new StringBuilder();
//        String sql = "select * from Event";
//        try (PreparedStatement stmt = conn.prepareStatement(sql)){
//            ResultSet rs = null;
//            rs = stmt.executeQuery();
//            while (rs.next()) {
//                String eventID = rs.getString(1);
//                String assocatedUsername = rs.getString(2);
//                String personId = rs.getString(3);
//                Float latitude = rs.getFloat(4);
//                Float longitude = rs.getFloat(5);
//                String country = rs.getString(6);
//                String city = rs.getString(7);
//                String eventType = rs.getString(8);
//                int year = rs.getInt(9);
//
//                out.append((eventID + "\t" + assocatedUsername + "\t" + personId + "\t" + latitude + "\t" + longitude + "\t" + country + "\t" + city + "\t" + eventType + "\t" + year + "\n"));
//
//            }
//
//        }
//        catch (SQLException e) {
//            throw new DataAccessException("error toString table");
//        }
//        return out.toString();
//    }

}





