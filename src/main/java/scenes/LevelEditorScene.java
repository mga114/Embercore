package scenes;

import components.Sprite;
import components.SpriteRenderer;
import components.Spritesheet;
import embercore.*;
import gui.Easing;
import gui.GUIComponent;
import gui.GUIConstraint;
import org.joml.Vector2f;
import renderer.GUIRenderController;
import util.AssetPool;

import static gui.ConstraintType.RELATIVE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;

public class LevelEditorScene extends Scene {
    GameObject obj1;
    GUIComponent comp;
    private Spritesheet sprites;
    private final GUIRenderController guiRenderer = new GUIRenderController();

    public LevelEditorScene () {

    }

    @Override
    public void init () {
        loadResources();
        this.camera = new Camera(new Vector2f());

        sprites = AssetPool.getSpritesheet("assets/images/spritesheet.png");

        obj1 = new GameObject("Object 1", new Transform(new Vector2f(100, 100), new Vector2f(256, 256)), 4);
        obj1.addComponent(new SpriteRenderer(new Sprite(AssetPool.getTexture("assets/images/blendImage1.png"))));
        this.addGameObjectToScene(obj1);

        GameObject obj2 = new GameObject("Object 2", new Transform(new Vector2f(200, 100), new Vector2f(256, 256)), 2);
        obj2.addComponent(new SpriteRenderer(new Sprite(AssetPool.getTexture("assets/images/blendImage2.png"))));
        this.addGameObjectToScene(obj2);

        GUIConstraint con = new GUIConstraint();
        con.setX (RELATIVE, 0.05f);
        con.setY(RELATIVE, 0.075f);
        con.setWidth(RELATIVE, 0.5f);
        con.setHeight(RELATIVE, 0.9f);

        GUIConstraint con2 = new GUIConstraint();
        con2.setX (RELATIVE, 0.05f);
        con2.setY(RELATIVE, 0.075f);
        con2.setWidth(RELATIVE, 0.5f);
        con2.setHeight(RELATIVE, 0.5f);

        comp = new GUIComponent(con);

        GUIComponent comp2 = new GUIComponent(con2);
        comp.addChild(comp2);

        guiRenderer.add(comp);
        guiRenderer.add(comp2);

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

        if (KeyListener.isKeyPressed(GLFW_KEY_SPACE)) {
            new Animation (Easing.easeOutQuint, comp.setX, )
        }

        for (GameObject go : this.gameObjects) {
            go.update(dt);
        }

        this.renderer.render();
        this.guiRenderer.render();
    }
}
