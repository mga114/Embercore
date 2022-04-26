package events;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Events {

    private static final HashMap<EventID, ArrayList<EventFunction>> events = new HashMap<>();

    public static void on(EventID e, EventFunction func) {
        var array = events.get(e);
        if (Objects.isNull(array)) {
            array = new ArrayList<>();
            events.put(e, array);
        }
        array.add(func);
    }

    public static void call(EventID e, Object... args) {
        var array = events.get(e);
        if (!Objects.isNull(array)) {
            for (var event: array) {
                event.call(args);
            }
        }
    }

    public static void clear() {
        events.clear();
    }
}

