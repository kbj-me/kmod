package com.kerwhite.kmod.network;

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
    public KFeedBackPack()
    {

    }
    public KFeedBackPack(FriendlyByteBuf buf)
    {
        result = buf.readInt();
        //buf.read
        //buf.read
       // Player.
    }
    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeInt(result);
    }
    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(()->
        {
            Minecraft.getInstance().player.sendSystemMessage(Component.literal(String.valueOf(result)));
           // ModMessages.sendToPlayer(new KFeedBackPack(),);
        });
        context.setPacketHandled(true);
        return true;
    }
}
