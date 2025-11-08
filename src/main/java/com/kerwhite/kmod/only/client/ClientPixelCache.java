package com.kerwhite.kmod.only.client;

import com.kerwhite.kmod.corelib.screenshot.ScreenShotHelper;

import java.util.ArrayList;

public class ClientPixelCache
{
    private static ScreenShotHelper.KPixels pixels = new ScreenShotHelper.KPixels(0,0,new ArrayList<>());
    public static synchronized ScreenShotHelper.KPixels get()
    {
        return ClientPixelCache.pixels;
    }
    public static  synchronized void set(ScreenShotHelper.KPixels pixels)
    {
        ClientPixelCache.pixels = pixels;
    }
}
