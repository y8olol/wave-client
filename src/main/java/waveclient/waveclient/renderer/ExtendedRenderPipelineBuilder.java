/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.renderer;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import waveclient.waveclient.mixininterface.IRenderPipeline;

public class ExtendedRenderPipelineBuilder extends RenderPipeline.Builder {
    private boolean lineSmooth;

    public ExtendedRenderPipelineBuilder(RenderPipeline.Snippet... snippets) {
        for (RenderPipeline.Snippet snippet : snippets) {
            withSnippet(snippet);
        }
    }

    public ExtendedRenderPipelineBuilder withLineSmooth() {
        lineSmooth = true;
        return this;
    }

    @Override
    public RenderPipeline build() {
        RenderPipeline pipeline = super.build();
        ((IRenderPipeline) pipeline).wave$setLineSmooth(lineSmooth);

        return pipeline;
    }
}
