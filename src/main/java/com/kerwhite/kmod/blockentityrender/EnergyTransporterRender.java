package com.kerwhite.kmod.blockentityrender;

import com.kerwhite.kmod.block.BlockEnergyTransporter;
import com.kerwhite.kmod.blockentity.BlockEntityEnergyTransporter;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class EnergyTransporterRender implements BlockEntityRenderer<BlockEntityEnergyTransporter> {
    public EnergyTransporterRender(BlockEntityRendererProvider.Context pContext)
    {

    }
    @Override
    public void render(BlockEntityEnergyTransporter pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay)
    {
      //  pPoseStack.pushPose();
     //   pPoseStack.translate(1,0,0);
      //  BlockRenderDispatcher blockRenderDispatcher = Minecraft.getInstance().getBlockRenderer();
      //  BlockState state = Blocks.CHEST.defaultBlockState();
       // blockRenderDispatcher.renderSingleBlock(state,pPoseStack,pBuffer,pPackedLight,pPackedOverlay);
       // pPoseStack.popPose();
        pPoseStack.pushPose();
        pPoseStack.translate(0.5,1.45+(Math.sin(((double)pBlockEntity.getLevel().getGameTime())/10)/8),0.5);
        pPoseStack.scale(0.8f,0.8f,0.8f);
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack stack = new ItemStack(Items.ENDER_EYE);
        BakedModel bakedModel = itemRenderer.getModel(stack,pBlockEntity.getLevel(),null,0);
        pPoseStack.mulPose(new Quaternionf().fromAxisAngleDeg(new Vector3f(0,1,0), pBlockEntity.getLevel().getGameTime()*2));
        pPoseStack.mulPose(new Quaternionf().fromAxisAngleDeg(new Vector3f(1,0,0), pBlockEntity.getLevel().getGameTime()*2));
        pPoseStack.mulPose(new Quaternionf().fromAxisAngleDeg(new Vector3f(0,0,-1), pBlockEntity.getLevel().getGameTime()*2));
        itemRenderer.render(stack, ItemDisplayContext.FIXED,true,pPoseStack,pBuffer,255,pPackedOverlay,bakedModel);
        pPoseStack.popPose();
    }
}