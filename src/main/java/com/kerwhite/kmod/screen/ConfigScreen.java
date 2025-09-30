package com.kerwhite.kmod.screen;

import com.kerwhite.kmod.blockentity.BlockEntityEnergyTransporter;
import com.kerwhite.kmod.kmod;
import com.kerwhite.kmod.network.KUpdatePacket;
import com.kerwhite.kmod.network.ModMessages;
import com.kerwhite.kmod.network.UpdatePacketWrapper;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ConfigScreen extends Screen
{
    Player player;
    Level level;
    BlockPos pos;
    EditBox editBox;
    EditBox editBox2;
    // button是按钮
    Button button;
    // 我们的GUI界面的背景图片的位置
    ResourceLocation FIRST_GUI_TEXTURE = new ResourceLocation(kmod.MODID, "textures/gui/configscreen.png");
    // 这里的content是我们用于渲染文字的内容
    Component title = Component.translatable("ui.kmod.title");
    Component content = Component.literal("aaa");
    // 滑条，参考调节音量那个
    //Slider sliderBar;
    public ConfigScreen(Component pTitle, Player serverPlayer,Level level1,BlockPos pos1)
    {
        super(pTitle);
        this.player=serverPlayer;
        this.pos=pos1;
        this.level=level1;
    }
    @Override
    public void tick()
    {
        super.tick();
        BlockEntity be = level.getBlockEntity(pos);
        if(be instanceof BlockEntityEnergyTransporter)
        {
            //UpdatePacketWrapper upw = new UpdatePacketWrapper();
           // upw.SetBuf(upw.GetBuf().writeUtf("www"));
           // upw.CreatePacket();
            content = Component.literal(Integer.toString(((BlockEntityEnergyTransporter)be).energy));
        }
    }
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button)
    {
        for (GuiEventListener child : this.children()) {
            if (child.mouseClicked(mouseX, mouseY, button))
            {
                if(child instanceof EditBox)
                {
                    this.setFocused(child);
                }
                //child.

                //player.sendSystemMessage(Component.literal("www"));
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
    @Override
    protected void init()
    {
        // 创建一个输入框，并设置其位置、大小以及默认文本
        // x,y,width,height,component
       // this.addRenderableWidget()
        this.editBox = new EditBox(this.font, this.width / 2 - 100, 66, 200, 20, Component.translatable("gui." + kmod.MODID + ".first_gui"));
        this.editBox2 = new EditBox(this.font, this.width / 2 - 100, 100, 50, 20, Component.translatable("gui." + kmod.MODID + ".first_gui"));

        //this.editBox.canConsumeInput();
        this.addWidget(this.editBox2);
        this.addWidget(this.editBox);
        this.setInitialFocus(this.editBox);
        // button的应该通过builder获得，其中的起一个参数是按钮的名称，第二个参数是按钮按下之后会有什么操作的回调函数。
        // pos是设置按钮的位置
        // size是按钮的大小
        this.button = new Button.Builder(Component.translatable("gui." + kmod.MODID + ".first_gui.save"), pButton -> {HandleButton(1);}).pos(this.width / 2 - 40, 96).size(80, 20).build();
        this.addWidget(this.button);
        // 滑条，位置x，y，宽高w，h，滑条名称前缀，后缀，滑条的最小值，最大值，初始值，是否渲染文字
       // this.sliderBar = new ExtendedSlider(this.width / 2 - 100, 120, 200, 10, Component.translatable("gui." + ExampleMod.MODID + ".first_gui.slider"), Component.empty(), 0, 100, 0, true);
       // this.addWidget(this.sliderBar);
        // 别忘记的调用super
        super.init();
    }
    public void HandleButton(int id)
    {
        switch (id)
        {
            case 1:


              //  Minecraft.getInstance().levelRenderer.setD
                //content = Component.literal("www");
                //UpdatePacketWrapper.SetFriendlyByteBuff(UpdatePacketWrapper.GetFriendlyByteBuff().writeUtf("www"));
               // UpdatePacketWrapper.CreatePacket();
                //UpdatePacketWrapper.SendPacketToServer();
                //UpdatePacketWrapper.clean();
                //UpdatePacketWrapper.setkUpdatePacket(UpdatePacketWrapper.getkUpdatePacket().);
                //ModMessages.sendToServer(new KUpdatePacket(new FriendlyByteBuf(Unpooled.buffer()).writeUtf(this.editBox.getValue())));
                break;
            case 2:
                break;
            case 3:
                break;
        }
    }
    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick)
    {
        // 暂时搞明白是干嘛的
        this.renderBackground(pGuiGraphics);
        //this.renderBackground(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        // 设置渲染时候的色彩和透明度是正常的。
        pGuiGraphics.setColor(1, 1, 1, 1);
        // 背景图的宽高
        int textureWidth = 300;
        int textureHeight = 200;
        // 渲染背景图
        pGuiGraphics.blit(FIRST_GUI_TEXTURE, this.width / 2 - 150, 10, 0, 0, 300, 200, textureWidth, textureHeight);
        // 渲染字体类型，内容，位置，颜色，
        pGuiGraphics.drawCenteredString(this.font, Component.translatable("lang.kmod.gui.title"),this.width / 2, 15, 0x000000);
        pGuiGraphics.drawCenteredString(this.font, content, this.width / 2 - 10, 30, 0xeb0505);
        // 渲染编辑框
        this.editBox.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        // 渲染 按钮
        this.button.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.editBox2.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        // 渲染滑条
        //this.sliderBar.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        // 别忘记了调用super
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }
    @Override
    public boolean isPauseScreen() {
        return false;
    }
}

