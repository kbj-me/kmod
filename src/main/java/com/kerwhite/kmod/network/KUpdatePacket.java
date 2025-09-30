package com.kerwhite.kmod.network;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class KUpdatePacket
{
    String str;
    public KUpdatePacket()
    {

    }
    public KUpdatePacket(FriendlyByteBuf buf)
    {
        str = buf.readUtf();
    }
    public void toBytes(FriendlyByteBuf buf)
    {
        if(str!=null)
        {
            buf.writeUtf(str);
        }
        else
        {
            buf.writeUtf("");
        }
    }
    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(()->
        {
            ServerPlayer serverPlayer = context.getSender();
            ServerLevel serverLevel = context.getSender().serverLevel();
            serverPlayer.sendSystemMessage(Component.literal(str));
            //EntityType.COW.spawn(serverLevel, (CompoundTag) null,null,serverPlayer.blockPosition(), MobSpawnType.SPAWN_EGG,true,false);
        });
        context.setPacketHandled(true);
        return true;
    }
}
