package com.kerwhite.kmod.event;

import com.kerwhite.kmod.ModRenderType;
import com.kerwhite.kmod.kmod;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = kmod.MODID,value = Dist.CLIENT)
public class RenderLevelStageEventHandler
{
    public static void renderHitOutline(PoseStack pPoseStack, VertexConsumer pConsumer, Entity pEntity, double pCamX, double pCamY, double pCamZ, BlockPos pPos, BlockState pState)
    {
        renderShape(pPoseStack, pConsumer, pState.getCollisionShape(pEntity.level(), pPos, CollisionContext.of(pEntity)), (double)pPos.getX() - pCamX, (double)pPos.getY() - pCamY, (double)pPos.getZ() - pCamZ, 0.0F, 0.0F, 0.0F, 0.4F);
    }
    public static void renderShape(PoseStack pPoseStack, VertexConsumer pConsumer, VoxelShape pShape, double pX, double pY, double pZ, float pRed, float pGreen, float pBlue, float pAlpha) {
        PoseStack.Pose posestack$pose = pPoseStack.last();
        pShape.forAllEdges((p_234280_, p_234281_, p_234282_, p_234283_, p_234284_, p_234285_) -> {
            float f = (float)(p_234283_ - p_234280_);
            float f1 = (float)(p_234284_ - p_234281_);
            float f2 = (float)(p_234285_ - p_234282_);
            float f3 = Mth.sqrt(f * f + f1 * f1 + f2 * f2);
            f /= f3;
            f1 /= f3;
            f2 /= f3;
            pConsumer.vertex(posestack$pose.pose(), (float)(p_234280_ + pX), (float)(p_234281_ + pY), (float)(p_234282_ + pZ)).color(pRed, pGreen, pBlue, pAlpha).normal(posestack$pose.normal(), f, f1, f2).endVertex();
            pConsumer.vertex(posestack$pose.pose(), (float)(p_234283_ + pX), (float)(p_234284_ + pY), (float)(p_234285_ + pZ)).color(pRed, pGreen, pBlue, pAlpha).normal(posestack$pose.normal(), f, f1, f2).endVertex();
        });
    }
    @SubscribeEvent
    public static void onRenderLevelStageEvent(RenderLevelStageEvent event)
    {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_SOLID_BLOCKS){return;}
        var cam = Minecraft.getInstance().gameRenderer.getMainCamera();
        event.getPoseStack().pushPose();
        event.getPoseStack().translate(-cam.getPosition().x, -cam.getPosition().y, -cam.getPosition().z);
        var consumer = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(ModRenderType.SIMPLE_VERTEX);
        var mat = event.getPoseStack().last().pose();
        consumer.vertex(mat,0,0,0).color(255,255,255,255).endVertex();
        consumer.vertex(mat,5,0,0).color(255,255,255,255).endVertex();
        consumer.vertex(mat,5,5,0).color(255,255,255,255).endVertex();
        consumer.vertex(mat,0,5,0).color(255,255,255,255).endVertex();
        Minecraft.getInstance().renderBuffers().bufferSource().endBatch(ModRenderType.SIMPLE_VERTEX);
//        int pPackedLight = 255;
        event.getPoseStack().popPose();
//        int pPackedOverlay = OverlayTexture.NO_OVERLAY;

//        Minecraft mc = Minecraft.getInstance();
//        if (mc.level == null || mc.player == null){return;}
//        PoseStack poseStack = event.getPoseStack();
//        RenderSystem.disableDepthTest();
//        MultiBufferSource.BufferSource bufferSource = mc.renderBuffers().bufferSource();
//        BlockPos targetPos = new BlockPos(0, 0, 0);
//        BlockState customBlockState = Blocks.LECTERN.defaultBlockState();
//        VertexConsumer vertexConsumer = bufferSource.getBuffer(ModRenderType.TOP_LAYER_LINE_TARGET);
//        Vec3 playerpos = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
//        poseStack.pushPose();
//        poseStack.translate(
//                targetPos.getX() - mc.gameRenderer.getMainCamera().getPosition().x,
//                targetPos.getY() - mc.gameRenderer.getMainCamera().getPosition().y,
//                targetPos.getZ() - mc.gameRenderer.getMainCamera().getPosition().z
//        );
//        BlockRenderDispatcher blockRenderer = mc.getBlockRenderer();
//        //blockRenderer.renderSingleBlock(customBlockState,poseStack,bufferSource,pPackedLight,pPackedOverlay,ModelData.EMPTY, ModRenderType.TOP_LAYER_TARGET);
//        poseStack.popPose();
//        RenderLevelStageEventHandler.renderHitOutline(poseStack,vertexConsumer,Minecraft.getInstance().gameRenderer.getMainCamera().getEntity(),playerpos.x,playerpos.y, playerpos.z,targetPos,customBlockState);
//        RenderSystem.enableDepthTest();

    }

}
