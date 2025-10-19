package com.kerwhite.kmod.gui.widget;

import com.kerwhite.kmod.KmodRuntimeException;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

public class DirectionLinkedButton extends LinkedButton
{

    public DirectionLinkedButton(int x, int y, int height, int width, Component p_259351_, @Nullable Button.OnPress onPress)
    {
        super(x, y, height, width, p_259351_, onPress);
        this.setTooltip(Tooltip.create(Component.literal("滚母")));
    }
    @Override
    public void onClick(double p_93371_, double p_93372_)
    {
        if(this.getLinkedpanel() !=null)
        {
            this.getLinkedpanel().visible = true;
            this.getLinkedpanel().reSetAppearFrame();
            this.visible = false;
        }
        else
        {
            throw new KmodRuntimeException("Didn't linked to a floating panel!");
        }
        if(this.onPress!=null)
        {
            super.onClick(p_93371_, p_93372_);
        }
    }
    @Override
    public AssetOffsets.OffsetPair getPair()
    {
        return AssetOffsets.DIRECTION_OFFSET;
    }
}
