package com.kerwhite.kmod.gui.widget;

public class PanelLinkHelper
{
    public static void link(FloatingPanel panel , LinkedButton button , boolean ispanelvisibleatfirst)
    {
        panel.setLinkedButton(button);
        button.setLinkedpanel(panel);
        if(ispanelvisibleatfirst)
        {
            button.visible = false;
        }
        else
        {
            panel.visible = false;
        }
    }
    public static boolean compareLink(FloatingPanel panel , LinkedButton button)
    {
        return panel.getLinkedButton() == button && button.getLinkedpanel() == panel;
    }
}
