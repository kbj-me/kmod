package com.kerwhite.kmod.datagen;

import com.kerwhite.kmod.kmod;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import com.kerwhite.kmod.regiter.register;
public class ModItemModelGen extends ItemModelProvider {
    //通用模型，让其他物品继承使用
    public static final String GENERATED="item/handheld";

    //构造函数
    public ModItemModelGen(PackOutput output, ExistingFileHelper existingFileHelper){
        //第二个参数要填自己的modid
        super(output, kmod.MODID,existingFileHelper);
    }

    //重写该方法，在这个方法里面填写要生成数据的物品
    @Override
    @SuppressWarnings("all")
    protected void registerModels() {
        //在里面调用自定义的生成模型的方法，传入对应参数即可
       // itemGenerateModel(register.ENERGYVIEWERITEM.get(),resourceItem(itemName(register.ENERGYVIEWERITEM.get())));
        //itemGenerateModel(ModItems.JIUCHANZHIYUAN.get(),resourceItem(itemName(ModItems.JIUCHANZHIYUAN.get())));
        //itemGenerateModel(ModItems.REDSWORD.get(),resourceItem(itemName(ModItems.REDSWORD.get())));
        //itemGenerateModel(ModItems.EIGHTBALL.get(),resourceItem(itemName(ModItems.EIGHTBALL.get())));
    }

    //自定义方法，item表明我们要生成数据的物品，texture表明生成数据的贴图资源
    public void itemGenerateModel(Item item, ResourceLocation texture){
        //因为要继承通用模型，调用这个方法设置父模型为通用模型，返回这个类的对象调用texture方法设置贴图
        withExistingParent(itemName(item),GENERATED).texture("layer0",texture);
    }

    //获取item的所在路径
    public String itemName(Item item){
        return ForgeRegistries.ITEMS.getKey(item).getPath();
    }

    //拼接路径寻找贴图资源
    @SuppressWarnings("all")
    public ResourceLocation resourceItem(String path){
        return new ResourceLocation(kmod.MODID,"item/"+path);
    }
}