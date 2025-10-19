package com.kerwhite.kmod;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import java.util.OptionalDouble;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderType.CompositeState;
@SuppressWarnings("All")
public class ModRenderType extends RenderStateShard
{
    public static RenderType TOP_LAYER_TARGET;
    public static RenderType TOP_LAYER_LINE_TARGET;

    public ModRenderType(String pName, Runnable pSetupState, Runnable pClearState)
    {
        super(pName, pSetupState, pClearState);
    }
    static
    {
        TOP_LAYER_TARGET = RenderType.create("top_layer_target", DefaultVertexFormat.BLOCK, Mode.QUADS, 2097152, true, false, CompositeState.builder().setLightmapState(RenderStateShard.LIGHTMAP).setShaderState(RenderStateShard.RENDERTYPE_CUTOUT_SHADER).setTextureState(BLOCK_SHEET_MIPPED).setDepthTestState(RenderStateShard.NO_DEPTH_TEST).createCompositeState(true));
        TOP_LAYER_LINE_TARGET = RenderType.create("top_layer_line_target", DefaultVertexFormat.POSITION_COLOR_NORMAL, Mode.LINES, 256, true, false, CompositeState.builder().setShaderState(RENDERTYPE_LINES_SHADER).setLineState(new RenderStateShard.LineStateShard(OptionalDouble.empty())).setLayeringState(VIEW_OFFSET_Z_LAYERING).setTransparencyState(TRANSLUCENT_TRANSPARENCY).setOutputState(RenderStateShard.ITEM_ENTITY_TARGET).setWriteMaskState(RenderStateShard.COLOR_DEPTH_WRITE).setCullState(NO_CULL).setDepthTestState(RenderStateShard.NO_DEPTH_TEST).createCompositeState(false));

    }
}