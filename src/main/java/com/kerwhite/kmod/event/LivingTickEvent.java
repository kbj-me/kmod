package com.kerwhite.kmod.event;

import com.kerwhite.kmod.item.SpearItem;
import com.kerwhite.kmod.kmod;
import com.kerwhite.kmod.network.KSpeedUpdatePack;
import com.kerwhite.kmod.network.ModMessages;
import com.kerwhite.kmod.register.EnchantmentsRegister;
import com.kerwhite.kmod.utils.PlayerSpeed;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Random;
import java.util.logging.LogRecord;

@Mod.EventBusSubscriber(modid = kmod.MODID)
public class LivingTickEvent
{
    @SubscribeEvent
    public static void onEntityCollision(LivingEvent.LivingTickEvent event)
    {
        if(event.getEntity() instanceof Player && (!event.getEntity().level().isClientSide()))
        {
            Player p = (Player)event.getEntity();
            double speed = 0.0D;
            try
            {
                speed = PlayerSpeed.playerspeed.get(p.getName().getString());
                speed = (speed+0.93)*1.5;
                //p.sendSystemMessage(Component.literal(String.valueOf(speed)));
            }
            catch (NullPointerException e)
            {
                kmod.LOGGER.info("Kmod getentityspeed cause NullPointerException:"+e.getMessage());
            }
            if(true)
            {
                int level = p.getItemInHand(InteractionHand.MAIN_HAND).getEnchantmentLevel(EnchantmentsRegister.EXPAND.get());
                AABB entityBoundingBox = event.getEntity().getBoundingBox().inflate(0.4+level, 0.4+level, 0.4+level);
                List<LivingEntity> nearbyEntities = event.getEntity().level().getEntitiesOfClass(
                        LivingEntity.class,
                        entityBoundingBox,
                        e -> e != event.getEntity() && e.isAlive() && e!=p.getVehicle()  // 排除自身和已死亡的实体
                );

                if(p.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof SpearItem && p.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTag().getInt("Mode")==1)
                {
                    if(!nearbyEntities.isEmpty())
                    {
                        for(Entity entity :nearbyEntities)
                        {
                            float damage = (float) (speed*(((SpearItem) p.getItemInHand(InteractionHand.MAIN_HAND).getItem()).getDamage()+p.getItemInHand(InteractionHand.MAIN_HAND).getEnchantmentLevel(Enchantments.SHARPNESS)*1.25));
                            if(damage>15){damage+=4;}
                            Random random = new Random(p.level().getGameTime());
                            int next = Math.abs(random.nextInt()%10);
                            System.out.println(next);
                            if(next <= 3)
                            {
                                ((ServerLevel)p.level()).sendParticles(ParticleTypes.CRIT,entity.getX(),entity.getY()+0.5,entity.getZ(),100,1,1,1,0);
                                ((ServerLevel)p.level()).sendParticles(ParticleTypes.FLAME,entity.getX(),entity.getY(),entity.getZ(),100,0.5,0.5,0.5,0);
                                ((ServerLevel)p.level()).playSound(null,new BlockPos((int)p.getX(),(int)p.getY(),(int)p.getZ()),SoundEvents.PLAYER_ATTACK_CRIT, SoundSource.PLAYERS);
                                p.getCooldowns().addCooldown(p.getItemInHand(InteractionHand.MAIN_HAND).getItem(),80);
                                damage*=1.5;
                            }
                            if(p.getVehicle()!=null){damage*=1.5;}

                            if(p.getItemInHand(InteractionHand.MAIN_HAND).getEnchantmentLevel(Enchantments.FIRE_ASPECT)>0)
                            {
                                entity.setSecondsOnFire(5);
                            }
                            double level2 = 0;
                            if(entity instanceof Player)
                            {
                                for(ItemStack IS : ((Player)entity).getArmorSlots())
                                {
                                    level2+=IS.getEnchantmentLevel(EnchantmentsRegister.SPEAR_PROTECTION.get());
                                }
                                if(level2 != 0)
                                {
                                    damage /= level2;
                                }
                                System.out.println(level2);
                            }
                            p.sendSystemMessage(Component.literal(String.valueOf(damage)));
                            entity.hurt(entity.damageSources().playerAttack(p),damage);
                            ((ServerLevel)p.level()).playSound(null,new BlockPos((int)p.getX(),(int)p.getY(),(int)p.getZ()),SoundEvents.PLAYER_ATTACK_STRONG, SoundSource.PLAYERS);
                        }
                        p.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTag().putInt("Mode",2);
                        p.getCooldowns().addCooldown(p.getItemInHand(InteractionHand.MAIN_HAND).getItem(),40-p.getItemInHand(InteractionHand.MAIN_HAND).getEnchantmentLevel(EnchantmentsRegister.QUICKCOOL.get())*12);
                        if(!p.isCreative())
                        {
                            p.getItemInHand(InteractionHand.MAIN_HAND).hurt(2, RandomSource.create(),null);
                        }
                    }
                }
            }
        }
        else if(event.getEntity() instanceof Player)
        {
            Player p = (Player)event.getEntity();
            double speed = getAndHandlePlayerSpeed(p);
            FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
            buf.writeUtf(p.getName().getString());
            buf.writeDouble(speed);
            KSpeedUpdatePack kSpeedUpdatePack = new KSpeedUpdatePack(buf);
            ModMessages.sendToServer(kSpeedUpdatePack);
        }
    }
    private static double getAndHandlePlayerSpeed(Player player) {
        // 获取玩家的运动矢量（速度向量）
        Vec3 motion = player.getDeltaMovement();
        // 分别获取X、Y、Z轴的速度分量
        double speedX = motion.x;
        double speedY = motion.y;
        double speedZ = motion.z;
        // 计算水平方向的合速度（忽略Y轴，即上下移动）
        double horizontalSpeed = Math.sqrt(speedX * speedX + speedZ * speedZ+speedY*speedY);
        // 其他使用示例：
        // 判断玩家是否在移动
        return horizontalSpeed;
    }
}
