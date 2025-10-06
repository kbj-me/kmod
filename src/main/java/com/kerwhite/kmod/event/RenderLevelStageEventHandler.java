package com.kerwhite.kmod.event;

import com.kerwhite.kmod.regiter.register;
import com.kerwhite.kmod.kmod;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = kmod.MODID,value = Dist.CLIENT)
public class RenderLevelStageEventHandler
{
    @SubscribeEvent
    public static void onRenderLevelStageEvent(RenderLevelStageEvent event)
    {
        int pPackedLight = 255;
        int pPackedOverlay = OverlayTexture.NO_OVERLAY;
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_SOLID_BLOCKS){return;}
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || mc.player == null){return;}
       // mc.player.sendSystemMessage(Component.literal("2222"));
        PoseStack poseStack = event.getPoseStack();
        MultiBufferSource.BufferSource bufferSource = mc.renderBuffers().bufferSource();
        BlockPos targetPos = new BlockPos(0, 0, 0);
        BlockState customBlockState = register.BLOCKENERGYTRANSPORTER.get().defaultBlockState();
        poseStack.pushPose();
        poseStack.translate(
                targetPos.getX() - mc.gameRenderer.getMainCamera().getPosition().x,
                targetPos.getY() - mc.gameRenderer.getMainCamera().getPosition().y,
                targetPos.getZ() - mc.gameRenderer.getMainCamera().getPosition().z
        );
        BlockRenderDispatcher blockRenderer = mc.getBlockRenderer();
        blockRenderer.renderSingleBlock(customBlockState,poseStack,bufferSource,pPackedLight,pPackedOverlay);
        poseStack.popPose();
        bufferSource.endBatch();

    }

}
