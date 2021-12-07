package com.beastle9end.projectredxt.block;

import com.beastle9end.projectredxt.init.ModFeatures;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class RubberSaplingBlock extends BushBlock implements BonemealableBlock, BlockRenderTypeProvider {
    public RubberSaplingBlock() {
        super(BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS));
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public @NotNull RenderType getRenderType() {
        return RenderType.cutoutMipped();
    }

    private boolean isValidSaplingConfig(@NotNull final BlockGetter getter, @NotNull final BlockPos pos) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (!getter.getBlockState(pos.offset(i, 0, j)).is(this)) {
                    return false;
                }
            }
        }

        return true;
    }

    private @Nullable BlockPos getTreeBasePos(@NotNull final BlockGetter getter, @NotNull final BlockPos pos) {
        for (int i = -2; i < 1; i++) {
            for (int j = -2; j < 1; j++) {
                final BlockPos basePos = pos.offset(i, 0, j);
                if (isValidSaplingConfig(getter, basePos)) {
                    return basePos.offset(1, 0, 1);
                }
            }
        }

        return null;
    }

    @Override
    public boolean isValidBonemealTarget(@NotNull final BlockGetter getter, @NotNull final BlockPos pos, @NotNull final BlockState state, final boolean other) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(@NotNull final Level level, @NotNull final Random random, @NotNull final BlockPos pos, @NotNull final BlockState state) {
        return getTreeBasePos(level, pos) != null;
    }

    @Override
    public void performBonemeal(@NotNull final ServerLevel level, @NotNull final Random random, @NotNull final BlockPos pos, @NotNull final BlockState state) {
        final BlockPos basePos = getTreeBasePos(level, pos);
        if (basePos == null) return;

        ModFeatures.RUBBER_TREE_CONFIGURED.place(level, null, random, basePos);
    }
}
