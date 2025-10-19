package com.kerwhite.kmod.bakedmodel;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
@SuppressWarnings("All")
public class SpecialBakedModel implements BakedModel
{

    private BakedModel existingModel;
    public SpecialBakedModel(BakedModel existingModel)
    {
        this.existingModel = existingModel;
    }
    @Override
    public @NotNull List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource rand, @NotNull ModelData data, @Nullable RenderType renderType)
    {
        throw new AssertionError("IForgeBakedModel::getQuads should never be called, only IForgeBakedModel::getQuads");
    }
    @Override
    public @NotNull List<BakedQuad> getQuads(@Nullable BlockState pState, @Nullable Direction pDirection, @NotNull RandomSource pRandom)
    {
        return this.existingModel.getQuads(pState, pDirection, pRandom);
    }
    @Override
    public boolean useAmbientOcclusion()
    {
        return this.existingModel.useAmbientOcclusion();
    }

    @Override
    public boolean isGui3d()
    {
        return this.existingModel.isGui3d();
    }
    @Override
    public boolean usesBlockLight()
    {
        return this.existingModel.usesBlockLight();
    }

    @Override
    public boolean isCustomRenderer()
    {
        return true;
    }
    @Override
    public @NotNull TextureAtlasSprite getParticleIcon()
    {
        return this.existingModel.getParticleIcon();
    }
    @Override
    public @NotNull ItemOverrides getOverrides()
    {
        return this.existingModel.getOverrides();
    }
    @Override
    public @NotNull BakedModel applyTransform(@NotNull ItemDisplayContext transformType, @NotNull PoseStack poseStack, boolean applyLeftHandTransform)
    {
        if (transformType == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND || transformType == ItemDisplayContext.FIRST_PERSON_LEFT_HAND)
        {
            return this;
        }
        return this.existingModel.applyTransform(transformType,poseStack,applyLeftHandTransform);
    }
}
