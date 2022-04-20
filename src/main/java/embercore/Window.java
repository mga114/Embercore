package embercore;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import scenes.LevelEditorScene;
import scenes.LevelScene;
import scenes.Scene;
import util.Time;

import java.util.Objects;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Display window for main game, contains loop for objects related to this window
 * Adapted from https://www.lwjgl.org/guide
 */
public class Window {
    private int width, height;
    private final String title;
    private long glfwWindow;

    private static Window window = null;

    private static Scene currentScene;

    private Window () {
        this.width = 1920;
        this.height = 1080;
        this.title = "Embercore";
    }

    public static void changeScene (int newScene) {
        switch (newScene) {
            case 0:
                currentScene = new LevelEditorScene();
                currentScene.init();
                currentScene.start();
                break;
            case 1:
                currentScene = new LevelScene();
                currentScene.init();
                currentScene.start();
                break;
            default:
                assert false : "Unknown scene '" + newScene + "'";
                break;
        }
    }

    public static Window get () {
        if (Window.window == null) {
            Window.window = new Window();
        }

        return Window.window;
    }

    public static Scene getScene () {
        return Window.get().currentScene;
    }

    public void run () {
        init ();
        loop ();

        //Clean up after the loop
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        //Stop GLFW and reset the error callback
        glfwTerminate();
        Objects.requireNonNull(glfwSetErrorCallback(null)).free();
    }

    public void init () {
        //Setup error callback
        GLFWErrorCallback.createPrint(System.err).set();

        //Initialise GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialise GLFW");
        }

        //Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_FALSE);

        //Create the window
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        if (glfwWindow == NULL) {
            throw new IllegalStateException("Failed to create GLFW window.");
        }

        //Create mouse callback using lambda function
        // src:  glfw.org/docs/3.3/input_guide.html
        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
        //Create keyboard callback using lambda function
        // src:  glfw.org/docs/3.3/input_guide.html
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);
        //Window size callback

        //glfwSetWindowSizeCallback(glfwWindow, (w, newWidth, newHeight) -> {
        //    Window.setWidth(newWidth);
        //    Window.setHeight(newHeight);
        //    Window.currentScene.camera().adjustProjection();
        //});

        //Make the OpenGL context current
        glfwMakeContextCurrent(glfwWindow);
        //Enable v-sync
        glfwSwapInterval(1);

        //Make the window visible
        glfwShowWindow(glfwWindow);

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);

        Window.changeScene(0);
    }

    public void loop () {
        double frameStart = Time.getTime();
        double frameEnd;
        float dt = -1.0f;

        while (!glfwWindowShouldClose(glfwWindow)) {
            //Poll events
            glfwPollEvents();

            glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT);

            if (dt >= 0) {
                currentScene.update(dt);
            }

            //Swaps front and back buffers for the current window
            glfwSwapBuffers(glfwWindow);

            //Handle delta time calculations
            frameEnd = Time.getTime();
            dt = (float) (frameEnd - frameStart);
            frameStart = frameEnd;
        }
    }

    public static int getWidth () {
        return get().width;
    }

    public static int getHeight () {
        return get().height;
    }

    public static void setWidth (int width) {
        get().width = width;
    }

    public static void setHeight (int height) {
        get().height = height;
    }
}
