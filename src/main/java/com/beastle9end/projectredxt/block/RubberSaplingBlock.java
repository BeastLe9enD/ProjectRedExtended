package com.beastle9end.projectredxt.block;

import com.beastle9end.projectredxt.init.ModFeatures;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class RubberSaplingBlock extends BushBlock implements IGrowable, IBlockRenderTypeProvider {
    public RubberSaplingBlock() {
        super(AbstractBlock.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS));
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public @NotNull RenderType getRenderType() {
        return RenderType.cutoutMipped();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public boolean isValidBonemealTarget(@NotNull final IBlockReader reader, @NotNull final BlockPos pos, @NotNull final BlockState state, final boolean other) {
        return getTreeBasePos(reader, pos) != null;
    }

    @Override
    public boolean isBonemealSuccess(@NotNull final World world, @NotNull final Random random, @NotNull final BlockPos pos, @NotNull final BlockState state) {
        return true;
    }

    private boolean isValidSaplingConfig(@NotNull final IBlockReader reader, @NotNull final BlockPos pos) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (!reader.getBlockState(pos.offset(i, 0, j)).is(this)) {
                    return false;
                }
            }
        }

        return true;
    }

    private @Nullable BlockPos getTreeBasePos(@NotNull final IBlockReader reader, @NotNull final BlockPos pos) {
        for (int i = -2; i < 1; i++) {
            for (int j = -2; j < 1; j++) {
                final BlockPos basePos = pos.offset(i, 0, j);
                if (isValidSaplingConfig(reader, basePos)) {
                    return basePos.offset(1, 0, 1);
                }
            }
        }

        return null;
    }

    @Override
    public void performBonemeal(@NotNull final ServerWorld world, @NotNull final Random random, @NotNull final BlockPos pos, @NotNull final BlockState state) {
        final BlockPos basePos = getTreeBasePos(world, pos);
        if (basePos == null) return;

        ModFeatures.RUBBER_TREE_CONFIGURED.place(world, null, random, basePos);
    }
}
