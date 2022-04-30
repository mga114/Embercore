package renderer;

import components.core.SpriteRenderer;
import ecs.Entity;
import ecs.Group;
import components.core.Transform;
import util.AssetPool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SpriteRenderController {
    private final int MAX_BATCH_SIZE = 1000;
    private List<SpriteRenderBatch> batches;
    private static final Group spriteGroup = new Group(Transform.class, SpriteRenderer.class);
    
    private static final RenderConfig spriteRenderConfig = new RenderConfig(2, 4, 2, 1, AssetPool.getShader("assets/shaders/default.glsl"));

    public SpriteRenderController() {
        this.batches = new ArrayList<>();
        //System.out.println("SRC");
        spriteGroup.onAdded((e) -> {
            System.out.println(e);
            SpriteRenderer spr = e.get(SpriteRenderer.class);
            if (spr != null) {
                add (spr);
            }
        });
    }

    public void add (SpriteRenderer sprite) {
        boolean added = false;
        for (SpriteRenderBatch batch : batches) {
            if (batch.hasRoom() && batch.zIndex() == sprite.zIndex) {
                Texture tex = sprite.getTexture();
                if (tex == null || (batch.hasTexture(tex) || batch.hasTextureRoom())) {
                    batch.addSprite(sprite);
                    added = true;
                    break;
                }
            }
        }

        if (!added) {
            SpriteRenderBatch batch = new SpriteRenderBatch(MAX_BATCH_SIZE, sprite.zIndex, spriteRenderConfig);
            batch.start();
            batches.add(batch);
            batch.addSprite(sprite);
            Collections.sort(batches);
        }
    }

    public void render () {
        for (RenderBatch batch : batches) {
            batch.render();
        }
    }
}
