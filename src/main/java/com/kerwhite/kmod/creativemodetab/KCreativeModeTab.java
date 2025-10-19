package com.kerwhite.kmod.creativemodetab;

import com.kerwhite.kmod.kmod;
import com.kerwhite.kmod.register.ItemRegister;
import com.kerwhite.kmod.register.register;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

//这个类用来将物品放到创造模式物品栏里
public class KCreativeModeTab {
    public static final String KMOD_TAB_STRING="creativetab.kcreativemodetab";
    //获取注册表
    private static final List<Item> items = new ArrayList<>();
    public static void addItem(RegistryObject<Item> item)
    {
        KCreativeModeTab.items.add(item.get());
    }
    public static final DeferredRegister<CreativeModeTab>CREATIVE_MODE_TABS=
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, kmod.MODID);
    public static final RegistryObject<CreativeModeTab>TEST1_TAB=CREATIVE_MODE_TABS.register("kcreativemodetab",
            ()-> CreativeModeTab.builder()
                    .icon(()->new ItemStack(register.ENERGYVIEWERITEM.get()))//设置mod物品栏的图标
                    .title(Component.translatable(KMOD_TAB_STRING))//设置mod物品栏的标题

                    .displayItems((pParameters, pOutput) ->
                    {
                        for (Item i : items)
                        {
                            pOutput.accept(i);
                        }
//                        pOutput.accept(register.ENERGY_TRANSPORTER_ITEM.get()); //用get方法获取对应的参数传进去
//                        pOutput.accept(register.ENERGYVIEWERITEM.get());
//                        pOutput.accept(ItemRegister.ENERGYVIEWERITEM.get());
//                        pOutput.accept(ItemRegister.WOODEN_SPEAR.get());
//                        pOutput.accept(ItemRegister.STONE_SPEAR.get());
//                        pOutput.accept(ItemRegister.IRON_SPEAR.get());
//                        pOutput.accept(ItemRegister.GOLDEN_SPEAR.get());
//                        pOutput.accept(ItemRegister.DIAMOND_SPEAR.get());
//                        pOutput.accept(ItemRegister.NETHERITE_SPEAR.get());
                    })
                    .build());//构建完成
    //对外接口方法，让主类注册用
    public static final void register(IEventBus eventBus)
    {
        CREATIVE_MODE_TABS.register(eventBus);
    }

}