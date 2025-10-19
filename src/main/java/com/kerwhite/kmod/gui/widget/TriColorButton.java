package com.kerwhite.kmod.gui.widget;


import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;

public class TriColorButton extends Button
{
    public int control;
    //kbj:0:blue 1:white 2:red
    public TriColorButton(int x, int y)
    {
        super(x,y,16,16,Component.literal(""), (p_93751_ -> {return;}),DEFAULT_NARRATION);
    }
}
