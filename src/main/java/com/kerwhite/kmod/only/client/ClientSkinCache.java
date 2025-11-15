package com.kerwhite.kmod.only.client;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class ClientSkinCache
{
    private static final HashMap<UUID, ResourceLocation> skins = new HashMap<>();
    public synchronized static void requestSkin(UUID uuid,Consumer<ResourceLocation> consumer)
    {
        if(ClientSkinCache.getSkins().get(uuid)==null)
        {
            Thread thread = new Thread(()->
            {
                GameProfile profile = new GameProfile(uuid,"");
                MinecraftSessionService sessionService = Minecraft.getInstance().getMinecraftSessionService();
                sessionService.fillProfileProperties(profile,true);
                AtomicReference<ResourceLocation> resourceLocation = new AtomicReference<>();
                Minecraft.getInstance().getSkinManager().registerSkins(profile,(type,location,texture)->
                {
                    if(type == MinecraftProfileTexture.Type.SKIN)
                    {
                        resourceLocation.set(location);
                        consumer.accept(location);
                    }
                },true);
            });
            thread.start();
        }
        else
        {
            consumer.accept(ClientSkinCache.getSkins().get(uuid));
        }
    }
    public static synchronized HashMap<UUID,ResourceLocation> getSkins()
    {
        return ClientSkinCache.skins;
    }
}
