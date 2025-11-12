package com.kerwhite.kmod.only.server;

import com.kerwhite.kmod.corelib.screenshot.ScreenShotHelper;

import java.util.HashMap;
import java.util.UUID;
import java.util.function.Consumer;

@SuppressWarnings("ALL")
public class ServerPixelCache
{
    public static synchronized void getPixels(Consumer<ScreenShotHelper.KPixels> consumer,UUID uuid)
    {
        Thread thread = new Thread(()->
        {
            ScreenShotHelper.KPixels pixels = ServerPixelCache.getMap().get(uuid);
            if(pixels != null)
            {
                consumer.accept(pixels);
            }
        },"GetPixelsThread");
        thread.start();
    }
    private static synchronized HashMap<UUID, ScreenShotHelper.KPixels> getMap()
    {
        return ServerPixelCache.serverpixels;
    }
    public static synchronized void setPixels(UUID uuid, ScreenShotHelper.KPixels pixels)
    {
        Thread thread = new Thread(()->
        {
            ServerPixelCache.getMap().put(uuid, pixels);
        },"SetPixelsThread");
        thread.start();
    }
    private static final HashMap<UUID, ScreenShotHelper.KPixels> serverpixels = new HashMap<>();
    @Deprecated
    public static synchronized HashMap<UUID,ScreenShotHelper.KPixels> get()
    {
        return ServerPixelCache.serverpixels;
    }
}
