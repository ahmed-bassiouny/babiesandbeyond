package tech.ntam.babiesandbeyond.database;

import java.util.ArrayList;
import java.util.List;

import tech.ntam.babiesandbeyond.model.Event;

/**
 * Created by bassiouny on 04/01/18.
 */

public class EventsDatabase {
    private static List<Event> events;

    public static List<Event> getEvents() {
        if (events == null)
            events = new ArrayList<>();
        return events;
    }

    public static void setEvents(List<Event> events) {
        EventsDatabase.events = events;
    }
}
