package com.kerwhite.kmod.only.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("ALL")
public class MultiBakedModel implements BakedModel
{
    private final List<BakedModel> models = new ArrayList<>();
    private final ItemOverrides overrides = new ItemOverrides()
    {
        @Override
        public @Nullable BakedModel resolve(BakedModel model, ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed)
        {
            List<BakedModel> $models = new ArrayList<>();
            CompoundTag tag = stack.getOrCreateTag();
            if (tag.contains("multimodel") && tag.contains("modelcount") && tag.contains("models"))
            {
                ModelManager manager = Minecraft.getInstance().getModelManager();
                int count = tag.getInt("modelcount");
                CompoundTag models = tag.getCompound("models");
                for (int i = 0; i < count; i++)
                {
                    String modelString = "model";
                    String modelModId = modelString.concat(Integer.toString(i)).concat("_modid");
                    String modelLocation = modelString.concat(Integer.toString(i)).concat("_location");
                    if (models.contains(modelModId) && models.contains(modelLocation))
                    {
                        String $modelModId = tag.getString(modelModId);
                        String $modelLocation = tag.getString(modelLocation);
                        ResourceLocation $$modelLocation = ResourceLocation.fromNamespaceAndPath($modelModId, $modelLocation);
                        BakedModel $model = manager.getModel($$modelLocation);
                        $models.add($model);
                    }
                    else
                    {
                        break;
                    }
                }
                return new MultiBakedModel(((BakedModel[]) $models.toArray()));
            }
            return super.resolve(model, stack, level, entity, seed);
        }
    };

    public MultiBakedModel(BakedModel... models)
    {
        Objects.requireNonNull(models);
        for (BakedModel model : models)
        {
            this.models.add(model);
        }
    }

    public MultiBakedModel getModel(ResourceLocation... locations)
    {
        ModelManager manager = Minecraft.getInstance().getModelManager();
        List<BakedModel> models = new ArrayList<>();
        for (ResourceLocation location : locations)
        {
            models.add(manager.getModel(location));
        }
        return new MultiBakedModel(((BakedModel[]) models.toArray()));
    }

    @Override
    public @NotNull List<BakedQuad> getQuads(@Nullable BlockState p_235039_, @Nullable Direction p_235040_, @NotNull RandomSource p_235041_)
    {
        List<BakedQuad> quads = new ArrayList<>();
        models.forEach((models) ->
        {
            models.getQuads(p_235039_, p_235040_, p_235041_).forEach((quad) ->
            {
                quads.add(quad);
            });
        });
        return quads;
    }

    @Override
    public boolean useAmbientOcclusion()
    {
        return false;
    }

    @Override
    public boolean isGui3d()
    {
        return false;
    }

    @Override
    public boolean usesBlockLight()
    {
        return false;
    }

    @Override
    public boolean isCustomRenderer()
    {
        return false;
    }

    @Override
    public @NotNull TextureAtlasSprite getParticleIcon()
    {
        return this.models.get(0).getParticleIcon();
    }

    @Override
    public @NotNull BakedModel applyTransform(@NotNull ItemDisplayContext transformType, @NotNull PoseStack poseStack, boolean applyLeftHandTransform)
    {
        List<BakedModel> transfromModels = new ArrayList<>();
        this.models.forEach((model) ->
        {
            poseStack.pushPose();
            transfromModels.add(model.applyTransform(transformType, poseStack, applyLeftHandTransform));
            poseStack.popPose();
        });
        return new MultiBakedModel(((BakedModel[]) transfromModels.toArray()));
    }

    @Override
    public @NotNull ItemOverrides getOverrides()
    {
        return this.overrides;
    }
}
