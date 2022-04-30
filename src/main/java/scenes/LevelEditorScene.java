package scenes;

import animation.*;
import animation.easing.Easing;
import components.core.Spritesheet;
import ecs.Entity;
import ecs.EntityType;
import components.core.Transform;
import ecs.Test;
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
        //Test.test();
        loadResources();
        this.camera = new Camera(new Vector2f());

        sprites = AssetPool.getSpritesheet("assets/images/spritesheet.png");

        for (int i = 0; i < 5; i++) {
            //System.out.println("LES");
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
