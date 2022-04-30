package scenes;

import animation.*;
import animation.easing.Easing;
import components.core.Spritesheet;
import ecs.Entity;
import ecs.EntityType;
import components.core.Transform;
import embercore.*;
import events.EventID;
import events.Events;
import gui.*;
import org.joml.Vector2f;
import renderer.GUIRenderController;
import util.AssetPool;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_E;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;

public class LevelEditorScene extends Scene {
    GUIComponent comp;
    Entity e;
    Transition t;
    ArrayList<Entity> entities = new ArrayList<>();
    EntityType testEntityType;
    private Spritesheet sprites;
    private final GUIRenderController guiRenderer = new GUIRenderController();

    public LevelEditorScene () {

    }

    @Override
    public void init () {
        loadResources();
        this.camera = new Camera(new Vector2f());

        sprites = AssetPool.getSpritesheet("assets/images/spritesheet.png");

        /*obj1 = new GameObject("Object 1", new Transform(new Vector2f(100, 100), new Vector2f(256, 256)), 4);
        obj1.addComponent(new SpriteRenderer(new Sprite(AssetPool.getTexture("assets/images/blendImage1.png"))));
        this.addGameObjectToScene(obj1);

        GameObject obj2 = new GameObject("Object 2", new Transform(new Vector2f(200, 100), new Vector2f(256, 256)), 2);
        obj2.addComponent(new SpriteRenderer(new Sprite(AssetPool.getTexture("assets/images/blendImage2.png"))));
        this.addGameObjectToScene(obj2);*/

        //region GUIComponents
        /*GUIConstraint con = new GUIConstraint();
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
        guiRenderer.add(comp2);*/
        //endregion

        for (int i = 0; i < 5; i++) {
            System.out.println("LES");
            Transition tt = new Transition();
            tt.xDriver(Easing.easeOutBounce(), 100.0f);
            Entity et = Prefabs.generateSpriteEntity(sprites.getSprite(0), new Transform (new Vector2f(-50.0f, ((float) i * 70.0f) + 100.0f), new Vector2f(50.0f, 50.0f)));
            Animator a = et.get(Animator.class);
            a.addAnimation(new Animation("Enter", tt, 2.0f, (float) i * 0.5f, et));

            Transition t1 = new Transition();
            t1.xDriver(Easing.easeOutBounce(), -100.0f);
            a.addAnimation(new Animation("Exit", t1, 2.0f, (float) i * 0.5f, et));
            entities.add(et);
        }

        /*t = new Transition();
        t.xDriver(Easing.easeOutQuint(), 100.0f);

        e = Prefabs.generateSpriteEntity(sprites.getSprite(0));

        Animator anim = e.get(Animator.class);
        anim.addAnimation(new Animation("Start", t, 2.0f, 0.0f, e));

        Transition t1 = new Transition();
        t1.xDriver(Easing.easeOutQuint(), -100.0f);
        anim.addAnimation(new Animation("End", t1, 2.0f, 0.0f, e));*/

        AnimationSystem as = new AnimationSystem();

    }

    private void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");
        AssetPool.addSpritesheet("assets/images/spritesheet.png", new Spritesheet(AssetPool.getTexture("assets/images/spritesheet.png"), 16, 16, 26, 0));
        AssetPool.getShader("assets/shaders/gui.glsl");

        testEntityType = new EntityType();
        testEntityType.add(Transform.class);
        testEntityType.add(Animator.class);

    }

    int placedX = 0;
    int placedY = 0;
    boolean hasHappened = true;

    @Override
    public void update(float dt) {

        /*if (MouseListener.isDragging()) {
            if (!(placedX == Math.floor(MouseListener.getOrthoX() / 32) * 32 && placedY == Math.floor(MouseListener.getOrthoY() / 32) * 32)) {
                GameObject obj = Prefabs.generateSpriteObject(sprites.getSprite(0),
                        new Transform(new Vector2f((float) Math.floor(MouseListener.getOrthoX() / 32) * 32, (float) Math.floor(MouseListener.getOrthoY() / 32) * 32), new Vector2f(32, 32)),
                        16, 16);
                this.addGameObjectToScene(obj);
                placedX = (int) Math.floor(MouseListener.getOrthoX() / 32) * 32;
                placedY = (int) Math.floor(MouseListener.getOrthoY() / 32) * 32;
            }
        }*/
        //Animator anim = e.get(Animator.class);
        if (KeyListener.isKeyPressed(GLFW_KEY_SPACE) && hasHappened) {
            for (Entity entity : entities) {
                Animator anim = entity.get(Animator.class);
                anim.animate("Enter");
            }
            hasHappened = false;
        }

        if (KeyListener.isKeyPressed(GLFW_KEY_E) && !hasHappened) {
            for (Entity entity : entities) {
                Animator anim = entity.get(Animator.class);
                anim.animate("Exit");
            }
            hasHappened = true;
        }

        for (GameObject go : this.gameObjects) {
            go.update(dt);
        }

        Events.call(EventID.UPDATE, dt);

        this.renderer.render();
        this.guiRenderer.render();
    }
}
