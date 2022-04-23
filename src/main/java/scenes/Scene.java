package scenes;

import embercore.Camera;
import embercore.GameObject;
import renderer.SpriteRenderController;

import java.util.ArrayList;
import java.util.List;

public abstract class Scene {

    protected SpriteRenderController renderer = new SpriteRenderController();
    protected Camera camera;
    private boolean isRunning = false;
    protected List<GameObject> gameObjects = new ArrayList<>();

    public Scene () {

    }

    public void init () {

    }

    public void start () {
        for (GameObject go : gameObjects) {
            go.start();
            this.renderer.add(go);
        }
        isRunning = true;
    }

    public void addGameObjectToScene(GameObject go) {
        gameObjects.add(go);
        if (isRunning) {
            go.start();
            this.renderer.add(go);
        }
    }

    public abstract void update (float dt);

    public Camera camera () {
        return this.camera;
    }
}
