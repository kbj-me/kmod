package com.kerwhite.kmod.network;

import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;

public class UpdatePacketWrapper
{
    private KUpdatePacket kupdatepacket;
    private FriendlyByteBuf friendlyByteBuf;
    public UpdatePacketWrapper()
    {
        friendlyByteBuf = new FriendlyByteBuf(Unpooled.buffer());
    }
    public UpdatePacketWrapper(FriendlyByteBuf buf)
    {
        friendlyByteBuf = buf;
    }
    public void CreatePacket()
    {
        this.kupdatepacket = null;
        this.kupdatepacket = new KUpdatePacket(friendlyByteBuf);
        this.SendPacketToServer();
    }
    public static FriendlyByteBuf GetNewBuff()
    {
        return new FriendlyByteBuf(Unpooled.buffer());
    }
    public FriendlyByteBuf GetBuf()
    {
        return friendlyByteBuf;
    }
    public void SetBuf(FriendlyByteBuf buf)
    {
        this.friendlyByteBuf = buf;
    }
    public void SendPacketToServer()
    {
        ModMessages.sendToServer(kupdatepacket);
        this.Clean();
    }
    public void Clean()
    {
        if(this.friendlyByteBuf.refCnt()>0)
        {
            this.friendlyByteBuf.release();
        }

    }
}
