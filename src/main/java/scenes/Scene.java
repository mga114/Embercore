package scenes;

import embercore.Camera;
import embercore.GameObject;
import renderer.SpriteRenderController;

import java.util.ArrayList;
import java.util.List;

public abstract class Scene {

    public SpriteRenderController renderer = new SpriteRenderController();
    protected Camera camera;
    private boolean isRunning = false;
    protected List<GameObject> gameObjects = new ArrayList<>();

    public Scene () {

    }

    public void init () {

    }

    public void start () {
        isRunning = true;
    }

    public abstract void update (float dt);

    public Camera camera () {
        return this.camera;
    }
}
