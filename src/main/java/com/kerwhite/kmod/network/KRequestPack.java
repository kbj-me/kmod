package com.kerwhite.kmod.network;

import com.kerwhite.kmod.blockentity.BlockEntityEnergyTransporter;
import com.kerwhite.kmod.worldsaveddata.KWorldSavedData;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class KRequestPack
{

    public KRequestPack()
    {

    }
    public KRequestPack(FriendlyByteBuf buf)
    {
    }
    public void toBytes(FriendlyByteBuf buf)
    {
    }
    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(()->
        {
            KWorldSavedData KWSD = KWorldSavedData.get(context.getSender().level());
            int res = KWSD.getE(context.getSender().getName().toString());
            FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
            buf.writeInt(res);
            ModMessages.sendToPlayer(new KFeedBackPack(buf),context.getSender());
            //context.getSender().sendSystemMessage(Component.literal("bbbbbb"));
        });
        context.setPacketHandled(true);
        return true;
    }
}
