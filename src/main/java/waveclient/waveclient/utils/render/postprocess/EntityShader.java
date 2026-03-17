package waveclient.waveclient.utils.render.postprocess;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import waveclient.waveclient.mixininterface.IWorldRenderer;
import waveclient.waveclient.utils.render.CustomOutlineVertexConsumerProvider;
import net.minecraft.entity.Entity;

import static waveclient.waveclient.WaveClient.mc;

public abstract class EntityShader extends PostProcessShader {
    public final CustomOutlineVertexConsumerProvider vertexConsumerProvider;

    protected EntityShader(RenderPipeline pipeline) {
        super(pipeline);
        this.vertexConsumerProvider = new CustomOutlineVertexConsumerProvider();
    }

    public abstract boolean shouldDraw(Entity entity);

    @Override
    protected void preDraw() {
        ((IWorldRenderer) mc.worldRenderer).wave$pushEntityOutlineFramebuffer(framebuffer);
    }

    @Override
    protected void postDraw() {
        ((IWorldRenderer) mc.worldRenderer).wave$popEntityOutlineFramebuffer();
    }

    public void submitVertices() {
        submitVertices(vertexConsumerProvider::draw);
    }
}
