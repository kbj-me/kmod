package com.kerwhite.kmod.network;

import com.kerwhite.kmod.KmodRuntimeException;
import com.kerwhite.kmod.network.packet.KPacketBase;
import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.phys.BlockHitResult;

import java.awt.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.BitSet;
import java.util.Date;
import java.util.UUID;
import java.util.function.Consumer;

@SuppressWarnings("ALL")
public class UniversalPacketWrapper<T extends KPacketBase>
{
    public interface KCustomBufferUser
    {
        public void run(FriendlyByteBuf buf);
    }
    private Class<T> tClass = null;
    private T packet = null;
    private FriendlyByteBuf buf = null;
    public UniversalPacketWrapper(Class<T> cls)
    {
        this.tClass = cls;
        this.buf = new FriendlyByteBuf(Unpooled.buffer());
    }
    public static UniversalPacketWrapper newInstance(Class cls)
    {
        return new UniversalPacketWrapper<>(cls);
    }
    public T build()
    {
        Constructor<T> constructor = null;
        try
        {
            constructor = this.tClass.getConstructor(FriendlyByteBuf.class);
        }
        catch (NoSuchMethodException e)
        {
            throw new KmodRuntimeException(e);
        }
        T packet = null;
        try
        {
            packet = constructor.newInstance(this.buf);
        }
        catch (InstantiationException e)
        {
            throw new KmodRuntimeException(e);
        }
        catch (IllegalAccessException e)
        {
            throw new KmodRuntimeException(e);
        }
        catch (InvocationTargetException e)
        {
            throw new KmodRuntimeException(e);
        }
        this.packet = packet;
        return this.packet;
    }
    public UniversalPacketWrapper writeInt(int i)
    {
        this.buf.writeInt(i);
        return this;
    }
    public UniversalPacketWrapper writeUtf(String i)
    {
        this.buf.writeUtf(i);
        return this;
    }
    public UniversalPacketWrapper writeBoolean(Boolean i)
    {
        this.buf.writeBoolean(i);
        return this;
    }
    public UniversalPacketWrapper writeBlockPos(BlockPos i)
    {
        this.buf.writeBlockPos(i);
        return this;
    }
    public UniversalPacketWrapper writeUUID(UUID i)
    {
        this.buf.writeUUID(i);
        return this;
    }
    public UniversalPacketWrapper writeDouble(double i)
    {
        this.buf.writeDouble(i);
        return this;
    }
    public UniversalPacketWrapper writeDate(Date i)
    {
        this.buf.writeDate(i);
        return this;
    }
    public UniversalPacketWrapper writeBitSet(BitSet i)
    {
        this.buf.writeBitSet(i);
        return this;
    }
    public UniversalPacketWrapper writeBlockHitResult(BlockHitResult i)
    {
        this.buf.writeBlockHitResult(i);
        return this;
    }
    public UniversalPacketWrapper writeBlockHitResult(Component i)
    {
        this.buf.writeComponent(i);
        return this;
    }
    public UniversalPacketWrapper writeFloat(Float i)
    {
        this.buf.writeFloat(i);
        return this;
    }
    public UniversalPacketWrapper writeFloat(ChunkPos i)
    {
        this.buf.writeChunkPos(i);
        return this;
    }
    public UniversalPacketWrapper writeNbt(CompoundTag i)
    {
        this.buf.writeNbt(i);
        return this;
    }
    public UniversalPacketWrapper writeLong(long i)
    {
        this.buf.writeLong(i);
        return this;
    }
    public UniversalPacketWrapper writeCustom(KCustomBufferUser user)
    {
        user.run(this.buf);
        return this;
    }
    public UniversalPacketWrapper async_writeCustom(Consumer<FriendlyByteBuf> consumer,Consumer<UniversalPacketWrapper<T>> consumer2)
    {
        Thread thread = new Thread(()->
        {
            consumer.accept(this.buf);
            consumer2.accept(this);
        });
        thread.start();
        return this;
    }
    public void async_build(Consumer<T> consumer)
    {
        Constructor<T> constructor = null;
        try
        {
            constructor = this.tClass.getConstructor(FriendlyByteBuf.class);
        }
        catch (NoSuchMethodException e)
        {
            throw new KmodRuntimeException(e);
        }
        T packet = null;
        try
        {
            packet = constructor.newInstance(this.buf);
        }
        catch (InstantiationException e)
        {
            throw new KmodRuntimeException(e);
        }
        catch (IllegalAccessException e)
        {
            throw new KmodRuntimeException(e);
        }
        catch (InvocationTargetException e)
        {
            throw new KmodRuntimeException(e);
        }
        this.packet = packet;
        consumer.accept(this.packet);
    }
}
