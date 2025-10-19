package com.kerwhite.kmod.gui.widget;


import com.kerwhite.kmod.corelib.render.GuiRenderHelper;
import com.kerwhite.kmod.gui.GuiAssets;
import com.kerwhite.kmod.register.BlockRegister;
import com.kerwhite.kmod.register.register;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public class TriColorButton extends Button
{
    public int control;
    //kbj:0:blue 1:white 2:red
    public TriColorButton(int x, int y)
    {
        super(x,y,32,32,Component.literal(""), (p_93751_ -> {
        }),DEFAULT_NARRATION);
    }
    public AssetOffsets.OffsetPair getPair(int control)
    {
        return switch (control)
        {
            case 0 -> AssetOffsets.TRI_COLOR_OFFSET_BLUE;
            case 1 -> AssetOffsets.TRI_COLOR_OFFSET_WHITE;
            case 2 -> AssetOffsets.TRI_COLOR_OFFSET_RED;
            default -> null;
        };

    }
    public float getXOffset(){return this.getPair(this.control).getxOffset();}
    public float getYOffset(){return this.getPair(this.control).getyOffset();}
    public float getXOffset_hov(){return this.getPair(this.control).getxOffset_hov();}
    public float getYOffset_hov(){return this.getPair(this.control).getyOffset_hov();}
    @Override
    protected void renderWidget(GuiGraphics p_281670_, int p_282682_, int p_281714_, float p_282542_)
    {
        Minecraft minecraft = Minecraft.getInstance();
        p_281670_.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        if (this.isHovered())
        {
            p_281670_.blit(GuiAssets.FLOATING_PANEL,this.getX(),this.getY(),32,32,this.getXOffset_hov(),this.getYOffset_hov(),16,16,200,100);
        }
        else
        {
            p_281670_.blit(GuiAssets.FLOATING_PANEL,this.getX(),this.getY(),32,32,this.getXOffset(),this.getYOffset(),16,16,200,100);
        }
        int i = getFGColor();
        GuiRenderHelper.renderItem(p_281670_,null,null, register.BLOCKENERGYTRANSPORTER.get().asItem().getDefaultInstance(),this.getX()+this.height/4,this.getY()+this.width/4,32,32);
        this.renderString(p_281670_, minecraft.font, i | Mth.ceil(this.alpha * 255.0F) << 24);
    }
}
