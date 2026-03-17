/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.utils.render;

import net.minecraft.client.render.VertexConsumerProvider;

public interface IVertexConsumerProvider extends VertexConsumerProvider {
    void setOffset(int offsetX, int offsetY, int offsetZ);
}
