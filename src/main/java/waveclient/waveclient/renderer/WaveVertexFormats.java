/*
 * This file is part of the Wave Client distribution (https://github.com/WaveDevelopment/wave-client).
 * Copyright (c) Wave Development.
 */

package waveclient.waveclient.renderer;

import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormatElement;

public abstract class WaveVertexFormats {
    public static final VertexFormat POS2 = VertexFormat.builder()
        .add("Position", WaveVertexFormatElements.POS2)
        .build();

    public static final VertexFormat POS2_COLOR = VertexFormat.builder()
        .add("Position", WaveVertexFormatElements.POS2)
        .add("Color", VertexFormatElement.COLOR)
        .build();

    public static final VertexFormat POS2_TEXTURE_COLOR = VertexFormat.builder()
        .add("Position", WaveVertexFormatElements.POS2)
        .add("Texture", VertexFormatElement.UV)
        .add("Color", VertexFormatElement.COLOR)
        .build();

    private WaveVertexFormats() {}
}
