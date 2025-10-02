package com.kerwhite.kmod.blockentity;

import com.google.common.collect.Queues;
import com.kerwhite.kmod.kmod;
import com.kerwhite.kmod.worldsaveddata.KWorldSavedData;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.arguments.ParticleArgument;
import net.minecraft.core.BlockPos;

import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

import static com.kerwhite.kmod.regiter.register.ENERGYTRANSPORTER;

public class BlockEntityEnergyTransporter extends BlockEntity
{
    public int energy=0;
    public int maxIO=50;
    public String bindPlayer="literal{MCLR_747}";
    public boolean isPlayerMode=false;
    public boolean isOut=false;
    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket()
    {
        super.getUpdatePacket();
        return ClientboundBlockEntityDataPacket.create(this);
    }
    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt)
    {
        super.onDataPacket(net,pkt);
        if (pkt.getTag() != null)
        {
            handleUpdateTag(pkt.getTag());
        }
    }
    @Override
    public void handleUpdateTag(CompoundTag tag)
    {
        this.energy = tag.getInt("KEnergy");
        this.maxIO = tag.getInt("MaxIO");
        this.isPlayerMode = tag.getBoolean("IsPlayerMode");
        this.isOut = tag.getBoolean("IsOut");
        this.bindPlayer = tag.getString("BindPlayer");
        super.handleUpdateTag(tag);
    }
    @Override
    public CompoundTag getUpdateTag()
    {
        CompoundTag compoundNBT = super.getUpdateTag();
        compoundNBT.putInt("KEnergy", this.energy);
        compoundNBT.putInt("MaxIO",this.maxIO);
        compoundNBT.putString("BindPlayer",this.bindPlayer);
        compoundNBT.putBoolean("IsOut",this.isOut);
        compoundNBT.putBoolean("IsPlayerMode",this.isPlayerMode);
        super.getUpdateTag();
        return compoundNBT;
    }
   // @Override
 //   public Component getDisplayName()
  //  {
   //     return Component.translatable("gui."+ kmod.MODID+".first_menu_gui");
   // }
   // @Nullable
   // @Override
   // public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer)
  //  {
        //return new FirstMenu(pContainerId,pPlayerInventory,this,this.data);
    //}
    private LazyOptional<IEnergyStorage> IN_lazyOptional=LazyOptional.of(()->new IEnergyStorage()
    {
        @Override
        public int receiveEnergy(int maxReceive, boolean simulate)
        {
            if (!simulate)
            {
                BlockEntityEnergyTransporter.this.energy += maxReceive;
                if (maxReceive != 0)
                {
                    BlockEntityEnergyTransporter.this.setChanged();
                }
            }
            return maxReceive;
        }
        @Override
        public int extractEnergy(int maxExtract, boolean simulate)
        {
            return 0;
        }
        @Override
        public int getEnergyStored()
        {
            return BlockEntityEnergyTransporter.this.energy;
        }
        @Override
        public int getMaxEnergyStored()
        {
            return 2147483647;
        }
        @Override
        public boolean canExtract()
        {
            return false;
        }
        @Override
        public boolean canReceive()
        {
            return true;
        }
    });


    private final Queue<Direction> directionQueue = Queues.newArrayDeque(Direction.Plane.HORIZONTAL);

    private void transferEnergy(@Nonnull Level world)
    {
        this.directionQueue.offer(this.directionQueue.remove());
        for (Direction direction : this.directionQueue)
        {
            BlockEntity tileEntity = world.getBlockEntity(this.getBlockPos().relative(direction));
            if (tileEntity != null)
            {
                tileEntity.getCapability(ForgeCapabilities.ENERGY, direction.getOpposite()).ifPresent(e ->
                {
                    if (e.canReceive())
                    {
                        int diff = e.receiveEnergy(Math.min(500, this.energy), false);
                        if (diff != 0)
                        {
                            this.energy -= diff;
                            this.setChanged();
                        }
                    }
                });
            }
        }
    }









    private LazyOptional<IEnergyStorage> OUT_lazyOptional=LazyOptional.of(()->new IEnergyStorage()
    {
        @Override
        public int receiveEnergy(int maxReceive, boolean simulate){return 0;}
        @Override
        public int extractEnergy(int maxExtract, boolean simulate)
        {
            if(!simulate)
            {
                if(BlockEntityEnergyTransporter.this.energy-maxExtract>=0)
                {
                    BlockEntityEnergyTransporter.this.energy -=maxExtract;
                    BlockEntityEnergyTransporter.this.setChanged();
                    return maxExtract;
                }
            }
            return 0;
        }
        @Override
        public int getEnergyStored()
        {
            return BlockEntityEnergyTransporter.this.energy;
        }
        @Override
        public int getMaxEnergyStored()
        {
            return 2147483647;
        }
        @Override
        public boolean canExtract()
        {
            return true;
        }
        @Override
        public boolean canReceive()
        {
            return false;
        }
    });
    public void setBindPlayer(String bindPlayer)
    {
        this.bindPlayer = bindPlayer;
        this.setChanged();
    }

    public void setPlayerMode(boolean playerMode)
    {
        this.isPlayerMode = playerMode;
        this.setChanged();
    }

    public void setisOut(boolean out)
    {
        this.isOut = out;
        this.setChanged();
    }
    public void setMaxIO(int maxIO)
    {
        this.maxIO = maxIO;
        this.setChanged();
    }
    public void setEnergy(int energy)
    {
        this.energy = energy;
        this.setChanged();
    }
    @Override
    public void load(CompoundTag compoundTag)
    {
        this.energy = compoundTag.getInt("KEnergy");
        this.isPlayerMode = compoundTag.getBoolean("IsPlayerMode");
        this.maxIO = compoundTag.getInt("MaxIO");
        this.isOut = compoundTag.getBoolean("IsOut");
        this.bindPlayer = compoundTag.getString("BindPlayer");
        super.load(compoundTag);
    }
    @Override
    protected void saveAdditional(CompoundTag compoundTag)
    {
        compoundTag.putInt("MaxIO",this.maxIO);
        compoundTag.putString("BindPlayer",this.bindPlayer);
        compoundTag.putBoolean("IsOut",this.isOut);
        compoundTag.putInt("KEnergy", this.energy);
        compoundTag.putBoolean("IsPlayerMode",this.isPlayerMode);
        super.saveAdditional(compoundTag);
    }
    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, Direction side)
    {
        //boolean isEnergy =  && side.getAxis().isHorizontal();
        if(this.isOut)
        {
            return Objects.equals(cap, ForgeCapabilities.ENERGY) ? this.OUT_lazyOptional.cast() : super.getCapability(cap, side);
        }
        else
        {
            return Objects.equals(cap, ForgeCapabilities.ENERGY) ? this.IN_lazyOptional.cast() : super.getCapability(cap, side);
        }
    }
    public BlockEntityEnergyTransporter(BlockEntityType<?>type, BlockPos pos, BlockState state)
    {
        super(type,pos,state);
    }
    public BlockEntityEnergyTransporter(BlockPos worldPosition, BlockState blockState)
    {
        this(ENERGYTRANSPORTER.get(), worldPosition, blockState);
    }
    public static void tick(Level Level, BlockPos Pos, BlockState State, BlockEntityEnergyTransporter BlockEntity)
    {
        if(Level!=null && !Level.isClientSide)
        {
            Level.sendBlockUpdated(Pos,Level.getBlockState(Pos),Level.getBlockState(Pos),3);
            KWorldSavedData KWSD = KWorldSavedData.get(Level);
            if(!BlockEntity.isOut)
            {
                if(BlockEntity.energy>=BlockEntity.maxIO)
                {
                    BlockEntity.energy-=BlockEntity.maxIO;
                    if(BlockEntity.isPlayerMode==true)
                    {
                        KWSD.addE(BlockEntity.bindPlayer, BlockEntity.maxIO);
                    }
                    else if(BlockEntity.isPlayerMode==false)
                    {
                        KWSD.addPublic(BlockEntity.maxIO);
                    }
                }
            }
            else if(BlockEntity.isOut)
            {
                if(BlockEntity.isPlayerMode==true)
                {
                    if(KWSD.getE(BlockEntity.bindPlayer)>=BlockEntity.maxIO)
                    {
                        BlockEntity.energy+= BlockEntity.maxIO;
                        KWSD.addE(BlockEntity.bindPlayer,-BlockEntity.maxIO);
                    }
                }
                else if (!BlockEntity.isPlayerMode)
                {
                    if(KWSD.getPublicEnergy()>= BlockEntity.maxIO)
                    {
                        BlockEntity.energy+= BlockEntity.maxIO;
                        KWSD.addPublic(-BlockEntity.maxIO);
                    }
                }
                BlockEntity.transferEnergy(Level);
            }
        }
        if (!Level.isClientSide)
        {
            ServerLevel serverLevel=((ServerLevel)Level);
            serverLevel.sendParticles(ParticleTypes.DRAGON_BREATH,Pos.getX(),Pos.getY(),Pos.getZ(),4,0.5,0.5,0.5,0.02);


        }
    }
}
