package com.kerwhite.kmod;

import com.kerwhite.kmod.creativemodetab.KCreativeModeTab;
import com.kerwhite.kmod.register.EnchantmentsRegister;
import com.kerwhite.kmod.register.ItemRegister;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.logging.Logger;

import static com.kerwhite.kmod.register.register.*;

@Mod(kmod.MODID)
public class kmod
{
    public volatile static boolean ENABLE_SKIN_OVERRIDE = false;
    public volatile static boolean DISABLE_OTHER_MOD_SKIN_OVERRIDE = false;
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
