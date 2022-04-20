package renderer;

import gui.GUIComponent;
import util.AssetPool;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class GUIRenderer {
    private final int POS_SIZE = 2;
    private final int COLOR_SIZE = 4;

    private final int POS_OFFSET = 0;
    private final int COLOR_OFFSET = POS_OFFSET + POS_SIZE * Float.BYTES;

    private final int VERTEX_SIZE = 6;
    private final int VERTEX_SIZE_BYTES = VERTEX_SIZE * Float.BYTES;

    private float[] vertices;

    private int vaoID, vboID;
    private Shader shader;

    private GUIRenderer[] guis;

    private GUIComponent comp;

    public GUIRenderer (GUIComponent comp) {
        shader = AssetPool.getShader ("assets/shaders/gui.glsl");
        this.comp = comp;
        vertices = new float [4 * VERTEX_SIZE];
    }

    public void start () {
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        vboID = glGenBuffers();
        glBindBuffer (GL_ARRAY_BUFFER, vboID);
        glBufferData (GL_ARRAY_BUFFER, (long) 4 * VERTEX_SIZE * Float.BYTES, GL_DYNAMIC_DRAW);

        int eboID = glGenBuffers();
        int[] indices = generateIndices();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

        glVertexAttribPointer(0, POS_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, POS_OFFSET);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, COLOR_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, COLOR_OFFSET);
        glEnableVertexAttribArray(1);
    }

    public void render () {
        loadVertexProperties();

        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);

        shader.use();

        glBindVertexArray(vaoID);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);

        shader.detach();
    }

    private void loadVertexProperties () {
        int offset = 0;

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

            vertices[offset + 2] = 0.2f;
            vertices[offset + 3] = 0.2f;
            vertices[offset + 4] = 0.2f;
            vertices[offset + 5] = 0.8f;

            offset += VERTEX_SIZE;
        }
    }

    private int[] generateIndices() {
        int[] elements = new int[6];
        loadElementIndices (elements);
        return elements;
    }

    private void loadElementIndices(int[] elements) {
        elements[0] = 3;
        elements[1] = 2;
        elements[2] = 0;

        elements[3] = 0;
        elements[4] = 2;
        elements[5] = 1;
    }


}
