package embercore;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
//glfw.org/docs/3.3/input_guide.html
public class KeyListener {
    private static KeyListener instance;
    private boolean keyPressed[] = new boolean[350];

    private KeyListener() {}

    //Singleton accesses for KeyListener
    public static KeyListener get () {
        if (KeyListener.instance == null) {
            KeyListener.instance = new KeyListener();
        }
        return KeyListener.instance;
    }

    //KeyCallback as specified in glfw.org/docs/3.3/input_guide.html
    //handles when a key is pressed in a window
    public static void keyCallback(long window, int key, int scancode, int action, int mods) {
        if (action == GLFW_PRESS) {
            get().keyPressed[key] = true;
        } else if (action == GLFW_RELEASE) {
            get().keyPressed[key] = false;
        }
    }

    //Test if a key is currently pressed
    public static boolean isKeyPressed(int keyCode) {
        if (keyCode < get().keyPressed.length) {
            return get().keyPressed[keyCode];
        }
        return false;
    }
}
