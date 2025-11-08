package com.kerwhite.kmod.only.server;

import com.kerwhite.kmod.corelib.screenshot.ScreenShotHelper;

import java.util.HashMap;
import java.util.UUID;

public class ServerPixelCache
{
    private static final HashMap<UUID, ScreenShotHelper.KPixels> serverpixels = new HashMap<>();
    public static synchronized HashMap<UUID,ScreenShotHelper.KPixels> get()
    {
        return ServerPixelCache.serverpixels;
    }
}
