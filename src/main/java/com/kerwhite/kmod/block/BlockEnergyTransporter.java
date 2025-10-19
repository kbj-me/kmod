package com.kerwhite.kmod.block;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import com.kerwhite.kmod.blockentity.BlockEntityEnergyTransporter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import net.minecraft.world.entity.player.Player;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.kerwhite.kmod.register.register.ENERGYTRANSPORTER;
@SuppressWarnings("All")
public class BlockEnergyTransporter extends BaseEntityBlock
{
    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        if(context.getNearestLookingDirection().getOpposite()!= Direction.DOWN && context.getNearestLookingDirection().getOpposite()!= Direction.UP )
        {
            return this.defaultBlockState().setValue(BlockStateProperties.FACING,context.getNearestLookingDirection().getOpposite()).setValue(BlockStateProperties.POWERED,Boolean.FALSE);
        }
        else
        {
            return this.defaultBlockState().setValue(BlockStateProperties.FACING,Direction.WEST).setValue(BlockStateProperties.POWERED,Boolean.FALSE);
        }
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder <Block,BlockState> builder)
    {
        super.createBlockStateDefinition(builder);

        builder.add(BlockStateProperties.FACING);
        builder.add(BlockStateProperties.POWERED);
    }
    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState p_49232_)
    {
        return RenderShape.MODEL;
    }
    @Override
    public BlockEntity newBlockEntity(@Nonnull BlockPos pos, @Nonnull BlockState state)
    {
        return new BlockEntityEnergyTransporter(pos,state);
    }
     public BlockEnergyTransporter()
     {
         super(BlockBehaviour.Properties.of().lightLevel(state -> 15).requiresCorrectToolForDrops().strength(2F, 1.5F));
           //super(BlockBehaviour.Properties.of().noOcclusion().requiresCorrectToolForDrops().strength(2F, 1.5F));
     }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, @NotNull BlockState pState, @NotNull BlockEntityType<T> pBlockEntityType)
    {
        return pLevel.isClientSide ? null : createTickerHelper(pBlockEntityType, ENERGYTRANSPORTER.get(), BlockEntityEnergyTransporter::tick);
    }
    //实体站在方块上
    @Override
    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity)
    {
        super.stepOn(pLevel, pPos, pState, pEntity);
    }



   // @Override
   // public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult trace) {
      //  if (!level.isClientSide) {
       //     BlockEntity be = level.getBlockEntity(pos);
           // if (be instanceof GeneratorBlockEntity) {
               // MenuProvider containerProvider = new MenuProvider() {
              //      @Override
               //     public Component getDisplayName() {
               //         return Component.translatable(SCREEN_TUTORIAL_GENERATOR);
               //     }

                 //   @Override
                 //   public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player playerEntity) {
                 //       return new GeneratorContainer(windowId, playerEntity, pos);
                //    }
             //   };
              //  NetworkHooks.openScreen((ServerPlayer) player, containerProvider, be.getBlockPos());
            //} else {
            //    throw new IllegalStateException(&quot;Our named container provider is missing!&quot;);
           // }
      //  }
        //return InteractionResult.SUCCESS;
   // }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState pState, Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        if (!pLevel.isClientSide())
        {
            //打开menu
            //值得说的就是这里，这里是打开Menu的操作，需要你传入一个试下你了MenuProvider的类，对于我们的blockentitiy当然的实现的，所以可以直接返回。需要将pos位置也传入
            // 当然对于openMenu方法也有重载的不需要pos的方法，自己挑选合适即可
            // 我们调用getmenprovider这方法返回对应的blockentity，这个方法很简单，自己点开查看下吧。
           //pPlayer.openMenu(this.getMenuProvider(pState,pLevel,pPos));
           //pPlayer.sendSystemMessage(Component.literal("www"));
            //滚吧
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }
}
