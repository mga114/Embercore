package renderer;

import components.SpriteRenderer;
import embercore.GameObject;
import util.AssetPool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SpriteRenderController {
    private final int MAX_BATCH_SIZE = 1000;
    private List<SpriteRenderBatch> batches;
    
    private static RenderConfig spriteRenderConfig = new RenderConfig(2, 4, 2, 1, AssetPool.getShader("assets/shaders/default.glsl"));

    public SpriteRenderController() {
        this.batches = new ArrayList<>();
    }

    public void add (GameObject go) {
        SpriteRenderer spr = go.getComponent(SpriteRenderer.class);
        if (spr != null) {
            add (spr);
        }
    }

    private void add (SpriteRenderer sprite) {
        boolean added = false;
        for (SpriteRenderBatch batch : batches) {
            if (batch.hasRoom() && batch.zIndex() == sprite.gameObject.zIndex()) {
                Texture tex = sprite.getTexture();
                if (tex == null || (batch.hasTexture(tex) || batch.hasTextureRoom())) {
                    batch.addSprite(sprite);
                    added = true;
                    break;
                }
            }
        }

        if (!added) {
            SpriteRenderBatch batch = new SpriteRenderBatch(MAX_BATCH_SIZE, sprite.gameObject.zIndex(), spriteRenderConfig);
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
