package com.kerwhite.kmod.network.packet.c2s;

import com.kerwhite.kmod.corelib.screenshot.ScreenShotHelper;
import com.kerwhite.kmod.network.packet.KPacketBase;
import com.kerwhite.kmod.only.server.ServerPixelCache;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.UUID;
import java.util.function.Supplier;

public class KUpdatePixelPack extends KPacketBase
{
    ScreenShotHelper.KPixels kPixels = new ScreenShotHelper.KPixels(0,0,new ArrayList<>());
    UUID uuid;
    @Override
    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(()->
        {
            ServerPixelCache.get().put(uuid,kPixels);
        });
        context.setPacketHandled(true);
        return true;
    }
    @Override
    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeUUID(this.uuid);
        kPixels.toBytes(buf);
    }
    public KUpdatePixelPack()
    {

    }
    public KUpdatePixelPack(FriendlyByteBuf buf)
    {
        this.uuid = buf.readUUID();
        kPixels.fromBuf(buf);
    }
}
