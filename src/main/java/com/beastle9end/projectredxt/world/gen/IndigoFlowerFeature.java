package com.beastle9end.projectredxt.world.gen;

import com.beastle9end.projectredxt.block.BaseFlowerBlock;
import com.beastle9end.projectredxt.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class IndigoFlowerFeature extends Feature<IndigoFlowerConfig> {
    public IndigoFlowerFeature() {
        super(IndigoFlowerConfig.CODEC);
    }

    @Override
    public boolean place(@NotNull final FeaturePlaceContext<IndigoFlowerConfig> context) {
        final WorldGenLevel level = context.level();
        final Random random = context.random();
        final BlockPos origin = context.origin();
        final IndigoFlowerConfig config = context.config();

        final BaseFlowerBlock flower = ModBlocks.INDIGO_FLOWER.get();

        if (random.nextInt(config.getChance()) > 0) {
            return false;
        }

        int numPlaced = 0;

        for (int i = 0; i < config.getCount(); i++) {
            final int x = origin.getX() + random.nextInt(16);
            final int z = origin.getZ() + random.nextInt(16);
            final int y = level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z);

            final BlockPos targetPos = new BlockPos(x, y, z);
            final BlockState targetState = level.getBlockState(targetPos);

            if (level.isEmptyBlock(targetPos) && level.canSeeSky(targetPos)
                    && (!level.dimensionType().hasCeiling() || targetPos.getY() < 127)
                    && flower.canSurvive(targetState, level, targetPos)) {
                level.setBlock(targetPos, flower.defaultBlockState(), Block.UPDATE_CLIENTS);
                numPlaced++;
            }
        }
        return numPlaced > 0;
    }
}
