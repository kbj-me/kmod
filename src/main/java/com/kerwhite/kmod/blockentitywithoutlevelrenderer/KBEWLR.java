package com.kerwhite.kmod.blockentitywithoutlevelrenderer;

import com.kerwhite.kmod.register.ItemRegister;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class KBEWLR extends BlockEntityWithoutLevelRenderer
{
    private static final float SCALE_FACTOR = 1.0F / 16.0F;
    private static int degree = 0;
    public KBEWLR()
    {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }
    @Override
    public void renderByItem(ItemStack pStack, @NotNull ItemDisplayContext pDisplayContext, @NotNull PoseStack pPoseStack, @NotNull MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay)
    {
        boolean flag = true;
        Item item = pStack.getItem();
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        BakedModel bakedModel = itemRenderer.getModel(pStack,null,null,1);
        if(item == ItemRegister.DIAMOND_SPEAR.get() ||item == ItemRegister.IRON_SPEAR.get() || item == ItemRegister.WOODEN_SPEAR.get() ||item == ItemRegister.STONE_SPEAR.get() ||item == ItemRegister.GOLDEN_SPEAR.get() )
        {
            flag = pStack.getOrCreateTag().getInt("Mode") == 2;
            pPoseStack.pushPose();
            pPoseStack.translate(0.3F, 0.3F, 0.3F);
            float xOffset = -1 / 32f;
            float zOffset = 0;
            pPoseStack.translate(-xOffset, 0.5F, -zOffset);
            if(flag || pStack.getOrCreateTag().getInt("Mode") == 0)
            {
                pPoseStack.mulPose(Axis.YP.rotationDegrees(270));
                pPoseStack.mulPose(Axis.ZP.rotationDegrees(45));
            }
            else
            {
                pPoseStack.mulPose(Axis.XP.rotationDegrees(90));
                pPoseStack.mulPose(Axis.ZP.rotationDegrees(225));
                pPoseStack.translate(0,-0.3,0);
            }
            pPoseStack.translate(xOffset, 0, zOffset);
            itemRenderer.render(pStack, ItemDisplayContext.NONE, false, pPoseStack, pBuffer, pPackedLight, pPackedOverlay, bakedModel);
            pPoseStack.popPose();
        }
        else
        {


        }

    }
}
