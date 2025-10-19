package com.kerwhite.kmod.corelib.render;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import javax.annotation.Nullable;
@SuppressWarnings("ALL")
public class GuiRenderHelper
{
    public static Minecraft minecraft = Minecraft.getInstance();
    public static void renderItem(GuiGraphics guiGraphics,@Nullable LivingEntity p_282619_, @Nullable Level p_281754_, ItemStack p_281675_, int p_281271_, int p_282210_, int p_283260_, int p_281995_)
    {
        if (!p_281675_.isEmpty())
        {
            BakedModel bakedmodel = GuiRenderHelper.minecraft.getItemRenderer().getModel(p_281675_, p_281754_, p_282619_, p_283260_);
            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate((float)(p_281271_ + 8), (float)(p_282210_ + 8), (float)(150 + (bakedmodel.isGui3d() ? p_281995_ : 0)));
            try {
                guiGraphics.pose().mulPoseMatrix((new Matrix4f()).scaling(1.0F, -1.0F, 1.0F));
                guiGraphics.pose().scale(16.0F, 16.0F, 16.0F);
                boolean flag = !bakedmodel.usesBlockLight();
                if (flag)
                {
                    Lighting.setupForFlatItems();
                }
                GuiRenderHelper.minecraft.getItemRenderer().render(p_281675_, ItemDisplayContext.GUI, false, guiGraphics.pose(), guiGraphics.bufferSource(), 15728880, OverlayTexture.NO_OVERLAY, bakedmodel);
                guiGraphics.flush();
                if (flag)
                {
                    Lighting.setupFor3DItems();
                }
            } finally
            {
                guiGraphics.pose().popPose();
            }
        }
    }
    public static void renderBlock(GuiGraphics guiGraphics,@NotNull Quaternionf quaternionf,@NotNull BlockState blockState, int x ,int y ,int size)
    {
        BlockRenderDispatcher renderer = GuiRenderHelper.minecraft.getBlockRenderer();
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(x, y , 0);
        guiGraphics.pose().scale(size, size, size);
        guiGraphics.pose().mulPoseMatrix(new Matrix4f().scaling(1, -1, 1));
        guiGraphics.pose().mulPose(quaternionf);
        Lighting.setupForFlatItems();
        renderer.renderSingleBlock(blockState,guiGraphics.pose(),guiGraphics.bufferSource(),255,OverlayTexture.NO_OVERLAY);
        Lighting.setupFor3DItems();
        guiGraphics.pose().popPose();
    }

    public static void renderEntity(GuiGraphics p_282665_, int p_283622_, int p_283401_, int p_281360_, Quaternionf p_281880_, @Nullable Quaternionf p_282882_, LivingEntity p_282466_)
    {
        p_282665_.pose().pushPose();
        p_282665_.pose().translate((double)p_283622_, (double)p_283401_, 50.0D);
        p_282665_.pose().mulPoseMatrix((new Matrix4f()).scaling((float)p_281360_, (float)p_281360_, (float)(-p_281360_)));
        p_282665_.pose().mulPose(p_281880_);
        Lighting.setupForEntityInInventory();
        EntityRenderDispatcher entityrenderdispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        if (p_282882_ != null)
        {
            p_282882_.conjugate();
            entityrenderdispatcher.overrideCameraOrientation(p_282882_);
        }
        entityrenderdispatcher.setRenderShadow(false);
        RenderSystem.runAsFancy(() -> {
            entityrenderdispatcher.render(p_282466_, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, p_282665_.pose(), p_282665_.bufferSource(), 15728880);
        });
        p_282665_.flush();
        entityrenderdispatcher.setRenderShadow(true);
        p_282665_.pose().popPose();
        Lighting.setupFor3DItems();
    }
}
