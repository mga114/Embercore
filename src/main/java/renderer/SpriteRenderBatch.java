package renderer;

import components.SpriteRenderer;
import org.joml.Vector2f;
import org.joml.Vector4f;

import java.util.List;

public class SpriteRenderBatch extends RenderBatch{

    private SpriteRenderer[] sprites;

    public SpriteRenderBatch(int maxBatchSize, int zIndex, RenderConfig conf) {
        super(maxBatchSize, zIndex, conf);
        this.sprites = new SpriteRenderer[maxBatchSize];
    }

    public void addSprite(SpriteRenderer spr) {
        //Get index and add renderObject
        int index = this.numElements;
        this.sprites[index] = spr;
        this.numElements++;

        if (spr.getTexture() != null) {
            if (!textures.contains(spr.getTexture())) {
                textures.add(spr.getTexture());
            }
        }

        //Add properties to local vertices array
        loadVertexProperties(index);

        if (numElements >= this.maxBatchSize) {
            this.hasRoom = false;
        }
    }

    @Override
    protected void loadVertexProperties (int index) {
        SpriteRenderer sprite = this.sprites[index];

        //Find offset within array (4 vertices per sprite)
        int offset = index * 4 * conf.VERTEX_SIZE();

        Vector4f color = sprite.getColor();
        Vector2f[] texCoords = sprite.getTexCoords();

        //Find the texture of the sprite
        int texId = 0;
        if (sprite.getTexture() != null) {
            for (int i = 0; i < textures.size(); i++) {
                if (textures.get(i) == sprite.getTexture()) {
                    texId = i + 1;
                    break;
                }
            }
        }

        float xAdd = 1.0f;
        float yAdd = 1.0f;
        for (int i = 0; i < 4; i++) {
            if (i == 1) {
                yAdd = 0.0f;
            } else if (i == 2) {
                xAdd = 0.0f;
            } else if (i == 3) {
                yAdd = 1.0f;
            }

            //Load position
            vertices[offset] = sprite.gameObject.transform.position.x + (xAdd * sprite.gameObject.transform.scale.x);
            vertices[offset + 1] = sprite.gameObject.transform.position.y + (yAdd * sprite.gameObject.transform.scale.y);

            //Load colour
            vertices[offset + 2] = color.x;
            vertices[offset + 3] = color.y;
            vertices[offset + 4] = color.z;
            vertices[offset + 5] = color.w;

            //Load texture coordinates
            vertices[offset + 6] = texCoords[i].x;
            vertices[offset + 7] = texCoords[i].y;
            //Load texture id
            vertices[offset + 8] = texId;

            offset += conf.VERTEX_SIZE();
        }
    }

    @Override
    protected void render() {
        this.rebufferData = false;

        for (int i = 0; i < numElements; i++) {
            SpriteRenderer spr = sprites[i];
            if (spr.isDirty()) {
                loadVertexProperties(i);
                spr.setClean();
                rebufferData = true;
            }
        }

        this.finishRender();
    }
}
