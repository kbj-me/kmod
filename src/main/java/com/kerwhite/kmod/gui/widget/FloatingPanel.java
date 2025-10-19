package com.kerwhite.kmod.gui.widget;

import com.kerwhite.kmod.gui.GuiAssets;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FloatingPanel extends AbstractWidget implements KContainSubWidgetAble
{
    private LinkedButton linkedButton;
    private final List<AbstractWidget> subWidgets = new ArrayList<>();
    private int renderx;
    private int rendery;
    private int appearframe;
    private int disappearframe;
    public void reSetAppearFrame()
    {
        appearframe = 0;
        this.visible = true;
        disappearframe = -65;
    }
    public void reSetDisapperFrame()
    {
        disappearframe = 0;
    }
    public FloatingPanel(int x, int y, int width, int height, Component p_93633_)
    {
        super(x, y, width, height, p_93633_);
        this.renderx = x;
        this.rendery = y;
    }
    public LinkedButton getLinkedButton()
    {
        return linkedButton;
    }
    public void setLinkedButton(LinkedButton linkedButton)
    {
        this.linkedButton = linkedButton;
    }
    @Override
    public void addSubWidget(AbstractWidget widget)
    {
        this.subWidgets.add(widget);
    }
    @Override
    public void clearSubWidget()
    {
        this.subWidgets.clear();
    }
    @Override
    public Iterator<AbstractWidget> getIterator()
    {
        return this.subWidgets.iterator();
    }
    @Override
    protected void renderWidget(@NotNull GuiGraphics guiGraphics, int p_268034_, int p_268009_, float p_268085_)
    {
        if(appearframe<64){appearframe++;}
        if(disappearframe==64){this.visible=false;}
        if(this.visible)
        {
            if(disappearframe>-64)
            {
                disappearframe++;
                guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F - (float) disappearframe /64);
            }
            else
            {
                guiGraphics.setColor(1.0F, 1.0F, 1.0F, (float) appearframe /64);
            }
            guiGraphics.blit(GuiAssets.FLOATING_PANEL,renderx,rendery,100,100,0.0f,0.0f,100,100,200,100);
            if(this.isFocused())
            {
                guiGraphics.blit(GuiAssets.FLOATING_PANEL,renderx,rendery,100,7,100.0f,0.0f,100,7,200,100);
                if(this.isClickExit(p_268034_, p_268009_))
                {
                    guiGraphics.blit(GuiAssets.FLOATING_PANEL,renderx+93,rendery+1,6,6,132.0f,8.0f,6,6,200,100);
                }
            }
            guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
            if(this.isNormalState())
            {
                this.onSubWidgetsRendered(guiGraphics, p_268034_, p_268009_, p_268085_);
            }
        }
    }
    private boolean isNormalState()
    {
        return appearframe == 64 && disappearframe<-64;
    }
    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput p_259858_)
    {
    }
    @Override
    public boolean mouseClicked(double p_93641_, double p_93642_, int p_93643_)
    {

        boolean flag = this.clicked(p_93641_, p_93642_);
        if (flag)
        {
            this.onClick(p_93641_, p_93642_);
            this.onSubWidgetsClicked(p_93641_, p_93642_, p_93643_);
            return true;
        }
        return false;
    }
    @Override
    public void onSubWidgetsRendered(@NotNull GuiGraphics guiGraphics, int p_268034_, int p_268009_, float p_268085_)
    {
        for(AbstractWidget subWidget : this.subWidgets)
        {
            if(subWidget != null)
            {
                subWidget.render(guiGraphics, p_268034_, p_268009_, p_268085_);
            }
        }
    }
    @Override
    public void onSubWidgetsClicked(double p_93641_, double p_93642_, int p_93643_)
    {
        for(AbstractWidget subWidget : this.subWidgets)
        {
            if(subWidget != null)
            {
                subWidget.mouseClicked(p_93641_, p_93642_, p_93643_);
            }
        }
    }
    @Override
    public void onSubWidgetsReleased(double p_93684_, double p_93685_, int p_93686_)
    {
        for(AbstractWidget subWidget : this.subWidgets)
        {
            if(subWidget != null )
            {
                subWidget.mouseReleased(p_93684_, p_93685_, p_93686_);
            }
        }
    }
    @Override
    public void onSubWidgetsDragged(double x, double y, int p_93647_, double p_93648_, double p_93649_)
    {
        for(AbstractWidget subWidget : this.subWidgets)
        {
            if(subWidget != null)
            {
                subWidget.mouseDragged(x, y, p_93647_, p_93648_, p_93649_);
            }
        }
    }
    @Override
    public void onSubWidgetsMouseMoved(double p_94758_, double p_94759_)
    {
        for(AbstractWidget subWidget : this.subWidgets)
        {
            if(subWidget != null)
            {
                subWidget.mouseMoved(p_94758_, p_94759_);
            }
        }
    }
    @Override
    public void onSubWidgetsMouseScrolled(double p_94734_, double p_94735_, double p_94736_)
    {
        for(AbstractWidget subWidget : this.subWidgets)
        {
            if(subWidget != null)
            {
                subWidget.mouseScrolled(p_94734_, p_94735_, p_94736_);
            }
        }
    }
    @SuppressWarnings("unused")
    public int getRenderx(){return renderx;}
    @SuppressWarnings("unused")
    public int getRendery(){return rendery;}
    @SuppressWarnings("unused")
    public void setRenderx(int renderx){this.renderx = renderx;}
    @SuppressWarnings("unused")
    public void setRendery(int rendery){this.rendery = rendery;}

    @Override
    public boolean mouseReleased(double p_93684_, double p_93685_, int p_93686_)
    {
        this.onRelease(p_93684_, p_93685_);
        this.onSubWidgetsReleased(p_93684_, p_93685_, p_93686_);
        return true;
    }
    @Override
    public boolean subWidgetclicked(double p_93681_, double p_93682_, @NotNull AbstractWidget widget)
    {
        return widget.active && widget.visible && p_93681_ >= (double)widget.getX() && p_93682_ >= (double)widget.getY() && p_93681_ < (double)(widget.getX() + widget.getWidth()) && p_93682_ < (double)(widget.getY() + widget.getHeight());
    }
    @Override
    public boolean mouseDragged(double x, double y, int p_93647_, double p_93648_, double p_93649_)
    {
        if(this.isNormalState())
        {
            this.onSubWidgetsDragged(x, y, p_93647_, p_93648_, p_93649_);
            boolean flag = true;
            for(AbstractWidget subWidget : this.subWidgets)
            {
                if(subWidget != null && subWidgetclicked(x,y,subWidget))
                {
                    flag=false;
                }
            }
            if(flag)
            {
                this.onDrag(x, y, p_93648_, p_93649_);
            }
        }
        return true;
    }
    @Override
    protected boolean clicked(double p_93681_, double p_93682_)
    {
        return p_93681_ >= (double)this.renderx && p_93682_ >= (double)this.rendery && p_93681_ <= (double)(this.renderx + this.width) && p_93682_ <= (double)(this.rendery + this.height);
    }
    @SuppressWarnings("RedundantMethodOverride")
    @Override
    public void onRelease(double p_93669_, double p_93670_)
    {
    }
    @Override
    protected void onDrag(double p_93636_, double p_93637_, double p_93638_, double p_93639_)
    {
        this.renderx+= (int) p_93638_;
        this.rendery+= (int) p_93639_;
        for(AbstractWidget subWidget : subWidgets)
        {
            if(subWidget != null)
            {
                subWidget.setX(subWidget.getX()+(int) p_93638_);
                subWidget.setY(subWidget.getY()+(int) p_93639_);
            }
        }
    }
    public boolean isClickExit(double p_93634_, double p_93635_)
    {
        return p_93634_>=renderx+93 && p_93634_<= renderx+99 && p_93635_>=rendery+1 && p_93635_<=rendery+6;
    }
    @Override
    public void onClick(double p_93634_, double p_93635_)
    {
        if(isClickExit(p_93634_, p_93635_) && this.isFocused() && this.isNormalState())
        {
            this.reSetDisapperFrame();
            this.linkedButton.visible = true;
        }
    }
    @Override
    public boolean mouseScrolled(double p_94734_, double p_94735_, double p_94736_)
    {
        this.onSubWidgetsMouseScrolled(p_94734_, p_94735_, p_94736_);
        return true;
    }
    @Override
    public void mouseMoved(double p_94758_, double p_94759_)
    {
        this.onSubWidgetsMouseMoved(p_94758_, p_94759_);
    }
}
