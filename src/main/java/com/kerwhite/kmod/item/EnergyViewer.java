package com.kerwhite.kmod.item;

import com.kerwhite.kmod.blockentity.BlockEntityEnergyTransporter;
import com.kerwhite.kmod.register.register;
import com.kerwhite.kmod.worldsaveddata.KWorldSavedData;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

@SuppressWarnings("All")
public class EnergyViewer extends Item
{
    int mode = 1;
   // boolean flag = false;
    public EnergyViewer()
    {
        super(new Properties().rarity(Rarity.RARE).fireResistant().stacksTo(1));
    }
   // @Override
   // public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand)
   // {
    //    super.use(level, player, interactionHand);
    //    if (!level.isClientSide() && !flag)
     //   {
     //       player.sendSystemMessage(Component.literal("www"));
      //  }
//
      //  return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
  //  }
    @Override
    public InteractionResult useOn(UseOnContext useOnContext)
    {
        int tpe=0;
        Level level = useOnContext.getLevel();
        Player player = useOnContext.getPlayer();
        BlockPos pos = useOnContext.getClickedPos();
        ItemStack itemStack = useOnContext.getPlayer().getItemInHand(InteractionHand.MAIN_HAND);
        if(level.isClientSide()&player.isCrouching())
        {
            Minecraft.getInstance().gameRenderer.displayItemActivation(new ItemStack(register.ENERGYVIEWERITEM.get()));
        }
        if (!level.isClientSide())
        {
            mode=useOnContext.getItemInHand().getTag().getInt("Mode");
            BlockEntity BE = level.getBlockEntity(pos);
            if(BE != null && BE instanceof BlockEntityEnergyTransporter)
            {
                switch (mode)
                {
                    case 1:
                        var ironBars = Component.translatable("lang.kmod.energy");
                        tpe=((BlockEntityEnergyTransporter) BE).energy;
                        player.sendSystemMessage(Component.literal("").append(ironBars).append(Integer.toString(tpe)));
                        break;
                    case 4:
                        ((BlockEntityEnergyTransporter) BE).setBindPlayer(player.getName().toString());
                        break;
                    case 5:
                        KWorldSavedData KWSD = KWorldSavedData.get(level);
                       // KWSD.addE(player.getName().toString(),111);
                       // KWSD.addPublic(222);
                        //KWSD.setPublicEnergy(KWSD.getPublicEnergy()+11111);
                        player.sendSystemMessage(Component.literal("PublicEnergy:"+Integer.toString(KWSD.getPublicEnergy())));
                        player.sendSystemMessage(Component.literal("PrivateEnergy:"+Integer.toString(KWSD.getE(player.getName().toString()))));
                        break;

                    default:
                        player.sendSystemMessage(Component.literal("??"));
                }
            }
            else
            {
                if (player.isCrouching())
                {
                    CompoundTag tag = itemStack.getOrCreateTag();
                    if(tag.getInt("Mode")==0){tag.putInt("Mode",1);}
                    if(tag.getInt("Mode")+1<=5){tag.putInt("Mode",tag.getInt("Mode")+1);}
                    else {tag.putInt("Mode",1);}
                    mode=tag.getInt("Mode");
                    Component meg = null;
                    meg = switch (mode)
                    {
                        case 1 -> Component.translatable("lang.kmod.energyviewer.1");
                        case 2 -> Component.translatable("lang.kmod.energyviewer.2");
                        case 3 -> Component.translatable("lang.kmod.energyviewer.3");
                        case 4 -> Component.translatable("lang.kmod.energyviewer.4");
                        case 5 -> Component.translatable("lang.kmod.energyviewer.debug");
                        default -> Component.translatable("lang.kmod.energyviewer.err");
                    };
                   //
                    player.sendSystemMessage(Component.literal("").append(meg));
                }
                else
                {
                    var ironBars2 = Component.translatable("lang.kmod.energy.no");
                    player.sendSystemMessage(Component.literal("").append(ironBars2));
                }
                player.getCooldowns().addCooldown(this,20);
            }
            //flag=false;
            return InteractionResult.PASS;
        }
        else
        {return InteractionResult.PASS;}

    }
}
