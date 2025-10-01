package com.kerwhite.kmod.network;

import com.kerwhite.kmod.screen.ConfigScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class KFeedBackPack
{
    int result;
    int pe;
    public KFeedBackPack()
    {

    }
    public KFeedBackPack(FriendlyByteBuf buf)
    {
        result = buf.readInt();
        pe = buf.readInt();
        //buf.read
        //buf.read
       // Player.
    }
    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeInt(result);
        buf.writeInt(pe);
    }
    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(()->
        {

            ((ConfigScreen)Minecraft.getInstance().screen).setPrivateenergy(Component.literal("PrivateEnergy:").append(String.valueOf(result)));
            ((ConfigScreen)Minecraft.getInstance().screen).setPublicenergy(Component.literal("PublicEnergy:").append(String.valueOf(pe)));


        });
        context.setPacketHandled(true);
        return true;
    }
}
