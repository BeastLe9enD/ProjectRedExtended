package com.beastle9end.projectredxt.block;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class TallCropsBlock extends BushBlock implements BonemealableBlock, BlockRenderTypeProvider, SuppressBlockItem {
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 8);
    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
            Block.box(0.0F, 0.0F, 0.0F, 16.0F, 4.0F, 16.0F),
            Block.box(0.0F, 0.0F, 0.0F, 16.0F, 4.0F, 16.0F),
            Block.box(0.0F, 0.0F, 0.0F, 16.0F, 4.0F, 16.0F),
            Block.box(0.0F, 0.0F, 0.0F, 16.0F, 8.0F, 16.0F),
            Block.box(0.0F, 0.0F, 0.0F, 16.0F, 8.0F, 16.0F),
            Block.box(0.0F, 0.0F, 0.0F, 16.0F, 12.0F, 16.0F),
            Block.box(0.0F, 0.0F, 0.0F, 16.0F, 12.0F, 16.0F),
            Block.box(0.0F, 0.0F, 0.0F, 16.0F, 16.0F, 16.0F),
            Block.box(0.0F, 0.0F, 0.0F, 16.0F, 16.0F, 16.0F)
    };

    private final RegistryObject<ItemNameBlockItem> seedProvider;
    private final boolean needsFertileSoil;

    public TallCropsBlock(@NotNull final Properties properties, @NotNull final RegistryObject<ItemNameBlockItem> seedProvider,
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
    public @NotNull VoxelShape getShape(@NotNull final BlockState state, @NotNull final BlockGetter getter, @NotNull final BlockPos pos, @NotNull final CollisionContext context) {
        return SHAPE_BY_AGE[getAge(state)];
    }

    @Override
    protected boolean mayPlaceOn(@NotNull final BlockState state, @NotNull final BlockGetter getter, @NotNull final BlockPos pos) {
        return state.is(Blocks.FARMLAND);
    }

    @Override
    public boolean isRandomlyTicking(@NotNull final BlockState state) {
        return true;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void randomTick(@NotNull final BlockState state, @NotNull final ServerLevel level, @NotNull final BlockPos pos, @NotNull final Random random) {
        if (!level.isAreaLoaded(pos, 1)) {
            return;
        }

        final BlockPos abovePos = pos.above();
        if (level.getRawBrightness(abovePos, 0) < 9) {
            return;
        }

        final int age = getAge(state);
        final BlockPos belowPos = pos.below();
        if (!level.getBlockState(belowPos).is(Blocks.FARMLAND) || level.getBlockState(belowPos).is(this) || !level.getBlockState(abovePos).isAir()) {
            return;
        }

        if (random.nextInt(29) == 0) {
            level.setBlock(pos, getStateForAge(age + 1), Block.UPDATE_CLIENTS);
        }
        if (age > 6 && level.getBlockState(belowPos).is(Blocks.FARMLAND) && level.getBlockState(abovePos).isAir()) {
            if (age == 7) {
                level.setBlock(abovePos, getStateForAge(8), Block.UPDATE_CLIENTS);
            }
            level.setBlock(pos, getStateForAge(7), Block.UPDATE_LIMIT);
        }
    }

    @Override
    public void destroy(@NotNull final LevelAccessor level, @NotNull final BlockPos pos, @NotNull final BlockState state) {
        if (getAge(state) == 8) {
            level.setBlock(pos.below(), getStateForAge(4), Block.UPDATE_CLIENTS);
        }

        super.destroy(level, pos, state);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public @NotNull RenderType getRenderType() {
        return RenderType.cutoutMipped();
    }

    protected boolean isSuitableFarmland(@NotNull final BlockGetter getter, @NotNull final BlockPos pos) {
        final BlockState state = getter.getBlockState(pos);
        return state.is(Blocks.FARMLAND) && (state.isFertile(getter, pos) || !needsFertileSoil);
    }

    @Override
    public boolean canSurvive(@NotNull final BlockState state, @NotNull final LevelReader reader, @NotNull final BlockPos pos) {
        final BlockPos belowPos = pos.below();

        final BlockState belowState = reader.getBlockState(belowPos);
        if (isSuitableFarmland(reader, belowPos)) return true;

        return belowState.is(this) && getAge(belowState) == 7 && isSuitableFarmland(reader, belowPos.below());
    }

    @Override
    @SuppressWarnings("deprecation")
    public void entityInside(@NotNull final BlockState state, @NotNull final Level level, @NotNull final BlockPos pos, @NotNull final Entity entity) {
        if (entity instanceof Ravager && ForgeEventFactory.getMobGriefingEvent(level, entity)) {
            level.destroyBlock(pos, true, entity);
        }

        super.entityInside(state, level, pos, entity);
    }

    @Override
    @SuppressWarnings("deprecation")
    public ItemStack getCloneItemStack(@NotNull final BlockGetter getter, @NotNull final BlockPos pos, @NotNull final BlockState state) {
        return new ItemStack(seedProvider.get());
    }

    @Override
    public boolean isValidBonemealTarget(@NotNull final BlockGetter getter, @NotNull final BlockPos pos, @NotNull final BlockState state, boolean flag) {
        final int age = getAge(state);
        if (age < 7) return true;
        if (age == 8) return false;

        final BlockPos abovePos = pos.above();
        return getter.getBlockState(abovePos).isAir();
    }

    @Override
    public boolean isBonemealSuccess(@NotNull final Level world, @NotNull final Random random, @NotNull final BlockPos pos, @NotNull final BlockState state) {
        return true;
    }

    protected int getBonemealAgeIncrease(@NotNull final Level level) {
        return level.random.nextInt(2) + 2;
    }

    @Override
    public void performBonemeal(@NotNull final ServerLevel world, @NotNull final Random random, @NotNull final BlockPos pos, @NotNull final BlockState state) {
        final int age = getAge(state);
        final BlockPos abovePos = pos.above();

        if (age == 7) {
            world.setBlock(abovePos, getStateForAge(8), Block.UPDATE_CLIENTS);
            return;
        }

        if (age == 6 && world.getBlockState(abovePos).isAir()) {
            world.setBlock(pos, getStateForAge(7), Block.UPDATE_CLIENTS);
            world.setBlock(abovePos, getStateForAge(8), Block.UPDATE_CLIENTS);
        } else {
            int newAge = age + getBonemealAgeIncrease(world);
            if (newAge > 6) {
                newAge = 6;
            }
            world.setBlock(pos, getStateForAge(newAge), Block.UPDATE_CLIENTS);
        }
    }

    @Override
    protected void createBlockStateDefinition(@NotNull final StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(getAgeProperty());
    }
}
