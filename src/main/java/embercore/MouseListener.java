package embercore;

import org.joml.Vector4f;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

/**
 * glfw.org/docs/3.3/input_guide.html
 */
public class MouseListener {
    private static MouseListener instance;
    private double scrollX, scrollY;
    private double xPos, yPos, lastY, lastX;
    private boolean mouseButtonPressed[] = new boolean[3];
    private boolean isDragging;

    private MouseListener () {
        this.scrollX = 0.0;
        this.scrollY = 0.0;
        this.xPos = 0.0;
        this.yPos = 0.0;
        this.lastX = 0.0;
        this.lastY = 0.0;
    }

    //Singleton accesses for MouseListener
    public static MouseListener get () {
        if (MouseListener.instance == null) {
            MouseListener.instance = new MouseListener();
        }

        return MouseListener.instance;
    }

    //Mouse position callback as specified in glfw.org/docs/3.3/input_guide.html
    //handles mouse movement in the glfw window
    public static void mousePosCallback (long window, double xpos, double ypos) {
        get().lastX = get().xPos;
        get().lastY = get().yPos;
        get().xPos = xpos;
        get().yPos = ypos;
        get().isDragging = get().mouseButtonPressed[0] || get().mouseButtonPressed[1] || get().mouseButtonPressed[2];
    }

    //Mouse button callback as specified in glfw.org/docs/3.3/input_guide.html
    //handles when a mouse button is pressed
    public static void mouseButtonCallback (long window, int button, int action, int mods) {
        if (button < get().mouseButtonPressed.length) {
            if (action == GLFW_PRESS) {
                get().mouseButtonPressed[button] = true;
            } else if (action == GLFW_RELEASE) {
                get().mouseButtonPressed[button] = false;
                get().isDragging = false;
            }
        }
    }

    //Mouse scroll callback as specified in glfw.org/docs/3.3/input_guide.html
    //handles when the mouse wheel is scrolled
    public static void mouseScrollCallback (long window, double xOffset, double yOffset) {
        get().scrollX = xOffset;
        get().scrollY = yOffset;
    }


    public static void endFrame() {
        get().scrollX = 0;
        get().scrollY = 0;
        get().lastX = get().xPos;
        get().lastY = get().yPos;
    }

    public static float getX () {
        return (float) get().xPos;
    }

    public static float getY () {
        return (float) get().yPos;
    }

    public static float getOrthoX () {
        float currentX = getX ();
        currentX = (currentX / (float) Window.getWidth()) * 2.0f - 1.0f;
        Vector4f tmp = new Vector4f(currentX, 0, 0, 1);
        tmp.mul(Window.getScene().camera().getInverseProjection()).mul(Window.getScene().camera().getInverseView());
        System.out.println(tmp.x);
        return tmp.x;
    }

    public static float getOrthoY () {
        float currentY = getY ();
        currentY = (currentY / (float) Window.getHeight()) * 2.0f - 1.0f;
        Vector4f tmp = new Vector4f(0, -currentY, 0, 1);
        tmp.mul(Window.getScene().camera().getInverseProjection()).mul(Window.getScene().camera().getInverseView());
        return tmp.y;
    }

    public static float getDx () {
        return (float) (get().lastX - get().xPos);
    }

    public static float getDy () {
        return (float) (get().lastY - get().yPos);
    }

    public static float getScrollX () {
        return (float) get().scrollX;
    }

    public static float getScrollY () {
        return (float) get().scrollY;
    }

    public static boolean isDragging () {
        return get().isDragging;
    }

    //Checks if a mouse button is currently down
    public static boolean mouseButtonDown (int button) {
        if (button < get().mouseButtonPressed.length) {
            return get().mouseButtonPressed[button];
        }
        return false;
    }
}
