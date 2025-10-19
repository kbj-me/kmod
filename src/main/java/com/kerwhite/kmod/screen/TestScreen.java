package com.kerwhite.kmod.screen;

import com.kerwhite.kmod.gui.widget.DirectionLinkedButton;
import com.kerwhite.kmod.gui.widget.FloatingPanel;
import com.kerwhite.kmod.gui.widget.LinkedButton;
import com.kerwhite.kmod.gui.widget.PanelLinkHelper;
import com.kerwhite.kmod.kmod;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

public class TestScreen extends Screen
{
    FloatingPanel floatingPanel;
    Button button;
    LinkedButton linkedButton;
    protected TestScreen(Component p_96550_)
    {
        super(p_96550_);
    }
    @Override
    public boolean isPauseScreen()
    {
        return false;
    }
    public void renderBlock(GuiGraphics guiGraphics)
    {
        PoseStack poseStack =  guiGraphics.pose();
        MultiBufferSource.BufferSource bufferSource = guiGraphics.bufferSource();
        poseStack.pushPose();
        poseStack.mulPoseMatrix(new Matrix4f().scaling(1, -1, 1));
        Lighting.setupForFlatItems();
        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(Blocks.ACACIA_BUTTON.defaultBlockState(),poseStack,bufferSource,255, OverlayTexture.NO_OVERLAY);
        Lighting.setupFor3DItems();
        poseStack.popPose();
    }
    @Override
    protected void init()
    {
        floatingPanel=new FloatingPanel(0,0,100,100,Component.literal("111"));
        this.button = new Button.Builder(Component.translatable("gui." + kmod.MODID + ".bindplayerget"), pButton -> {System.out.println("www");}).pos(this.floatingPanel.getWidth() / 2 + 20, 70).size(40, 15).build();
        this.addWidget(floatingPanel);
        floatingPanel.clearSubWidget();
        floatingPanel.addSubWidget(this.button);
        //this.linkedButton = new LinkedButton(new  Button.Builder(Component.translatable("gui." + kmod.MODID + ".bindplayerget"), pButton -> {System.out.println("www");}).pos(this.floatingPanel.getWidth() / 2 + 20, 70).size(40, 15),this.floatingPanel);
        this.linkedButton = new DirectionLinkedButton(this.floatingPanel.getWidth() / 2 + 20, 70,16, 16,Component.literal("testlink"),null);
        PanelLinkHelper.link(this.floatingPanel,this.linkedButton,false);
        this.addWidget(linkedButton);
        super.init();
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int p_281550_, int p_282878_, float p_282465_)
    {
        //this.renderBackground(guiGraphics);
        this.floatingPanel.render(guiGraphics, p_281550_, p_282878_, p_282465_);
        this.linkedButton.render(guiGraphics, p_281550_, p_282878_, p_282465_);
        //guiGraphics.renderItem(new ItemStack(Items.ACACIA_BOAT),0,0);
        //Quaternionf quaternionf = new Quaternionf().fromAxisAngleDeg(new Vector3f(0,1,0), 180).fromAxisAngleDeg(new Vector3f(1,0,0),180);
        //GuiRenderHelper.renderEntity(guiGraphics,20,70,40,quaternionf,null,minecraft.player);
       // Quaternionf quaternionf = new Quaternionf().fromAxisAngleDeg(new Vector3f(1,0,0),0).fromAxisAngleDeg(new Vector3f(0,1,0),0);
      //  GuiRenderHelper.renderBlock(guiGraphics,quaternionf,Blocks.CRAFTING_TABLE.defaultBlockState(),100,40,40);
        super.render(guiGraphics, p_281550_, p_282878_, p_282465_);
    }

    @Override
    public boolean mouseClicked(double p_94695_, double p_94696_, int p_94697_)
    {
        if(this.getFocused()!=null)
        {
            this.getFocused().setFocused(false);
        }
        for(GuiEventListener guiEventListener : this.children())
        {
            if(guiEventListener.mouseClicked(p_94695_,p_94696_,p_94697_))
            {
                this.setFocused(guiEventListener);
            }
        }
        return super.mouseClicked(p_94695_, p_94696_, p_94697_);
    }

    @Override
    public boolean mouseDragged(double p_94699_, double p_94700_, int p_94701_, double p_94702_, double p_94703_)
    {
        return super.mouseDragged(p_94699_, p_94700_, p_94701_, p_94702_, p_94703_);
    }

    @Override
    public void mouseMoved(double p_94758_, double p_94759_)
    {
        super.mouseMoved(p_94758_, p_94759_);
    }

    @Override
    public boolean mouseReleased(double p_94722_, double p_94723_, int p_94724_)
    {
        return super.mouseReleased(p_94722_, p_94723_, p_94724_);
    }
}
