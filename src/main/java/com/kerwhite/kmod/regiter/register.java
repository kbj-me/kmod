package com.kerwhite.kmod.regiter;

import com.kerwhite.kmod.blockentity.BlockEntityEnergyTransporter;
import com.kerwhite.kmod.block.BlockEnergyTransporter;
import com.kerwhite.kmod.item.EnergyViewer;
import com.kerwhite.kmod.kmod;
import com.mojang.datafixers.DSL;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class register
{
    public static final DeferredRegister<Block> KBLOCKS= DeferredRegister.create(ForgeRegistries.BLOCKS, kmod.MODID);
    public static final DeferredRegister<Item> KITEMS= DeferredRegister.create(ForgeRegistries.ITEMS, kmod.MODID);
    public static final DeferredRegister<BlockEntityType<?>> KBLOCKENTITIES =DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, kmod.MODID);
    public static final RegistryObject<Item> EXAMPLEITEM = KITEMS.register("exampleitem", ()->new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Block> BLOCKENERGYTRANSPORTER = KBLOCKS.register("energy_transporter",()->{return new BlockEnergyTransporter();});
    public static final RegistryObject<Item> ENERGYVIEWERITEM = KITEMS.register("energy_viewer",()->{return new EnergyViewer();});
    public static final RegistryObject<BlockEntityType<BlockEntityEnergyTransporter>> ENERGYTRANSPORTER =
            KBLOCKENTITIES.register("energy_transporter_block_entity", () ->
                   BlockEntityType.Builder.of(BlockEntityEnergyTransporter::new,
                            BLOCKENERGYTRANSPORTER.get()).build(DSL.remainderType()));
    public static RegistryObject<Item> ENERGY_TRANSPORTER_ITEM = KITEMS.register("energy_transporter", () -> {
      return new BlockItem(BLOCKENERGYTRANSPORTER.get(), new Item.Properties().stacksTo(64).rarity(Rarity.UNCOMMON));
    });
    public static final DeferredRegister<CreativeModeTab>CREATIVE_MODE_TABS=DeferredRegister.create(Registries.CREATIVE_MODE_TAB, kmod.MODID);
    public register()
    {

    }
}
