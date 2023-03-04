package dao;

import Model.Event;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A class to handle events from the database
 */
public class EventDAO {
    /**
     * connect to the database
     */
    private final Connection conn; // connect to the database

    /**
     * EventDAO constructor
     * @param conn connect to the database
     */
    public EventDAO(Connection conn) {
        this.conn = conn;
    }

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
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM Event;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the event table");
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
                if (rs.next()) {
                    Event event = new Event(rs.getString("EventID"), rs.getString("AssociatedUsername"),
                            rs.getString("PersonID"), rs.getFloat("Latitude"), rs.getFloat("Longitude"),
                            rs.getString("Country"), rs.getString("City"), rs.getString("EventType"),
                            rs.getInt("Year"));
                    eventList.add(event);
                }
            }
            return eventList;
        }catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting an event into the database");
        }
    }



}
