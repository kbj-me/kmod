package com.kerwhite.kmod.network;

import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;

public class KUpdatePacketWrapper
{
    private KUpdatePacket kupdatepacket=null;
    private FriendlyByteBuf friendlyByteBuf;
    public KUpdatePacketWrapper()
    {
        this.friendlyByteBuf = new FriendlyByteBuf(Unpooled.buffer());
    }
    public KUpdatePacket build()
    {
        this.kupdatepacket = new KUpdatePacket(friendlyByteBuf);
        return this.kupdatepacket;
    }
    public KUpdatePacketWrapper writeInt(int i)
    {
        this.friendlyByteBuf.writeInt(i);
        return this;
    }
    public KUpdatePacketWrapper writeUtf(String i)
    {
        this.friendlyByteBuf.writeUtf(i);
        return this;
    }
    public KUpdatePacketWrapper writeBoolean(Boolean i)
    {
        this.friendlyByteBuf.writeBoolean(i);
        return this;
    }
    public KUpdatePacketWrapper writeBlockPos(BlockPos i)
    {
        this.friendlyByteBuf.writeBlockPos(i);
        return this;
    }

}
