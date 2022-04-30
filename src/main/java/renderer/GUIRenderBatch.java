package renderer;

import gui.GUIComponent;
import org.joml.Vector4f;

public class GUIRenderBatch extends RenderBatch{

    private GUIComponent[] components;

    public GUIRenderBatch(int maxBatchSize, int zIndex, RenderConfig conf) {
        super(maxBatchSize, zIndex, conf);
        this.components = new GUIComponent[maxBatchSize];
    }

    public void addGUI (GUIComponent comp) {
        int index = this.numElements;
        this.components[index] = comp;
        this.numElements++;

        loadVertexProperties(index);

        if (numElements >= this.maxBatchSize) {
            this.hasRoom = false;
        }
    }

    @Override
    protected void render() {
        for (int i = 0; i < numElements; i++) {
            loadVertexProperties(i);
        }

        this.finishRender();
    }

    @Override
    protected void loadVertexProperties(int index) {
        GUIComponent comp = this.components[index];

        int offset = index * 4 * conf.VERTEX_SIZE();

        Vector4f color = comp.getColor();

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

            vertices[offset] = comp.getX() + (xAdd * comp.getWidth());
            vertices[offset + 1] = comp.getY() + (yAdd * comp.getHeight());

            vertices[offset + 2] = color.x;
            vertices[offset + 3] = color.y;
            vertices[offset + 4] = color.z;
            vertices[offset + 5] = color.w;

            offset += conf.VERTEX_SIZE();
        }
    }
}
