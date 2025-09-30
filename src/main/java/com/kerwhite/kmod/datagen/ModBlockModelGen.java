package com.kerwhite.kmod.datagen;

import com.kerwhite.kmod.kmod;
import com.kerwhite.kmod.regiter.register;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockModelGen extends BlockStateProvider {

    public ModBlockModelGen(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, kmod.MODID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
       // this.registerBlockModelAndItem(register.BLOCKENERGYTRANSPORTER.get());
        //this.registerBlockModelAndItem(ModBlocks.BLUE_ORE.get());
        //this.registerBlockModelAndItem(ModBlocks.JUMP_BLOCK.get());
    }

    //在registerStatesAndModels方法里调用该函数直接添加对应的方块即可
    public void registerBlockModelAndItem(Block block)
    {
        this.simpleBlockWithItem(block, this.cubeAll(block));
    }
}