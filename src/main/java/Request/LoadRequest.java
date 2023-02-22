package Request;

import Model.Event;
import Model.Person;
import Model.User;

/**
 * A class for creating a load request
 */
public class LoadRequest {
    /**
     * an array of users
     */
    private User[] users; // an array of users
    /**
     * an array of persons
     */
    private Person[] persons; // an array of persons
    /**
     * an array of events
     */
    private Event[] events; // an array of events

    // constructor

    /**
     * Load
     * @param usersInput Input of a user list
     * @param personsInput Input of a person list
     * @param eventsInput Input event a list
     */
    public LoadRequest(User[] usersInput, Person[] personsInput, Event[] eventsInput){
        this.users = usersInput;
        this.persons = personsInput;
        this.events = eventsInput;
    }

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    public Person[] getPersons() {
        return persons;
    }

    public void setPersons(Person[] persons) {
        this.persons = persons;
    }

    public Event[] getEvents() {
        return events;
    }

    public void setEvents(Event[] events) {
        this.events = events;
    }


}
