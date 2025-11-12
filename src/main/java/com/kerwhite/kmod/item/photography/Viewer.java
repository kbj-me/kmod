package com.kerwhite.kmod.item.photography;

import com.kerwhite.kmod.screen.ScreenTransportationScreen;
import com.kerwhite.kmod.screen.UniversalGuiOpenWrapper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class Viewer extends Item
{
    public Viewer()
    {
        super(new Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
    }

    @Override
    public void appendHoverText(@NotNull ItemStack p_41421_, @Nullable Level p_41422_, @NotNull List<Component> p_41423_, @NotNull TooltipFlag p_41424_)
    {
        try
        {
            p_41423_.add(Component.literal("UUID: "+p_41421_.getOrCreateTag().getUUID("UUID")));
            p_41423_.add(Component.literal("Name: "+p_41421_.getOrCreateTag().getString("Name")));
        }
        catch(Exception e)
        {
        }

        super.appendHoverText(p_41421_, p_41422_, p_41423_, p_41424_);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand interactionHand)
    {
        CompoundTag tag = player.getItemInHand(interactionHand).getOrCreateTag();
        List<ServerPlayer> playerList;
        if(player.isCrouching() && !level.isClientSide())
        {
            playerList  = Objects.requireNonNull(level.getServer()).getPlayerList().getPlayers();
            for(ServerPlayer p : playerList)
            {
                //System.out.println(p.getName().getString());
            }
        }
        else if(!player.isCrouching() && level.isClientSide() && !(player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof Camera))
        {
            try
            {
                UniversalGuiOpenWrapper.newInstance(ScreenTransportationScreen.class).addArgs(tag.getUUID("UUID")).autoArgClass().build().setScreen();
            }
            catch (Exception e)
            {
                player.sendSystemMessage(Component.literal("Error opening screen (May uuid = null)"));
            }
        }
        return super.use(level, player, interactionHand);
    }
}
