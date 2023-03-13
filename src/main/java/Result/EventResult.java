package Result;
import Model.Event;
import Model.Person;

import java.util.List;


/**
 * A class to handle event information results
 */
public class EventResult {
    /**
     * A list of events
     */
    private List<Event> events;
    /**
     * message to return
     */
    private String message;
    /**
     * A boolean variable
     */
    private boolean success;


    /**
     * An ID for an event
     */
    private String eventID; // An ID for an event

    /**
     * username that is associated with the event
     */
    private String associatedUsername; // username that is associated with the event

    /**
     * an Id for a person
     */
    private String personID; // an Id for a person

    /**
     * the latitude of an event
     */
    private Float latitude; // the latitude of an event

    /**
     * the longitude of an event
     */
    private Float longitude; // the longitude of an event

    /**
     * the country of the event
     */
    private String country; // the country of the event

    /**
     * the city of the event
     */
    private String city; // the city of the event

    /**
     * The type of the event
     */
    private String eventType; // The type of the event

    /**
     * the year when the event happened
     */
    private Integer year; // the year when the event happened


    /**
     * EventResult Constructor
     * @param eventsInput Input of an list of events
     * @param messageInput Input of a string
     * @param successInput A boolean input
     */
    //constructor
    public EventResult(List<Event> eventsInput, String messageInput, boolean successInput){
        events = eventsInput;
        message = messageInput;
        success = successInput;
    }



    /**
     * Another EventResult constructor
     * @param eventID Input of the event ID
     * @param username Input of the username
     * @param personID Input of the person ID
     * @param latitude Input of the latitude
     * @param longitude Input of the longitude
     * @param country Input of the country
     * @param city Input of the city
     * @param eventType Input of the event type
     * @param year Input of the year
     */
    public EventResult(String eventID, String username, String personID, Float latitude, Float longitude,
                 String country, String city, String eventType, Integer year) {
        this.eventID = eventID;
        this.associatedUsername = username;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    public EventResult() {}

    public EventResult(List<Event> eventsInput){events = eventsInput;}

    public EventResult(Event event, boolean successInput){
        eventID = event.getEventID();
        associatedUsername = event.getAssociatedUsername();
        personID = event.getPersonID();
        latitude = event.getLatitude();
        longitude = event.getLongitude();
        country = event.getCountry();
        city = event.getCity();
        eventType = getEventType();
        year = event.getYear();
        success = successInput;
    }




    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }


    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }





}
