package com.kerwhite.kmod.event;

import com.kerwhite.kmod.blockentityrender.EnergyTransporterRender;
import com.kerwhite.kmod.kmod;
import com.kerwhite.kmod.regiter.register;
import com.kerwhite.kmod.worldsaveddata.KWorldSavedData;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = kmod.MODID)
public class PlayerLoggedInEventHandler
{
    @SubscribeEvent
    public static void onPlayer(PlayerEvent.PlayerLoggedInEvent event)
    {
        ServerLevel sl = event.getEntity().level().getServer().getLevel(ServerLevel.OVERWORLD);
        DimensionDataStorage DDS = sl.getDataStorage();
        KWorldSavedData KWSD = DDS.computeIfAbsent(KWorldSavedData::load,KWorldSavedData::new,"KWSD");
        KWSD.add(event.getEntity().getName().toString());
        KWSD.addE(event.getEntity().getName().toString(),5);
        event.getEntity().sendSystemMessage(Component.literal(Integer.toString(KWSD.getE(event.getEntity().getName().toString()))));
        event.getEntity().sendSystemMessage(Component.literal(event.getEntity().getName().toString()));
        KWSD.setDirty();
    }

}
