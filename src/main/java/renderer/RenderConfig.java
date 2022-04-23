package renderer;

public class RenderConfig {
    protected int POS_SIZE;
    private int COLOR_SIZE;
    private int TEX_COORDS_SIZE;
    private int TEX_ID_SIZE;

    private int POS_OFFSET;
    private int COLOR_OFFSET;
    private int TEX_COORDS_OFFSET;
    private int TEX_ID_OFFSET;
    private int VERTEX_SIZE;
    private int VERTEX_SIZE_BYTES;

    public int POS_SIZE() {
        return POS_SIZE;
    }

    public int COLOR_SIZE() {
        return COLOR_SIZE;
    }

    public int TEX_COORDS_SIZE() {
        return TEX_COORDS_SIZE;
    }

    public int TEX_ID_SIZE() {
        return TEX_ID_SIZE;
    }

    public int POS_OFFSET() {
        return POS_OFFSET;
    }

    public int COLOR_OFFSET() {
        return COLOR_OFFSET;
    }

    public int TEX_COORDS_OFFSET() {
        return TEX_COORDS_OFFSET;
    }

    public int TEX_ID_OFFSET() {
        return TEX_ID_OFFSET;
    }

    public int VERTEX_SIZE() {
        return VERTEX_SIZE;
    }

    public int VERTEX_SIZE_BYTES() {
        return VERTEX_SIZE_BYTES;
    }

    public Shader shader() {
        return shader;
    }

    private Shader shader;

    public RenderConfig (int POS_SIZE, int COLOR_SIZE, int TEX_COORDS_SIZE, int TEX_ID_SIZE, Shader shader) {
        this.POS_SIZE = POS_SIZE;
        this.COLOR_SIZE = COLOR_SIZE;
        this.TEX_COORDS_SIZE = TEX_COORDS_SIZE;
        this.TEX_ID_SIZE = TEX_ID_SIZE;
        this.POS_OFFSET = 0;
        this.COLOR_OFFSET = POS_OFFSET + POS_SIZE * Float.BYTES;
        this.TEX_COORDS_OFFSET = COLOR_OFFSET + COLOR_SIZE * Float.BYTES;
        this.TEX_ID_OFFSET = TEX_COORDS_OFFSET + TEX_COORDS_SIZE * Float.BYTES;
        this.VERTEX_SIZE = POS_SIZE + COLOR_SIZE + TEX_COORDS_SIZE + TEX_ID_SIZE;
        this.VERTEX_SIZE_BYTES = VERTEX_SIZE * Float.BYTES;
        this.shader = shader;
    }

    public boolean useTextures () {
        return this.TEX_COORDS_SIZE != 0;
    }
}
