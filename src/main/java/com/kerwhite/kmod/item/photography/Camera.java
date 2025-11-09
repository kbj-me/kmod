package com.kerwhite.kmod.item.photography;

import com.kerwhite.kmod.corelib.screenshot.ScreenShotHelper;
import com.kerwhite.kmod.network.ModMessages;
import com.kerwhite.kmod.network.UniversalPacketWrapper;
import com.kerwhite.kmod.network.packet.c2s.KUpdatePixelPack;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;


public class Camera extends Item
{
    public Camera()
    {
        super(new Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
    }
    @Override
    public void appendHoverText(@NotNull ItemStack p_41421_, @Nullable Level p_41422_, @NotNull List<Component> p_41423_, @NotNull TooltipFlag p_41424_)
    {
        p_41423_.add(Component.literal("SelectedFeature: "+p_41421_.getOrCreateTag().getInt("SelectedFeature")));
        p_41423_.add(Component.literal("IsOn: "+p_41421_.getOrCreateTag().getBoolean("IsOn")));
        p_41423_.add(Component.literal("FrameCount: "+p_41421_.getOrCreateTag().getInt("FrameCount")));
        p_41423_.add(Component.literal("Scale: "+p_41421_.getOrCreateTag().getInt("Scale")));
        super.appendHoverText(p_41421_, p_41422_, p_41423_, p_41424_);
    }
    @SuppressWarnings("ALL")
    @Override
    public void inventoryTick(@NotNull ItemStack p_41404_, @NotNull Level p_41405_, @NotNull Entity p_41406_, int p_41407_, boolean p_41408_)
    {
        if(p_41406_ instanceof Player && p_41405_.isClientSide() && p_41404_.getOrCreateTag().getBoolean("IsOn"))
        {
            boolean flag = switch (p_41404_.getOrCreateTag().getInt("FrameCount"))
            {
                case 5 ->
                        p_41407_ % 20 == 0 || p_41407_ % 20 == 3 || p_41407_ % 20 == 7 || p_41407_ % 20 == 11 || p_41407_ % 20 == 14;
                case 10 ->
                        p_41407_ % 20 == 0 || p_41407_ % 20 == 1 || p_41407_ % 20 == 3 || p_41407_ % 20 == 5 || p_41407_ % 20 == 7 || p_41407_ % 20 == 9 || p_41407_ % 20 == 11 || p_41407_ % 20 == 13 || p_41407_ % 20 == 15 || p_41407_ % 20 == 17 || p_41407_ % 20 == 19;
                case 20 -> true;
                default -> false;
            };
            if(flag && p_41404_.getOrCreateTag().getInt("Scale")!=0)
            {
                switch (p_41404_.getOrCreateTag().getInt("Scale"))
                {
                    case 1:
                        ScreenShotHelper.getPixels((kpixels)-> UniversalPacketWrapper.newInstance(KUpdatePixelPack.class).writeUUID(p_41406_.getUUID()).async_writeCustom((Consumer<FriendlyByteBuf>) kpixels::toBytes, (Consumer<UniversalPacketWrapper>) wrapper -> ModMessages.sendToServer((KUpdatePixelPack)wrapper.build())));
                        break;
                    default:
                        ScreenShotHelper.getPixelsAsScale(p_41404_.getOrCreateTag().getInt("Scale"),(kpixels)-> UniversalPacketWrapper.newInstance(KUpdatePixelPack.class).writeUUID(p_41406_.getUUID()).async_writeCustom((Consumer<FriendlyByteBuf>) kpixels::toBytes, (Consumer<UniversalPacketWrapper>) wrapper -> ModMessages.sendToServer((KUpdatePixelPack)wrapper.build())));
                        break;
                }
            }
        }
        super.inventoryTick(p_41404_, p_41405_, p_41406_, p_41407_, p_41408_);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand interactionHand)
    {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        CompoundTag tag =  itemStack.getOrCreateTag();
        if(player.isCrouching())
        {
            if(!level.isClientSide())
            {
                /*
                0->IsOn
                1->FrameCount
                */
                switch (tag.getInt("SelectedFeature"))
                {
                    case 0:
                        tag.putBoolean("IsOn",!tag.getBoolean("IsOn"));
                        player.sendSystemMessage(Component.literal("Set [IsOn] to "+tag.getBoolean("IsOn")));
                        break;
                    case 1:
                        switch (tag.getInt("FrameCount"))
                        {
                            case 0:
                                tag.putInt("FrameCount",5);
                            case 5:
                                tag.putInt("FrameCount",10);
                                player.sendSystemMessage(Component.literal("Set [FrameCount] to "+tag.getInt("FrameCount")));
                                break;
                            case 10:
                                tag.putInt("FrameCount",20);
                                player.sendSystemMessage(Component.literal("Set [FrameCount] to "+tag.getInt("FrameCount")));
                                break;
                            case 20:
                                tag.putInt("FrameCount",5);
                                player.sendSystemMessage(Component.literal("Set [FrameCount] to "+tag.getInt("FrameCount")));
                                break;
                        }
                        break;
                    case 2:
                        switch (tag.getInt("Scale"))
                        {
                            case 0:
                                tag.putInt("Scale",1);
                            case 1:
                                tag.putInt("Scale",2);
                                player.sendSystemMessage(Component.literal("Set [Scale] to "+tag.getInt("Scale")));
                                break;
                            case 2:
                                tag.putInt("Scale",4);
                                player.sendSystemMessage(Component.literal("Set [Scale] to "+tag.getInt("Scale")));
                                break;
                            case 4:
                                tag.putInt("Scale",1);
                                player.sendSystemMessage(Component.literal("Set [Scale] to "+tag.getInt("Scale")));
                                break;
                        }
                        break;
                    default:
                        break;
                }
            }
            else
            {
                Minecraft.getInstance().gameRenderer.displayItemActivation(this.getDefaultInstance());
            }
        }
        else
        {
            if(!level.isClientSide() && !(player.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof Viewer))
            {
                switch (tag.getInt("SelectedFeature"))
                {
                    case 0:
                        tag.putInt("SelectedFeature", 1);
                        player.sendSystemMessage(Component.literal("Set [SelectedFeature] to 1 [FrameCount]"));
                        break;
                    case 1:
                        tag.putInt("SelectedFeature", 2);
                        player.sendSystemMessage(Component.literal("Set [SelectedFeature] to 2 [Scale]"));
                        break;
                    case 2:
                        tag.putInt("SelectedFeature", 0);
                        player.sendSystemMessage(Component.literal("Set [SelectedFeature] to 0 [IsOn]"));
                        break;
                    default:
                        break;
                }
            }
            else if(!level.isClientSide() && player.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof Viewer)
            {
                player.getItemInHand(InteractionHand.OFF_HAND).getOrCreateTag().putUUID("UUID",player.getUUID());
                player.getItemInHand(InteractionHand.OFF_HAND).getOrCreateTag().putString("Name",player.getName().getString());
                player.sendSystemMessage(Component.literal("Set player uuid to: "+ player.getUUID() + "player name to: " + player.getName().getString()));
            }
        }
        return super.use(level, player, interactionHand);
    }
}
