package com.kerwhite.kmod.mixin;

import com.kerwhite.kmod.ModRenderType;
import com.kerwhite.kmod.only.client.render.BakedQuadHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;
import java.util.List;

@Mixin(ItemRenderer.class)
public class ItemRenderMixin
{
    @Inject(method = "renderQuadList", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/VertexConsumer;putBulkData(Lcom/mojang/blaze3d/vertex/PoseStack$Pose;Lnet/minecraft/client/renderer/block/model/BakedQuad;FFFFIIZ)V", shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILHARD)
    public void renderQuadList(PoseStack p_115163_, VertexConsumer p_115164_, List<BakedQuad> p_115165_, ItemStack p_115166_, int p_115167_, int p_115168_, CallbackInfo ci, boolean flag, PoseStack.Pose posestack$pose, Iterator var9, BakedQuad bakedquad, int i, float f, float f1, float f2)
    {
        BakedQuadHelper helper = BakedQuadHelper.fromRenderType(ModRenderType.END_PROTAL);
        //VertexConsumer combinedConsumer = VertexMultiConsumer.create(originalConsumer, portalConsumer);
        helper.vertexConsumer.putBulkData(posestack$pose, bakedquad, f, f1, f2, 1.0F, p_115167_, p_115168_, true);
    }
}
