package com.kerwhite.kmod.network;

import com.kerwhite.kmod.blockentity.BlockEntityEnergyTransporter;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class KUpdatePacket
{
    public int maxIO=50;
    public String bindPlayer="";
    public boolean isPlayerMode=false;
    public boolean isOut=false;
    BlockPos pos;
    public KUpdatePacket()
    {

    }
    public KUpdatePacket(FriendlyByteBuf buf)
    {
        this.maxIO=buf.readInt();
        this.bindPlayer= buf.readUtf();
        this.isPlayerMode=buf.readBoolean();
        this.isOut=buf.readBoolean();
        this.pos=buf.readBlockPos();
       // str = buf.readUtf();
    }
    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeInt(this.maxIO);
        buf.writeUtf(this.bindPlayer);
        buf.writeBoolean(this.isPlayerMode);
        buf.writeBoolean(this.isOut);
        buf.writeBlockPos(this.pos);
       // if(str!=null)
        //{
        //    buf.writeUtf(str);
       // }
        //else
        //{
        //    buf.writeUtf("");
        //}
    }
    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(()->
        {
            ServerPlayer serverPlayer = context.getSender();
            ServerLevel serverLevel = context.getSender().serverLevel();
            BlockEntity be = serverLevel.getBlockEntity(this.pos);
            if(be instanceof BlockEntityEnergyTransporter)
            {
                ((BlockEntityEnergyTransporter)be).setMaxIO(this.maxIO);
                ((BlockEntityEnergyTransporter)be).setBindPlayer(this.bindPlayer);
                ((BlockEntityEnergyTransporter)be).setPlayerMode(this.isPlayerMode);
                ((BlockEntityEnergyTransporter)be).setisOut(this.isOut);
            }
            //serverPlayer.sendSystemMessage(Component.literal(str));
            //ModMessages.sendToPlayer(new KUpdateFeedBack(),context.getSender());
            //EntityType.COW.spawn(serverLevel, (CompoundTag) null,null,serverPlayer.blockPosition(), MobSpawnType.SPAWN_EGG,true,false);
        });
        context.setPacketHandled(true);
        return true;
    }
}
