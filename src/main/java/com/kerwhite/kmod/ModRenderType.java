package com.kerwhite.kmod;

import com.kerwhite.kmod.only.client.render.ModShaderStateShard;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderType.CompositeState;
import net.minecraft.client.renderer.blockentity.TheEndPortalRenderer;

import java.util.OptionalDouble;
@SuppressWarnings("All")
public class ModRenderType extends RenderStateShard
{
    public static RenderType TOP_LAYER_TARGET;
    public static RenderType TOP_LAYER_LINE_TARGET;
    public static RenderType SIMPLE_VERTEX;
    public static RenderType END_PROTAL;
    public ModRenderType(String pName, Runnable pSetupState, Runnable pClearState)
    {
        super(pName, pSetupState, pClearState);
    }
    static
    {
        TOP_LAYER_TARGET = RenderType.create("top_layer_target", DefaultVertexFormat.BLOCK, Mode.QUADS, 2097152, true, false, CompositeState.builder().setLightmapState(RenderStateShard.LIGHTMAP).setShaderState(RenderStateShard.RENDERTYPE_CUTOUT_SHADER).setTextureState(BLOCK_SHEET_MIPPED).setDepthTestState(RenderStateShard.NO_DEPTH_TEST).createCompositeState(true));
        TOP_LAYER_LINE_TARGET = RenderType.create("top_layer_line_target", DefaultVertexFormat.POSITION_COLOR_NORMAL, Mode.LINES, 256, true, false, CompositeState.builder().setShaderState(RENDERTYPE_LINES_SHADER).setLineState(new RenderStateShard.LineStateShard(OptionalDouble.empty())).setLayeringState(VIEW_OFFSET_Z_LAYERING).setTransparencyState(TRANSLUCENT_TRANSPARENCY).setOutputState(RenderStateShard.ITEM_ENTITY_TARGET).setWriteMaskState(RenderStateShard.COLOR_DEPTH_WRITE).setCullState(NO_CULL).setDepthTestState(RenderStateShard.NO_DEPTH_TEST).createCompositeState(false));
        SIMPLE_VERTEX = RenderType.create("simple_vertex", DefaultVertexFormat.POSITION_COLOR, Mode.QUADS, 2097152, true, false, RenderType.CompositeState.builder().setShaderState(new ShaderStateShard(GameRenderer::getPositionColorShader)).setTransparencyState(RenderType.TRANSLUCENT_TRANSPARENCY).setTextureState(RenderType.NO_TEXTURE).setDepthTestState(RenderType.NO_DEPTH_TEST).setCullState(RenderStateShard.NO_CULL).createCompositeState(true));
    }
    public static void registerRenderType()
    {
        END_PROTAL= RenderType.create("kmod_end_portal", DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS, 256, false, false, RenderType.CompositeState.builder().setShaderState(ModShaderStateShard.END_PORTAL).setTextureState(RenderStateShard.MultiTextureStateShard.builder().add(TheEndPortalRenderer.END_SKY_LOCATION, false, false).add(TheEndPortalRenderer.END_PORTAL_LOCATION, false, false).build()).createCompositeState(false));
    }
}