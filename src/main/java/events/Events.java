package events;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Events {

    private static final HashMap<Event, ArrayList<EventFunction>> events = new HashMap<>();

    public static void on(Event e, EventFunction func) {
        var array = events.get(e);
        if (Objects.isNull(array)) {
            array = new ArrayList<>();
            events.put(e, array);
        }
        array.add(func);
    }

    public static void call(Event e, Object... args) {
        var array = events.get(e);
        if (!Objects.isNull(array)) {
            for (var event: array) {
                event.call(args);
            }
        }
    }
}

