package renderer;

import gui.GUIComponent;
import util.AssetPool;

import java.util.ArrayList;
import java.util.List;

public class GUIRenderController {
    private final int MAX_BATCH_SIZE = 1000;
    private List<GUIRenderBatch> batches;

    private static RenderConfig guiRenderConfig = new RenderConfig (2, 4, 0, 0, AssetPool.getShader("assets/shaders/gui.glsl"));

    public GUIRenderController() {this.batches = new ArrayList<>();}

    public void add(GUIComponent comp) {
        boolean added = false;
        for (GUIRenderBatch batch : batches) {
            if (batch.hasRoom()) {
                batch.addGUI(comp);
                added = true;
                break;
            }
        }

        if (!added) {
            GUIRenderBatch batch = new GUIRenderBatch(MAX_BATCH_SIZE, 1, guiRenderConfig);
            batch.start();
            batches.add(batch);
            batch.addGUI(comp);
        }
    }

    public void render () {
        for (GUIRenderBatch batch : batches) {
            batch.render();
        }
    }
}
