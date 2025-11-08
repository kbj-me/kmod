package com.kerwhite.kmod.network.packet.s2c;

import com.kerwhite.kmod.corelib.screenshot.ScreenShotHelper;
import com.kerwhite.kmod.network.packet.KPacketBase;
import com.kerwhite.kmod.only.client.ClientPixelCache;
import com.kerwhite.kmod.screen.GuiOpenWrapper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public class KFeedBackPixelPack extends KPacketBase
{
    ScreenShotHelper.KPixels pixels = new ScreenShotHelper.KPixels(0,0,new ArrayList<>());
    @Override
    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(()->
        {
            ClientPixelCache.set(this.pixels);
        });
        context.setPacketHandled(true);
        return true;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf)
    {
        this.pixels.toBytes(buf);
    }
    public KFeedBackPixelPack()
    {

    }
    public KFeedBackPixelPack(FriendlyByteBuf buf)
    {
        this.pixels.fromBuf(buf);
    }
}
