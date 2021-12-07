package com.beastle9end.projectredxt.world.gen;

import com.beastle9end.projectredxt.init.ModBlocks;
import com.beastle9end.projectredxt.util.MathUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class RubberTreeFeature extends Feature<RubberTreeConfig> {
    private static final double STEP_SIZE = 0.25D;
    private static final Direction[] VINE_DIRECTIONS = {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};

    public RubberTreeFeature() {
        super(RubberTreeConfig.CODEC);
    }

    private static boolean isGrassUnderTree(@NotNull final WorldGenLevel level, @NotNull final BlockPos pos) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                final BlockPos currentPos = pos.offset(i, -1, j);
                if (!level.getBlockState(currentPos).is(Blocks.GRASS_BLOCK) && !level.getBlockState(currentPos).is(Blocks.DIRT)) {
                    return false;
                }
            }
        }

        return true;
    }

    private static boolean canPlaceTree(@NotNull final WorldGenLevel level, @NotNull final BlockPos pos, final int treeHeight) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                for (int k = 1; k < treeHeight; k++) {
                    final BlockPos currentPos = pos.offset(i, k, j);
                    if (!level.getBlockState(currentPos).isAir()) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private void generateVines(@NotNull final WorldGenLevel level, @NotNull final Random random, @NotNull final BlockPos min,
                               @NotNull final BlockPos max, @NotNull final BlockState vinesState) {
        for (int i = min.getX(); i < max.getX(); i++) {
            for (int j = min.getY(); j < max.getY(); j++) {
                for (int k = min.getZ(); k < max.getZ(); k++) {
                    final BlockPos currentPos = new BlockPos(i, j, k);

                    if (random.nextInt(100) < 69 && level.getBlockState(currentPos).isAir()) {
                        level.setBlock(new BlockPos(i, j, k), vinesState, Block.UPDATE_CLIENTS);
                    }
                }
            }
        }
    }

    private static @NotNull BlockState vineFromDirection(@NotNull Direction direction) {
        return Blocks.VINE.defaultBlockState().setValue(VineBlock.getPropertyForFace(direction), true);
    }

    private void generateTrunk(@NotNull final WorldGenLevel level, @NotNull final Random random, @NotNull final BlockPos pos, int treeHeight,
                               @NotNull final BlockState logState) {
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                for (int k = 0; k < treeHeight; k++) {
                    level.setBlock(pos.offset(i, k, j), logState, Block.UPDATE_CLIENTS);
                }
            }
        }

        generateVines(level, random, pos.offset(-1, 0, -2), pos.offset(2, treeHeight, -1), vineFromDirection(Direction.SOUTH));
        generateVines(level, random, pos.offset(-1, 0, 2), pos.offset(2, treeHeight, 3), vineFromDirection(Direction.NORTH));
        generateVines(level, random, pos.offset(-2, 0, -1), pos.offset(-1, treeHeight, 2), vineFromDirection(Direction.EAST));
        generateVines(level, random, pos.offset(2, 0, -1), pos.offset(3, treeHeight, 2), vineFromDirection(Direction.WEST));
    }

    private static void generateLeaves(@NotNull final WorldGenLevel level, @NotNull final Random random, @NotNull final BlockPos pos, @NotNull final Block logBlock,
                                       @NotNull final Block woodBlock, @NotNull final BlockState leafState, @NotNull RubberTreeConfig config) {
        for (int j = -1; j <= 1; j++) {
            for (int k = -1; k <= 1; k++) {
                for (int l = -1; l <= 1; l++) {
                    if (j * j + k * k + l * l > 1 && random.nextInt(3) < 2) {
                        continue;
                    }

                    final BlockPos leafPos = pos.offset(j, k, l);
                    final BlockState state = level.getBlockState(leafPos);

                    if (state.is(logBlock) || state.is(woodBlock)) {
                        continue;
                    }

                    level.setBlock(leafPos, leafState, Block.UPDATE_CLIENTS);

                    if (random.nextInt(config.getVineChance()) == 0) {
                        for (final Direction direction : VINE_DIRECTIONS) {
                            final Vec3i normal = direction.getNormal();

                            final BlockPos neighborPos = leafPos.offset(normal.getX(), normal.getY(), normal.getZ());
                            if (level.getBlockState(neighborPos).isAir()) {
                                final int vineLength = random.nextInt(2) + 2;
                                final Direction oppositeDirection = direction.getOpposite();

                                for (int i = 0; i < vineLength; i++) {
                                    final BlockPos vinePos = neighborPos.offset(0, -i, 0);
                                    if (level.getBlockState(vinePos).isAir()) {
                                        level.setBlock(vinePos, vineFromDirection(oppositeDirection), Block.UPDATE_CLIENTS);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean place(@NotNull final FeaturePlaceContext<RubberTreeConfig> context) {
        final WorldGenLevel level = context.level();
        final Random random = context.random();
        BlockPos origin = context.origin();
        final ChunkGenerator generator = context.chunkGenerator();
        final RubberTreeConfig config = context.config();

        final int treeHeight = random.nextInt(config.getMaxHeight() - config.getMinHeight()) + config.getMinHeight();

        final boolean isWorldGen = generator != null;

        if (isWorldGen) {
            origin = new BlockPos(origin.getX(), level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, origin.getX(), origin.getZ()), origin.getZ());

            if (random.nextInt(config.getSpawnRate()) > 0 || !isGrassUnderTree(level, origin)) {
                return false;
            }
        } else if (!canPlaceTree(level, origin, treeHeight)) {
            return false;
        }

        final Block logBlock = ModBlocks.RUBBER_LOG.get();
        final Block woodBlock = ModBlocks.RUBBER_WOOD.get();

        final BlockState logState = logBlock.defaultBlockState();
        final BlockState woodState = woodBlock.defaultBlockState();

        final BlockState leafState = ModBlocks.RUBBER_LEAVES.get().defaultBlockState();

        generateTrunk(level, random, origin, treeHeight, logState);

        final BlockPos snakeSource = origin.offset(0, treeHeight - 2, 0);

        for (int i = -2; i <= 2; i++) {
            for (int j = -2; j <= 2; j++) {
                final int height = random.nextInt(1) + 4;

                for (int k = 0; k < height; k++) {
                    if (random.nextInt(4) == 0) {
                        final BlockPos currentPos = snakeSource.offset(i, k, j);

                        level.setBlock(currentPos, random.nextInt(2) == 0 ? woodState : logState, Block.UPDATE_CLIENTS);
                        generateLeaves(level, random, currentPos, logBlock, woodBlock, leafState, config);
                    }
                }
            }
        }

        final int numSnakes = random.nextInt(2) + config.getNumSnakes() - 1;
        final double snakeOffset = random.nextDouble() * Math.PI * 2.0D;

        for (int i = 0; i < numSnakes; i++) {
            final double angle = 2.0D * Math.PI / numSnakes * i + random.nextDouble() * config.getSnakeOffset() - config.getSnakeOffset() / 2.0D + snakeOffset;

            final int length = random.nextInt(config.getMaxSnakeLength() - config.getMinSnakeLength()) + config.getMinSnakeLength();

            Vec3 v = MathUtils.blockPosToVec3d(snakeSource);
            Vec3 a = Vec3.ZERO;

            double heightIncrease = config.getHeightIncrease();

            Vec3 direction;

            final double sinAngle = Math.sin(angle);
            final double cosAngle = Math.cos(angle);

            while (a.lengthSqr() < length * length) {
                direction = new Vec3(cosAngle, heightIncrease, sinAngle).normalize().scale(STEP_SIZE);

                v = v.add(direction);
                a = a.add(direction);

                final BlockPos currentPos = MathUtils.vec3dToBlockPos(v);
                level.setBlock(currentPos, logState, 2);

                if (random.nextInt(5) == 0) {
                    int offset = random.nextInt(8);

                    final BlockPos randomPos = currentPos.offset((offset & 1), (offset & 2) >> 1, (offset & 4) >> 2);

                    level.setBlock(randomPos, logState, 2);
                    generateLeaves(level, random, randomPos, logBlock, woodBlock, leafState, config);
                }

                generateLeaves(level, random, currentPos, logBlock, woodBlock, leafState, config);

                heightIncrease -= config.getHeightDecrease();
            }
        }

        return true;
    }
}
