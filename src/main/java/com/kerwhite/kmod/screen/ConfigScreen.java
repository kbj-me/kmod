package com.kerwhite.kmod.screen;

import java.util.Random;
import java.util.logging.Logger;

import com.kerwhite.kmod.blockentity.BlockEntityEnergyTransporter;
import com.kerwhite.kmod.kmod;
import com.kerwhite.kmod.network.KRequestPack;
import com.kerwhite.kmod.network.KUpdatePacket;
import com.kerwhite.kmod.network.ModMessages;
import com.kerwhite.kmod.network.UpdatePacketWrapper;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Tooltip;
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
@SuppressWarnings("all")
public class ConfigScreen extends Screen
{
    Player player;
    Level level;
    BlockPos pos;

    EditBox editBox;
    EditBox editBox2;
    private Button button;
    private Button button2;
    private Button button3;
    private Button button4;
    public Button button5;
    public int maxIO=0;
    public String bindPlayer="";
    public boolean isPlayerMode=false;
    public boolean isOut=false;
    Random random = new Random(System.currentTimeMillis());
    int nextint = random.nextInt();
    ResourceLocation FIRST_GUI_TEXTURE = new ResourceLocation(kmod.MODID, "textures/gui/configscreen.png");
    ResourceLocation GUI_TEXTURE_1 = new ResourceLocation(kmod.MODID, "textures/gui/cf1.png");
    private Component publicenergy=Component.literal("Error to load public energy.");
    private Component privateenergy=Component.literal("Error to load private energy");
    public Component content = Component.translatable("ui.kmod.rcb");

