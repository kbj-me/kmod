package com.kerwhite.kmod.only.client.model;

import com.kerwhite.kmod.kmod;
import com.kerwhite.kmod.register.ItemRegister;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings("ALL")
public class MultiBakedModel implements BakedModel
{
    private final List<BakedModel> models = new ArrayList<>();

    public MultiBakedModel()
    {
    }

    private final ItemOverrides overrides = new ItemOverrides()
    {
        /*

         */
        @Override
        public @Nullable BakedModel resolve(BakedModel model, ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed)
        {
            try
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
                            String $modelModId = models.getString(modelModId);
                            String $modelLocation = models.getString(modelLocation);
                            ResourceLocation $$modelLocation = ResourceLocation.fromNamespaceAndPath($modelModId, $modelLocation);
                            ModelResourceLocation modelResourceLocation = new ModelResourceLocation($$modelLocation, "inventory");
                            BakedModel $model = manager.getModel(modelResourceLocation);
                            $models.add($model);
                        }
                        else
                        {
                            break;
                        }
                    }
                    return new MultiBakedModel($models.toArray(new BakedModel[0]));
                }
                return super.resolve(model, stack, level, entity, seed);
            }
            catch (Exception e)
            {
                return super.resolve(model, stack, level, entity, seed);
            }
        }
    };

    public MultiBakedModel(@NotNull BakedModel... models)
    {
        Objects.requireNonNull(models);
        for (BakedModel model : models)
        {
            this.models.add(model);
        }
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
    public TextureAtlasSprite getParticleIcon()
    {
        return null;
    }


    @Override
    public @NotNull BakedModel applyTransform(@NotNull ItemDisplayContext transformType, @NotNull PoseStack poseStack, boolean applyLeftHandTransform)
    {
        for(BakedModel model : this.models)
        {
            model.applyTransform(transformType, poseStack, applyLeftHandTransform);
        }
        return this;
//        List<BakedModel> transfromModels = new ArrayList<>();
//        this.models.forEach((model) ->
//        {
//            poseStack.pushPose();
//            transfromModels.add(model.applyTransform(transformType, poseStack, applyLeftHandTransform));
//            poseStack.popPose();
//        });
//        return new MultiBakedModel(transfromModels.toArray(new BakedModel[0]));
    }

    @Override
    public @NotNull ItemOverrides getOverrides()
    {
        return this.overrides;
    }

    @Mod.EventBusSubscriber(modid = kmod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class EventHandler
    {
        @SubscribeEvent
        public static void onModelBaked(ModelEvent.ModifyBakingResult event)
        {
            Map<ResourceLocation, BakedModel> modelRegistry = event.getModels();
            ModelResourceLocation location = new ModelResourceLocation(BuiltInRegistries.ITEM.getKey(ItemRegister.MULTI_MODEL_ITEM.get()), "inventory");
            modelRegistry.put(location, new MultiBakedModel());
        }
    }
}
