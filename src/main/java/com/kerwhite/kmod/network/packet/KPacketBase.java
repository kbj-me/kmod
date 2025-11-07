package com.kerwhite.kmod.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public abstract class KPacketBase
{
    public abstract boolean handle(Supplier<NetworkEvent.Context> supplier);
    public abstract void toBytes(FriendlyByteBuf buf);
}
