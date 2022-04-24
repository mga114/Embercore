package events;

public class Test {
    private static int a = 0;
    private static int b = 0;

    public static void test() {

        Events.on(EventID.UPDATE, (x) -> {
            a = a + 1;
        });

        Events.on(EventID.UPDATE, (x) -> {
            b = b + 1;
        });

        Events.call(EventID.UPDATE);

        assert (a == 1 && b==1);

        for (int i=0; i<10; i++) {
            Events.call(EventID.UPDATE);
        }

        assert (a==11 && b==11);

        Events.clear();
    }
}
