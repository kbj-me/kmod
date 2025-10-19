package com.kerwhite.kmod.event;

import com.kerwhite.kmod.kmod;
import com.kerwhite.kmod.worldsaveddata.KWorldSavedData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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
        KWSD.setDirty();
    }

}
