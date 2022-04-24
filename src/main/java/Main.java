import embercore.Window;



public class Main {
    public static void test() {
        ecs.Test.test();
        events.Test.test();
    }

    public static void main(String[] args) {
        test();
        Window window = Window.get();
        window.run();
    }
}
