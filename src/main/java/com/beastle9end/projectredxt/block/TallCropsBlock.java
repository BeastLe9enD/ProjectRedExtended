package com.beastle9end.projectredxt.block;

import net.minecraft.block.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.RavagerEntity;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class TallCropsBlock extends BushBlock implements IGrowable, IBlockRenderTypeProvider, ISuppressBlockItem {
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 8);
    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
            VoxelShapes.box(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F),
            VoxelShapes.box(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F),
            VoxelShapes.box(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F),
            VoxelShapes.box(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F),
            VoxelShapes.box(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F),
            VoxelShapes.box(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F),
            VoxelShapes.box(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F),
            VoxelShapes.box(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F),
            VoxelShapes.box(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F)
    };

    private final RegistryObject<BlockNamedItem> seedProvider;
    private final boolean needsFertileSoil;

    public TallCropsBlock(@NotNull final Properties properties, @NotNull final RegistryObject<BlockNamedItem> seedProvider,
                          final boolean needsFertileSoil) {
        super(properties);

        this.seedProvider = seedProvider;
        this.needsFertileSoil = needsFertileSoil;
        registerDefaultState(getStateDefinition().any().setValue(getAgeProperty(), 0));
    }

    protected @NotNull IntegerProperty getAgeProperty() {
        return AGE;
    }

    protected int getAge(@NotNull final BlockState state) {
        return state.getValue(getAgeProperty());
    }

    @NotNull
    protected BlockState getStateForAge(final int age) {
        return defaultBlockState().setValue(getAgeProperty(), age);
    }

    @Override
    @SuppressWarnings("deprecation")
    public @NotNull VoxelShape getShape(@NotNull final BlockState state, @NotNull final IBlockReader reader, @NotNull final BlockPos pos, @NotNull final ISelectionContext ctx) {
        return SHAPE_BY_AGE[getAge(state)];
    }

    @Override
    protected boolean mayPlaceOn(@NotNull final BlockState state, @NotNull final IBlockReader reader, @NotNull final BlockPos pos) {
        return state.is(Blocks.FARMLAND);
    }

    @Override
    public boolean isRandomlyTicking(@NotNull final BlockState state) {
        return true;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void randomTick(@NotNull final BlockState state, @NotNull final ServerWorld world, @NotNull final BlockPos pos, @NotNull final Random random) {
        if (!world.isAreaLoaded(pos, 1)) {
            return;
        }

        final BlockPos abovePos = pos.above();
        if (world.getRawBrightness(abovePos, 0) < 9) {
            return;
        }

        final int age = getAge(state);
        final BlockPos belowPos = pos.below();
        if (!world.getBlockState(belowPos).is(Blocks.FARMLAND) || world.getBlockState(belowPos).is(this) || !world.getBlockState(abovePos).isAir(world, pos)) {
            return;
        }

        if (random.nextInt(29) == 0) {
            world.setBlock(pos, getStateForAge(age + 1), 2);
        }
        if (age > 6 && world.getBlockState(belowPos).is(Blocks.FARMLAND) && world.getBlockState(abovePos).isAir(world, pos)) {
            if (age == 7) {
                world.setBlock(abovePos, getStateForAge(8), 2);
            }
            world.setBlock(pos, getStateForAge(7), 2);
        }
    }

    @Override
    public void destroy(@NotNull final IWorld world, @NotNull final BlockPos pos, @NotNull final BlockState state) {
        if (getAge(state) == 8) {
            world.setBlock(pos.below(), getStateForAge(4), 2);
        }

        super.destroy(world, pos, state);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public @NotNull RenderType getRenderType() {
        return RenderType.cutoutMipped();
    }

    protected boolean isSuitableFarmland(@NotNull final IBlockReader reader, @NotNull final BlockPos pos) {
        final BlockState state = reader.getBlockState(pos);
        return state.is(Blocks.FARMLAND) && (state.isFertile(reader, pos) || !needsFertileSoil);
    }

    @Override
    public boolean canSurvive(@NotNull final BlockState state, @NotNull final IWorldReader reader, @NotNull final BlockPos pos) {
        final BlockPos belowPos = pos.below();

        final BlockState belowState = reader.getBlockState(belowPos);
        if (isSuitableFarmland(reader, belowPos)) return true;

        return belowState.is(this) && getAge(belowState) == 7 && isSuitableFarmland(reader, belowPos.below());
    }

    @Override
    @SuppressWarnings("deprecation")
    public void entityInside(@NotNull final BlockState state, @NotNull final World world, @NotNull final BlockPos pos, @NotNull final Entity entity) {
        if (entity instanceof RavagerEntity && ForgeEventFactory.getMobGriefingEvent(world, entity)) {
            world.destroyBlock(pos, true, entity);
        }

        super.entityInside(state, world, pos, entity);
    }

    @Override
    @SuppressWarnings("deprecation")
    public ItemStack getCloneItemStack(@NotNull final IBlockReader reader, @NotNull final BlockPos pos, @NotNull final BlockState state) {
        return new ItemStack(seedProvider.get());
    }

    @Override
    public boolean isValidBonemealTarget(@NotNull final IBlockReader reader, @NotNull final BlockPos pos, @NotNull final BlockState state, boolean flag) {
        final int age = getAge(state);
        if (age < 7) return true;
        if (age == 8) return false;

        final BlockPos abovePos = pos.above();
        return reader.getBlockState(abovePos).isAir(reader, abovePos);
    }

    @Override
    public boolean isBonemealSuccess(@NotNull final World world, @NotNull final Random random, @NotNull final BlockPos pos, @NotNull final BlockState state) {
        return true;
    }

    protected int getBonemealAgeIncrease(@NotNull final World world) {
        return MathHelper.nextInt(world.random, 2, 4);
    }

    @Override
    public void performBonemeal(@NotNull final ServerWorld world, @NotNull final Random random, @NotNull final BlockPos pos, @NotNull final BlockState state) {
        final int age = getAge(state);
        final BlockPos abovePos = pos.above();

        if (age == 7) {
            world.setBlock(abovePos, getStateForAge(8), 2);
            return;
        }

        if (age == 6 && world.getBlockState(abovePos).isAir(world, pos)) {
            world.setBlock(pos, getStateForAge(7), 2);
            world.setBlock(abovePos, getStateForAge(8), 2);
        } else {
            int newAge = age + getBonemealAgeIncrease(world);
            if (newAge > 6) {
                newAge = 6;
            }
            world.setBlock(pos, getStateForAge(newAge), 2);
        }
    }

    @Override
    protected void createBlockStateDefinition(@NotNull final StateContainer.Builder<Block, BlockState> builder) {
        builder.add(getAgeProperty());
    }
}
