package com.kerwhite.kmod;

import com.kerwhite.kmod.creativemodetab.KCreativeModeTabItems;
import com.kerwhite.kmod.register.EnchantmentsRegister;
import com.kerwhite.kmod.register.ItemRegister;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import com.kerwhite.kmod.creativemodetab.KCreativeModeTab;

import java.util.logging.Logger;

import static com.kerwhite.kmod.register.register.*;

@Mod(kmod.MODID)
public class kmod
{
    public final static String MODID="kmod";
    public static final Logger LOGGER = Logger.getLogger("com.kerwhite.kmod");
    @SuppressWarnings("all")
    public kmod()
    {
        KBLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        KITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        KBLOCKENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
        KCreativeModeTab.register(FMLJavaModLoadingContext.get().getModEventBus());
        ItemRegister.RegisterBus(FMLJavaModLoadingContext.get().getModEventBus());
        EnchantmentsRegister.RegisterBus(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
