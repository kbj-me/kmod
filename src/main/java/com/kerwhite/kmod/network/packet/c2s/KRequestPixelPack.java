package com.kerwhite.kmod.network.packet.c2s;

import com.kerwhite.kmod.corelib.screenshot.ScreenShotHelper;
import com.kerwhite.kmod.network.ModMessages;
import com.kerwhite.kmod.network.UniversalPacketWrapper;
import com.kerwhite.kmod.network.packet.KPacketBase;
import com.kerwhite.kmod.network.packet.s2c.KFeedBackPixelPack;
import com.kerwhite.kmod.only.server.ServerPixelCache;
import io.netty.buffer.Unpooled;
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
            ScreenShotHelper.KPixels pixels = ServerPixelCache.get().get(this.uuid);
            if(pixels!=null)
            {
                FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
                pixels.toBytes(buf);
                ModMessages.sendToPlayer(new KFeedBackPixelPack(buf),context.getSender());
            }
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
