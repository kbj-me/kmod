package com.kerwhite.kmod.network.packet.c2s;

import com.kerwhite.kmod.network.ModMessages;
import com.kerwhite.kmod.network.UniversalPacketWrapper;
import com.kerwhite.kmod.network.packet.KPacketBase;
import com.kerwhite.kmod.network.packet.s2c.KFeedBackPixelPack;
import com.kerwhite.kmod.only.server.ServerPixelCache;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class KRequestPixelPack extends KPacketBase
{
    UUID uuid;
    @Override
    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(()->
        {
            ServerPixelCache.getPixels((pixels)->
            {
                if(pixels!=null)
                {
                    UniversalPacketWrapper.newInstance(KFeedBackPixelPack.class).async_writeCustom(pixels::toBytes,(wrapper)-> ModMessages.sendToPlayer(wrapper.build(),context.getSender()));
                }
            },this.uuid);
        });
        return true;
    }
    @Override
    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeUUID(this.uuid);
    }
    public KRequestPixelPack()
    {

    }
    public KRequestPixelPack(FriendlyByteBuf buf)
    {
        this.uuid = buf.readUUID();
    }
}
