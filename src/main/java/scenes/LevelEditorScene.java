package scenes;

import components.Sprite;
import components.SpriteRenderer;
import components.Spritesheet;
import embercore.*;
import gui.ConstraintType;
import gui.GUIComponent;
import gui.GUIConstraint;
import org.joml.Vector2f;
import renderer.GUIRenderer;
import util.AssetPool;

import static gui.ConstraintType.PIXEL;
import static gui.ConstraintType.RELATIVE;

public class LevelEditorScene extends Scene {
    GameObject obj1;
    private Spritesheet sprites;

    public LevelEditorScene () {

    }

    @Override
    public void init () {
        loadResources();
        this.camera = new Camera(new Vector2f());

        sprites = AssetPool.getSpritesheet("assets/images/spritesheet.png");

        obj1 = new GameObject("Object 1", new Transform(new Vector2f(100, 100), new Vector2f(256, 256)), 4);
        obj1.addComponent(new SpriteRenderer(new Sprite(AssetPool.getTexture("assets/images/blendImage1.png"))));
        //this.addGameObjectToScene(obj1);

        GameObject obj2 = new GameObject("Object 2", new Transform(new Vector2f(200, 100), new Vector2f(256, 256)), 2);
        obj2.addComponent(new SpriteRenderer(new Sprite(AssetPool.getTexture("assets/images/blendImage2.png"))));
        //this.addGameObjectToScene(obj2);

    }

    private void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");
        AssetPool.addSpritesheet("assets/images/spritesheet.png", new Spritesheet(AssetPool.getTexture("assets/images/spritesheet.png"), 16, 16, 26, 0));
        AssetPool.getShader("assets/shaders/gui.glsl");
    }

    int placedX = 0;
    int placedY = 0;

    @Override
    public void update(float dt) {

        if (MouseListener.isDragging()) {
            if (!(placedX == Math.floor(MouseListener.getOrthoX() / 32) * 32 && placedY == Math.floor(MouseListener.getOrthoY() / 32) * 32)) {
                GameObject obj = Prefabs.generateSpriteObject(sprites.getSprite(0),
                        new Transform(new Vector2f((float) Math.floor(MouseListener.getOrthoX() / 32) * 32, (float) Math.floor(MouseListener.getOrthoY() / 32) * 32), new Vector2f(32, 32)),
                        16, 16);
                this.addGameObjectToScene(obj);
                placedX = (int) Math.floor(MouseListener.getOrthoX() / 32) * 32;
                placedY = (int) Math.floor(MouseListener.getOrthoY() / 32) * 32;
            }
        }
        //System.out.println("FPS: " + 1.0 / dt);

        for (GameObject go : this.gameObjects) {
            go.update(dt);
        }

        this.renderer.render();

        GUIComponent comp = new GUIComponent();
        comp.setX(RELATIVE, 0.05f);
        comp.setY(RELATIVE, 0.075f);
        comp.setWidth(RELATIVE, 0.5f);
        comp.setHeight(RELATIVE, 0.5f);

        GUIComponent comp2 = new GUIComponent();
        comp.addChild(comp2);
        comp2.setX(RELATIVE, 0.05f);
        comp2.setY(RELATIVE, 0.075f);
        comp2.setWidth(RELATIVE, 0.5f);
        comp2.setHeight(RELATIVE, 0.5f);

        GUIRenderer r = new GUIRenderer(comp);
        r.start();
        r.render();

        GUIRenderer r2 = new GUIRenderer(comp2);
        r2.start();
        r2.render();
    }
}
