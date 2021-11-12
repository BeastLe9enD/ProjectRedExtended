package com.beastle9end.projectredxt.world.gen;

import com.beastle9end.projectredxt.block.BaseFlowerBlock;
import com.beastle9end.projectredxt.init.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class IndigoFlowerFeature extends Feature<IndigoFlowerConfig> {
    public IndigoFlowerFeature() {
        super(IndigoFlowerConfig.CODEC);
    }

    @Override
    public boolean place(@NotNull final ISeedReader reader, @NotNull final ChunkGenerator generator, @NotNull final Random random,
                         @NotNull final BlockPos pos, @NotNull final IndigoFlowerConfig config) {
        final BaseFlowerBlock flower = ModBlocks.INDIGO_FLOWER.get();

        if (random.nextInt(config.getChance()) > 0) {
            return false;
        }

        for (int i = 0; i < config.getCount(); i++) {
            final int x = pos.getX() + random.nextInt(16);
            final int z = pos.getZ() + random.nextInt(16);
            final int y = reader.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, x, z);

            final BlockPos targetPos = new BlockPos(x, y, z);
            final BlockState targetState = reader.getBlockState(targetPos);

            if (reader.isEmptyBlock(targetPos) && reader.canSeeSky(targetPos)
                    && (!reader.dimensionType().hasCeiling() || targetPos.getY() < 127)
                    && flower.canSurvive(targetState, reader, targetPos)) {
                reader.setBlock(targetPos, flower.defaultBlockState(), 2);
            }
        }
        return false;
    }
}
