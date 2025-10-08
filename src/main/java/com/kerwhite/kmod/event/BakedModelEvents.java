package com.kerwhite.kmod.event;

import com.kerwhite.kmod.bakedmodel.SpecialBakedModel;
import com.kerwhite.kmod.regiter.ItemRegister;
import com.kerwhite.kmod.regiter.register;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class BakedModelEvents
{
    @SubscribeEvent
    public static void onModelBaked(ModelEvent.ModifyBakingResult event){
        // wrench item model
        Map<ResourceLocation, BakedModel> modelRegistry = event.getModels();
        ModelResourceLocation location = new ModelResourceLocation(BuiltInRegistries.ITEM.getKey(ItemRegister.DIAMOND_SPEAR.get()), "inventory");
        ModelResourceLocation wooden_spear = new ModelResourceLocation(BuiltInRegistries.ITEM.getKey(ItemRegister.WOODEN_SPEAR.get()), "inventory");
        ModelResourceLocation iron_spear = new ModelResourceLocation(BuiltInRegistries.ITEM.getKey(ItemRegister.IRON_SPEAR.get()), "inventory");
        ModelResourceLocation stone_spear = new ModelResourceLocation(BuiltInRegistries.ITEM.getKey(ItemRegister.STONE_SPEAR.get()), "inventory");

        BakedModel existingModel = modelRegistry.get(location);
        BakedModel wooden_spear_model = modelRegistry.get(wooden_spear);
        BakedModel iron_spear_model = modelRegistry.get(iron_spear);
        BakedModel stone_spear_model = modelRegistry.get(stone_spear);
        if (existingModel == null)
        {
           throw new RuntimeException("Did not find Obsidian Hidden in registry");
        } else if (existingModel instanceof SpecialBakedModel)
        {
            throw new RuntimeException("Tried to replaceObsidian Hidden twice");
        } else
        {
            SpecialBakedModel obsidianWrenchBakedModel = new SpecialBakedModel(existingModel);
            SpecialBakedModel wooden_spear_bakedmodel = new SpecialBakedModel(wooden_spear_model);
            SpecialBakedModel stone_spear_bakedmodel = new SpecialBakedModel(stone_spear_model);
            SpecialBakedModel iron_spear_bakedmodel = new SpecialBakedModel(iron_spear_model);
            event.getModels().put(location, obsidianWrenchBakedModel);
            event.getModels().put(wooden_spear,wooden_spear_bakedmodel);
            event.getModels().put(stone_spear,stone_spear_bakedmodel);
            event.getModels().put(iron_spear,iron_spear_bakedmodel);
        }
    }
}