    public void setPrivateenergy(Component privateenergy)
    {
        this.privateenergy = privateenergy;
    }
    public void setPublicenergy(Component publicenergy)
    {
        this.publicenergy = publicenergy;
    }
    public ConfigScreen(Component pTitle, Player serverPlayer, Level level1, BlockPos pos1)
    {
        super(pTitle);
        this.player=serverPlayer;
        this.pos=pos1;
        this.level=level1;
        //Minecraft.getInstance().screen
    }
    @Override
    public void tick()
    {
        super.tick();
        BlockEntity be = level.getBlockEntity(pos);
        if(be instanceof BlockEntityEnergyTransporter)
        {
            content = Component.literal(Integer.toString(((BlockEntityEnergyTransporter)be).energy));
        }
        this.Getint(level,pos);
        this.button5.setMessage(!this.isPlayerMode ? Component.translatable("gui.kmod.input1") : Component.translatable("gui.kmod.input2"));
        ModMessages.sendToServer(new KRequestPack());
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
        boolean res = this.Getint(level,pos);
        this.editBox = new EditBox(this.font, this.width / 2 - 60, 50, 70, 15, Component.translatable("gui." + kmod.MODID + ".first_gui"));
        this.editBox.setFilter(input->input.isEmpty()||input.matches("\\d+"));
        this.editBox2 = new EditBox(this.font, this.width / 2 - 60, 70, 70, 15, Component.translatable("gui." + kmod.MODID + ".first_gui2"));
        this.editBox.setValue(String.valueOf(this.maxIO));
        this.editBox2.setValue(String.valueOf(this.bindPlayer));
        //this.editBox.canConsumeInput();
        this.addWidget(this.editBox2);
        this.addWidget(this.editBox);
        this.setInitialFocus(this.editBox);
        // button的应该通过builder获得，其中的起一个参数是按钮的名称，第二个参数是按钮按下之后会有什么操作的回调函数。
        // pos是设置按钮的位置
        // size是按钮的大小
        this.button = new Button.Builder(Component.translatable("gui." + kmod.MODID + ".bindplayerget"), pButton -> {HandleButton(1);}).pos(this.width / 2 + 20, 70).size(40, 15).build();
        this.button4 = new Button.Builder(Component.translatable("gui." + kmod.MODID + ".bindplayerset"), pButton -> {HandleButton(4);}).pos(this.width / 2 + 60, 70).size(40, 15).build();
        this.button2 = new Button.Builder(Component.translatable("gui." + kmod.MODID + ".maxioget"), pButton -> {HandleButton(2);}).pos(this.width / 2 + 20, 50).size(40, 15).build();
        this.button3 = new Button.Builder(Component.translatable("gui." + kmod.MODID + ".maxioset"), pButton -> {HandleButton(3);}).pos(this.width / 2 + 60, 50).size(40, 15).build();
        this.button5 = new Button.Builder(Component.translatable("gui.kmod.err"), pButton -> {HandleButton(5);}).pos(this.width / 2 + 20, 90).size(80, 15).build();
        this.button5.setTooltip(Tooltip.create(Component.translatable("gui.kmod.select")));
        if(res)
        {
            this.button5.setMessage(!this.isPlayerMode?Component.translatable("gui.kmod.input1"):Component.translatable("gui.kmod.input2"));
        }
        else
        {
            this.button.setMessage(Component.translatable("gui.kmod.err"));
            this.button2.setMessage(Component.translatable("gui.kmod.err"));
            this.button3.setMessage(Component.translatable("gui.kmod.err"));
            this.button4.setMessage(Component.translatable("gui.kmod.err"));
        }
        this.addWidget(this.button);
        this.addWidget(this.button2);
        this.addWidget(this.button3);
        this.addWidget(this.button4);
        this.addWidget(this.button5);
        // 滑条，位置x，y，宽高w，h，滑条名称前缀，后缀，滑条的最小值，最大值，初始值，是否渲染文字
       // this.sliderBar = new ExtendedSlider(this.width / 2 - 100, 120, 200, 10, Component.translatable("gui." + ExampleMod.MODID + ".first_gui.slider"), Component.empty(), 0, 100, 0, true);
       // this.addWidget(this.sliderBar);
        // 别忘记的调用super
        super.init();
    }
    public boolean Getint(Level level,BlockPos pos)
    {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if(blockEntity instanceof BlockEntityEnergyTransporter)
        {
            BlockEntityEnergyTransporter blockEntityEnergyTransporter = ((BlockEntityEnergyTransporter)blockEntity);
            this.isOut = blockEntityEnergyTransporter.isOut;
            this.maxIO = blockEntityEnergyTransporter.maxIO;
            this.isPlayerMode = blockEntityEnergyTransporter.isPlayerMode;
            this.bindPlayer = blockEntityEnergyTransporter.bindPlayer;
            return true;
        }
        return false;
    }
    public void HandleButton(int id)
    {
        switch (id) {
            case 1:
                this.Getint(level, pos);
                this.editBox2.setValue(String.valueOf(this.bindPlayer));
                break;
            case 2:
                this.Getint(level, pos);
                this.editBox.setValue(String.valueOf(this.maxIO));
                //this.editBox.setValue(String.valueOf(MI));
                break;
            case 3:
                if (this.Getint(level, pos) && editBox.getValue() != "") {
                    UpdatePacketWrapper upw = new UpdatePacketWrapper();
                    FriendlyByteBuf buf = upw.GetBuf();
                    buf.writeInt(Integer.parseInt(editBox.getValue()));
                    buf.writeUtf(this.bindPlayer);
                    buf.writeBoolean(this.isPlayerMode);
                    buf.writeBoolean(this.isOut);
                    buf.writeBlockPos(this.pos);
                    upw.CreatePacket();
                    upw.SendPacketToServer();
                }
                break;
            case 4:
                if (this.Getint(level, pos) && editBox2.getValue() != "") {
                    UpdatePacketWrapper upw = new UpdatePacketWrapper();
                    FriendlyByteBuf buf = upw.GetBuf();
                    buf.writeInt(this.maxIO);
                    buf.writeUtf(editBox2.getValue());
                    buf.writeBoolean(this.isPlayerMode);
                    buf.writeBoolean(this.isOut);
                    buf.writeBlockPos(this.pos);
                    upw.CreatePacket();
                    upw.SendPacketToServer();
                }
                break;
            case 5:
                UpdatePacketWrapper upw = new UpdatePacketWrapper();
                FriendlyByteBuf buf = upw.GetBuf();
                if (this.Getint(level, pos))
                {
                    buf.writeInt(this.maxIO);
                    buf.writeUtf(this.bindPlayer);
                    if(button5.getMessage().getString().equals(Component.translatable("gui.kmod.input1").getString()))
                    {
                        System.out.println("public");
                        buf.writeBoolean(true);
                    }
                    else
                    {
                        System.out.println("private");
                        buf.writeBoolean(false);
                    }
                    System.out.println(button5.getMessage().getString());
                    System.out.println(Component.translatable("gui.kmod.input1").getString());
                    buf.writeBoolean(this.isOut);
                    buf.writeBlockPos(this.pos);
                    upw.CreatePacket();
                    upw.SendPacketToServer();
                    this.Getint(level, pos);
                }
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

        // 背景图的宽高
        int textureWidth = 300;
        int textureHeight = 200;
        // 渲染背景图
        pGuiGraphics.setColor(1, 1, 1, 1);
       // if(nextint%20 == 0)
      //  {
           // pGuiGraphics.blit(GUI_TEXTURE_1, this.width / 2 - 150, 10, 0, 0, 300, 200, textureWidth, textureHeight);
       // }
        //else if(nextint%20 == 1)
       // {
           // pGuiGraphics.blit(FIRST_GUI_TEXTURE, this.width / 2 - 150, 10, 0, 0, 300, 200, textureWidth, textureHeight);
       // }
       // else
       // {
            pGuiGraphics.blit(FIRST_GUI_TEXTURE, this.width / 2 - 150, 10, 0, 0, 300, 200, textureWidth, textureHeight);
       // }
        // 渲染字体类型，内容，位置，颜色，
        pGuiGraphics.drawCenteredString(this.font, Component.translatable("lang.kmod.gui.title"),this.width / 2, 15, 0x000000);
        pGuiGraphics.drawCenteredString(this.font, Component.translatable("lang.kmod.gui.maxio"),this.width / 2-100, 50, 0x000000);
        pGuiGraphics.drawCenteredString(this.font, Component.translatable("lang.kmod.gui.bindplayer"),this.width / 2-100, 70, 0x000000);
        pGuiGraphics.drawCenteredString(this.font, Component.translatable("lang.kmod.gui.isplayermode"),this.width / 2-100, 90, 0x000000);
        pGuiGraphics.drawCenteredString(this.font, content, this.width / 2, 30, 0xeb0505);
        pGuiGraphics.drawCenteredString(this.font, this.publicenergy, this.width / 2, 170, 0xeb0505);
        pGuiGraphics.drawCenteredString(this.font, this.privateenergy, this.width / 2, 180, 0xeb0505);
        // 渲染编辑框
        this.editBox.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        // 渲染 按钮
        this.button.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.button2.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.button3.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.button4.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.button5.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.editBox2.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        // 渲染滑条
        //this.sliderBar.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        // 别忘记了调用super
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }
    @Override
    public boolean isPauseScreen()
    {
        return false;
    }
}

