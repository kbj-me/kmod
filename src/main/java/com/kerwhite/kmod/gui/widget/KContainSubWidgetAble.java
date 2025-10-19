package com.kerwhite.kmod.gui.widget;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Iterator;
@SuppressWarnings("All")
public interface KContainSubWidgetAble
{
    void onSubWidgetsClicked(double p_93641_, double p_93642_, int p_93643_);
    void onSubWidgetsReleased(double p_93684_, double p_93685_, int p_93686_);
    void onSubWidgetsDragged(double x, double y, int p_93647_, double p_93648_, double p_93649_);
    void onSubWidgetsMouseMoved(double p_94758_, double p_94759_);
    void onSubWidgetsMouseScrolled(double p_94734_, double p_94735_, double p_94736_);
    boolean subWidgetclicked(double p_93681_, double p_93682_,@Nonnull AbstractWidget widget);
    void addSubWidget(@Nullable AbstractWidget widget);
    void clearSubWidget();
    Iterator<AbstractWidget> getIterator();
    void onSubWidgetsRendered(@NotNull GuiGraphics guiGraphics, int p_268034_, int p_268009_, float p_268085_);
}
