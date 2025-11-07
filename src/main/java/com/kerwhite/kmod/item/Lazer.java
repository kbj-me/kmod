package com.kerwhite.kmod.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;


public class Lazer extends Item
{
    public Lazer()
    {
        super(new Properties());
    }
    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand p_41434_)
    {
        if (!level.isClientSide())
        {
            Vec3 see = player.getEyePosition();
            Vec3 see_1 = player.getLookAngle().normalize();
            for(int i = 0;i<10;i++)
            {
                ClipContext context = new ClipContext(see,see.add(see_1),ClipContext.Block.COLLIDER,ClipContext.Fluid.NONE,null);
                BlockHitResult hitResult = level.clip(context);
                if(hitResult.getType() == HitResult.Type.BLOCK)
                {
                    Direction direction = hitResult.getDirection();
                    double bc = player.getDirection() == Direction.EAST || player.getDirection() == Direction.WEST?Math.abs(player.getEyePosition().x() - see.add(see_1).x()):Math.abs(player.getEyePosition().z() - see.add(see_1).z());
                    double ac = Math.abs(player.getEyePosition().y - see.add(see_1).y);
                    double yo = Math.atan(bc/ac)*(180/Math.PI);
                    double de = Math.abs(player.getEyePosition().x - see.add(see_1).x);
                    double df = Math.abs(player.getEyePosition().z - see.add(see_1).z);
                    double xo = player.getDirection() == Direction.EAST || player.getDirection() == Direction.WEST?Math.atan(de/df)*(180/Math.PI):Math.atan(df/de)*(180/Math.PI);
                    if(direction == Direction.UP || direction == Direction.DOWN)
                    {

                    }
                    else
                    {

                    }
                    System.out.println("xo:"+xo);
                    System.out.println("yo:"+yo);
                    Vec3 worldHitPos = hitResult.getLocation();
                    BlockPos hitPos = hitResult.getBlockPos();
                    double localX = worldHitPos.x() - hitPos.getX();
                    double localY = worldHitPos.y() - hitPos.getY();
                    double localZ = worldHitPos.z() - hitPos.getZ();
                    String side = "";
                    switch (hitResult.getDirection())
                    {
                        case NORTH: // 北面（Z-方向），方块本地Z=0的面
                            // 以方块X轴中心（0.5）为界：X < 0.5 为左，X > 0.5 为右
                            side = localX < 0.5 ? "右半边" : "左半边";
                            break;
                        case SOUTH: // 南面（Z+方向），方块本地Z=1的面
                            // 视角相反，X < 0.5 为右，X > 0.5 为左（与北面相反）
                            side = localX < 0.5 ? "左半边" : "右半边";
                            break;
                        case WEST: // 西面（X-方向），方块本地X=0的面
                            // 以方块Z轴中心（0.5）为界：Z < 0.5 为左，Z > 0.5 为右
                            side = localZ < 0.5 ? "左半边" : "右半边";
                            break;
                        case EAST: // 东面（X+方向），方块本地X=1的面
                            // 视角相反，Z < 0.5 为右，Z > 0.5 为左（与西面相反）
                            side = localZ < 0.5 ? "右半边" : "左半边";
                            break;
                        case UP: // 顶面（Y+方向），方块本地Y=1的面
                            // 以方块X轴中心（0.5）为界（左右按玩家视角，这里简化为X轴）
                            if(player.getDirection() == Direction.EAST || player.getDirection() == Direction.WEST)
                            {
                                side = localZ < 0.5 ? "左半边" : "右半边";
                            }
                            else
                            {
                                side = localX < 0.5 ? "左半边" : "右半边";
                            }

                            break;
                        case DOWN: // 底面（Y-方向），方块本地Y=0的面
                            // 与顶面相反
                            if(player.getDirection() == Direction.EAST || player.getDirection() == Direction.WEST)
                            {
                                side = localZ < 0.5 ?  "左半边":"右半边";
                            }
                            else
                            {
                                side = localX < 0.5 ?  "左半边":"右半边";
                            }

                            break;
                    }
                   // System.out.println("击中方块的" + side);
                    see = see.add(see_1.scale(0.5));
                    ((ServerLevel)level).sendParticles(ParticleTypes.WITCH,see.x,see.y,see.z,10,0.0,0.0,0.0,0.0);
                    break;
                }
                see = see.add(see_1);
                ((ServerLevel)level).sendParticles(ParticleTypes.WITCH,see.x,see.y,see.z,10,0.0,0.0,0.0,0.0);
            }
        }
        return super.use(level,player, p_41434_);
    }
}
