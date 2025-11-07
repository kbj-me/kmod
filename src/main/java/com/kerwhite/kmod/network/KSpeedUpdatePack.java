package com.kerwhite.kmod.network;

import com.kerwhite.kmod.network.packet.KPacketBase;
import com.kerwhite.kmod.utils.PlayerSpeed;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class KSpeedUpdatePack extends KPacketBase
{
    String name="";
    double speed=0.0;
    public KSpeedUpdatePack()
    {

    }
    public KSpeedUpdatePack(FriendlyByteBuf buf)
    {
        this.name = buf.readUtf();
        this.speed = buf.readDouble();
    }
    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeUtf(this.name);
        buf.writeDouble(this.speed);
    }
    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {

        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(()->
        {
            ServerPlayer serverPlayer = context.getSender();
            //serverPlayer.sendSystemMessage(Component.literal(this.name));
            //serverPlayer.sendSystemMessage(Component.literal(String.valueOf(this.speed)));
            PlayerSpeed.playerspeed.put(this.name,this.speed);
        });
        context.setPacketHandled(true);
        return true;
    }
}