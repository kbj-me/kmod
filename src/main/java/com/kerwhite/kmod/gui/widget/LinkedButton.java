package com.kerwhite.kmod.gui.widget;


import com.kerwhite.kmod.gui.GuiAssets;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

import javax.annotation.Nullable;

public class LinkedButton extends Button
{
    private FloatingPanel linkedpanel;
    public LinkedButton(int x, int y,int height,int width, Component p_259351_,@Nullable Button.OnPress onPress)
    {
        super(x,y,width,height,p_259351_,onPress,DEFAULT_NARRATION);
    }
    public void setLinkedpanel(FloatingPanel panel)
    {
        this.linkedpanel = panel;
    }
    public FloatingPanel getLinkedpanel()
    {
        return this.linkedpanel;
    }
    //need override
    public AssetOffsets.OffsetPair getPair(){return new AssetOffsets.OffsetPair(0,0,0,0);}
    public float getXOffset(){return this.getPair().getxOffset();}
    public float getYOffset(){return this.getPair().getyOffset();}
    public float getXOffset_hov(){return this.getPair().getxOffset_hov();}
    public float getYOffset_hov(){return this.getPair().getyOffset_hov();}
    @Override
    protected void renderWidget(GuiGraphics p_281670_, int p_282682_, int p_281714_, float p_282542_)
    {
        Minecraft minecraft = Minecraft.getInstance();
        p_281670_.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        if (this.isHovered())
        {
            p_281670_.blit(GuiAssets.FLOATING_PANEL,this.getX(),this.getY(),16,16,this.getXOffset_hov(),this.getYOffset_hov(),16,16,200,100);
        }
        else
        {
            p_281670_.blit(GuiAssets.FLOATING_PANEL,this.getX(),this.getY(),16,16,this.getXOffset(),this.getYOffset(),16,16,200,100);
        }
        int i = getFGColor();
        this.renderString(p_281670_, minecraft.font, i | Mth.ceil(this.alpha * 255.0F) << 24);
    }
    @Override
    public void onClick(double p_93371_, double p_93372_){super.onClick(p_93371_, p_93372_);}
}
