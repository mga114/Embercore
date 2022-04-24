import embercore.Window;

import ecs.Test;

public class Main {
    public static void test() {
        Test.test();
    }

    public static void main(String[] args) {
        test();
        Window window = Window.get();
        window.run();
    }
}
