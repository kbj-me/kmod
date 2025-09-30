package com.kerwhite.kmod.creativemodetab;

import com.kerwhite.kmod.kmod;
import com.kerwhite.kmod.regiter.ItemRegister;
import com.kerwhite.kmod.regiter.register;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

//这个类用来将物品放到创造模式物品栏里
public class KCreativeModeTab {
    public static final String TEST1_TAB_STRING="creativetab.kcreativemodetab";

    //获取注册表
    public static final DeferredRegister<CreativeModeTab>CREATIVE_MODE_TABS=
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, kmod.MODID);
    public static final RegistryObject<CreativeModeTab>TEST1_TAB=CREATIVE_MODE_TABS.register("kcreativemodetab",
            ()-> CreativeModeTab.builder()
                    .icon(()->new ItemStack(register.ENERGYVIEWERITEM.get()))//设置mod物品栏的图标
                    .title(Component.translatable(TEST1_TAB_STRING))//设置mod物品栏的标题
                    //开始向物品栏里添加我们新增的物品
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(register.ENERGY_TRANSPORTER_ITEM.get()); //用get方法获取对应的参数传进去
                        pOutput.accept(register.ENERGYVIEWERITEM.get());
                        pOutput.accept(ItemRegister.ENERGYVIEWERITEM.get());
                    })
                    .build());//构建完成
    //对外接口方法，让主类注册用
    public static final void register(IEventBus eventBus)
    {
        CREATIVE_MODE_TABS.register(eventBus);
    }

}