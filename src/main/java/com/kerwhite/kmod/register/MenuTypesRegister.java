package com.kerwhite.kmod.register;

import com.kerwhite.kmod.kmod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
@SuppressWarnings("All")
public class MenuTypesRegister
{
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(Registries.MENU, kmod.MODID);


}
