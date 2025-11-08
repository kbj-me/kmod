package com.kerwhite.kmod.corelib.screenshot;

import com.kerwhite.kmod.network.UniversalPacketWrapper;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
@SuppressWarnings("ALL")
public class ScreenShotHelper
{
    public static class KPixels
    {
        private List<Integer> pixels = new ArrayList<>();
        private int height = 0 ;
        private int width = 0;
        public KPixels(int height,int width,List<Integer> pixels)
        {
            this.pixels = pixels;
            this.height = height;
            this.width = width;
        }
        public List<Integer> getPixels()
        {
            return pixels;
        }
        public int getWidth()
        {
            return width;
        }
        public int getHeight()
        {
            return height;
        }
        /*
            Param:
            1.int height
            2.int width
            3.height*width pixels
        */
        public void toBytes(FriendlyByteBuf buf)
        {
            buf.writeInt(this.height);
            buf.writeInt(this.width);
            for (int y = 0; y < height; y++)
            {
                for (int x = 0; x < width; x++)
                {
                    buf.writeInt(this.pixels.get(y * width + x));
                }
            }
        }
        public void fromBuf(FriendlyByteBuf buf)
        {
            int h = buf.readInt();
            int w = buf.readInt();
            this.height = h;
            this.width = w;
            //System.out.println(height*width);
            if(this.pixels!=null){this.pixels.clear();}
            else{this.pixels = new ArrayList<>();}
            int t = 0;
            for (int y = 0; y < h; y++)
            {
                for (int x = 0; x < w; x++)
                {
                    t = buf.readInt();
                    this.pixels.add(t);
                }
            }
        }
    }
    private static final RenderTarget MAIN_RENDER_TARGET = Minecraft.getInstance().getMainRenderTarget();
    private static NativeImage nativeImage;
    private static int height;
    private static int width;
    public static void getPixels(Consumer<KPixels> consumer)
    {
        ScreenShotHelper.height = MAIN_RENDER_TARGET.height;
        ScreenShotHelper.width = MAIN_RENDER_TARGET.width;
        RenderSystem.recordRenderCall(() ->
        {
            try
            {
                ScreenShotHelper.nativeImage = new NativeImage(ScreenShotHelper.width,ScreenShotHelper.height,false);
                RenderSystem.bindTexture(MAIN_RENDER_TARGET.getColorTextureId());
                if(MAIN_RENDER_TARGET.getColorTextureId()!=0)
                {
                    ScreenShotHelper.nativeImage.downloadTexture(0,true);
                    ScreenShotHelper.nativeImage.flipY();
                    List<Integer> pixels = new ArrayList<>(height*width);
                    for (int y = 0; y < height; y++)
                    {
                        for (int x = 0; x < width; x++)
                        {
                            int red = nativeImage.getRedOrLuminance(x, y) & 0xFF;
                            int green = nativeImage.getGreenOrLuminance(x, y) & 0xFF;
                            int blue = nativeImage.getBlueOrLuminance(x, y) & 0xFF;
                            int pixel = (red << 16) | (green << 8) | blue;
                            pixels.add(pixel);
                        }
                    }
                    consumer.accept(new KPixels(ScreenShotHelper.height,ScreenShotHelper.width,pixels));
                }

            }
            finally
            {
                ScreenShotHelper.nativeImage.close();
            }
        });
    }
}
