package com.kerwhite.kmod.worldsaveddata;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.List;

public class KWorldSavedData extends SavedData
{
    int PublicEnergy;
    List<CompoundTag> PrivateEnergy = new ArrayList<CompoundTag>();
    public void add(String name)
    {
        for (CompoundTag tag : PrivateEnergy)
        {
            if(tag.getString("playername").equals(name)){return;}
        }
        CompoundTag tag = new CompoundTag();
        tag.putInt("playerenergy",0);
        tag.putString("playername",name);
        PrivateEnergy.add(tag);
    }
    public int getE(String name)
    {
        for (CompoundTag tag : PrivateEnergy)
        {
            if(tag.getString("playername").equals(name)){return tag.getInt("playerenergy");}
        }
        return 0;
    }
    public void set(String string,int i)
    {
        for (CompoundTag tag : PrivateEnergy)
        {
            if(tag.getString("playername").equals(string))
            {
                tag.putInt("playerenergy",i);
            }
        }
        this.setDirty();
    }
    public void addE(String string,int i)
    {
        for (CompoundTag tag : PrivateEnergy)
        {
            if(tag.getString("playername").equals(string))
            {
                int i2=tag.getInt("playerenergy");
                tag.putInt("playerenergy",i+i2);
            }
        }
        this.setDirty();
    }
    public void addPublic (int i)
    {
        PublicEnergy+=i;
        this.setDirty();
    }
    public CompoundTag save(CompoundTag compoundTag)
    {
        CompoundTag tag = compoundTag;
        tag.putInt("PublicEnergy",PublicEnergy);
        ListTag listTag = new ListTag();
        for (Tag tag1 : PrivateEnergy)
        {
            listTag.add(tag1);
        }
        tag.put("list",listTag);
        return tag;
    }
    public static KWorldSavedData load(CompoundTag tag)
    {
        KWorldSavedData KWSD = new KWorldSavedData();
        KWSD.PublicEnergy=tag.getInt("PublicEnergy");
        Tag listraw=tag.get("list");
        ListTag list=null;
        if(listraw instanceof ListTag)
        {
            list=((ListTag) listraw);
        }
        for (Tag tag1 : list)
        {
            CompoundTag energy=((CompoundTag) tag1);
            KWSD.add(energy.getString("playername"));
            KWSD.set(energy.getString("playername"),energy.getInt("playerenergy"));
        }
        return KWSD;
    }
    public KWorldSavedData decode(CompoundTag tag)
    {
        KWorldSavedData modLevelSaveData = new KWorldSavedData();
        modLevelSaveData.load(tag);
        return modLevelSaveData;
    }
    public static KWorldSavedData get(Level l)
    {
        ServerLevel sl = l.getServer().getLevel(ServerLevel.OVERWORLD);
        DimensionDataStorage DDS = sl.getDataStorage();
        return DDS.computeIfAbsent(KWorldSavedData::load,KWorldSavedData::new,"KWSD");
    }
    public int getPublicEnergy()
    {
        return PublicEnergy;
    }
    public void setPublicEnergy(int publicEnergy)
    {
        PublicEnergy = publicEnergy;
        setDirty();
    }
}
