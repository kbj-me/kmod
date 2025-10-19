package com.kerwhite.kmod.event;

import com.kerwhite.kmod.blockentityrender.EnergyTransporterRender;
import com.kerwhite.kmod.kmod;
import com.kerwhite.kmod.register.register;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = kmod.MODID,bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class BlockEntityRenderersRegiter
{
    @SubscribeEvent
    public static void onClientEvent(FMLClientSetupEvent event){
        event.enqueueWork(()->{
            BlockEntityRenderers.register(register.ENERGYTRANSPORTER.get(), EnergyTransporterRender::new);
        });
    }

}
