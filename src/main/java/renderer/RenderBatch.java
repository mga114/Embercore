package renderer;

import components.SpriteRenderer;
import embercore.Window;
import org.joml.Vector2f;
import org.joml.Vector4f;
import util.AssetPool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public abstract class RenderBatch implements Comparable<RenderBatch> {

    protected RenderConfig conf;
    private int zIndex;
    protected int maxBatchSize;
    protected int numElements;
    protected boolean hasRoom;

    protected ArrayList<Texture> textures;
    private int[] texSlots = {0, 1, 2, 3, 4, 5, 6, 7};
    protected float[] vertices;

    private int vaoID, vboID;

    protected boolean rebufferData = true;

    public RenderBatch(int maxBatchSize, int zIndex, RenderConfig conf) {
        this.zIndex = zIndex;
        this.maxBatchSize = maxBatchSize;
        this.conf = conf;

        //4 vertices quads
        vertices = new float[maxBatchSize * 4 * conf.VERTEX_SIZE()];

        this.numElements = 0;
        this.hasRoom = true;
        if (conf.useTextures()) this.textures = new ArrayList<>();
    }

    public void start() {
        //Generate and bind a Vertex Array Object
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        //Allocate the space for vertices
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, (long) vertices.length * Float.BYTES, GL_DYNAMIC_DRAW);

        //Create and upload indices buffer
        int eboID = glGenBuffers();
        int[] indices = generateIndices();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

        //Enable the buffer attrib pointers
        glVertexAttribPointer(0, conf.POS_SIZE(), GL_FLOAT, false, conf.VERTEX_SIZE_BYTES(), conf.POS_OFFSET());
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, conf.COLOR_SIZE(), GL_FLOAT, false, conf.VERTEX_SIZE_BYTES(), conf.COLOR_OFFSET());
        glEnableVertexAttribArray(1);

        if (conf.useTextures()) {
            glVertexAttribPointer(2, conf.TEX_COORDS_SIZE(), GL_FLOAT, false, conf.VERTEX_SIZE_BYTES(), conf.TEX_COORDS_OFFSET());
            glEnableVertexAttribArray(2);

            glVertexAttribPointer(3, conf.TEX_ID_SIZE(), GL_FLOAT, false, conf.VERTEX_SIZE_BYTES(), conf.TEX_ID_OFFSET());
            glEnableVertexAttribArray(3);
        }
    }

    protected abstract void render();

    protected void finishRender () {
        //LOAD VERTEX PROPERTIES BEFORE THIS METHOD

        //Only re-buffer if something has changed
        if (rebufferData) {
            glBindBuffer(GL_ARRAY_BUFFER, vboID);
            glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);
        }

        //use shader
        conf.shader().use();
        //Do these lines need to be here?
        conf.shader().uploadMat4f("uProjection", Window.getScene().camera().getProjectionMatrix());
        conf.shader().uploadMat4f("uView", Window.getScene().camera().getViewMatrix());

        if (conf.useTextures()) {
            //Bind each texture
            for (int i = 0; i < textures.size(); i++) {
                glActiveTexture(GL_TEXTURE0 + i + 1);
                textures.get(i).bind();
            }
            conf.shader().uploadIntArray("uTextures", texSlots);
        }

        glBindVertexArray(vaoID);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, this.numElements * 6, GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);
        //Unbind each texture
        if (conf.useTextures()) {
            for (Texture texture : textures) {
                texture.unbind();
            }
        }

        conf.shader().detach();
    }

    protected abstract void loadVertexProperties (int index);

    protected int[] generateIndices() {
        //6 indices per quad (3 per triangle)
        int[] elements = new int[6 * maxBatchSize];
        for (int i = 0; i < maxBatchSize; i++) {
            loadElementIndices(elements, i);
        }

        return elements;
    }
    
    private void loadElementIndices(int[] elements, int index) {
        int offsetArrayIndex = 6 * index;
        int offset = 4 * index;

        //Triangle 1
        elements[offsetArrayIndex] = offset + 3;
        elements[offsetArrayIndex + 1] = offset + 2;
        elements[offsetArrayIndex + 2] = offset + 0;
        //Triangle 2
        elements[offsetArrayIndex + 3] = offset + 0;
        elements[offsetArrayIndex + 4] = offset + 2;
        elements[offsetArrayIndex + 5] = offset + 1;
    }

    public boolean hasRoom () {
        return this.hasRoom;
    }

    public boolean hasTextureRoom() {
        return this.textures.size() < 8;
    }

    public boolean hasTexture (Texture tex) {
        return this.textures.contains(tex);
    }

    public int zIndex ( ) {return this.zIndex;}

    @Override
    public int compareTo(RenderBatch renderBatch) {
        return Integer.compare(this.zIndex, renderBatch.zIndex());
    }
}
