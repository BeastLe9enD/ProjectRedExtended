package com.beastle9end.projectredxt.world.gen;

import com.beastle9end.projectredxt.init.ModBlocks;
import com.beastle9end.projectredxt.util.MathUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.VineBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class RubberTreeFeature extends Feature<RubberTreeConfig> {
    private static final double STEP_SIZE = 0.25D;
    private static final Direction[] VINE_DIRECTIONS = {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};

    public RubberTreeFeature() {
        super(RubberTreeConfig.CODEC);
    }

    private static boolean isGrassUnderTree(@NotNull final ISeedReader reader, @NotNull final BlockPos pos) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                final BlockPos currentPos = pos.offset(i, -1, j);
                if (!reader.getBlockState(currentPos).is(Blocks.GRASS_BLOCK) && !reader.getBlockState(currentPos).is(Blocks.DIRT)) {
                    return false;
                }
            }
        }

        return true;
    }

    private static boolean canPlaceTree(@NotNull final ISeedReader reader, @NotNull final BlockPos pos, int treeHeight) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                for (int k = 1; k < treeHeight; k++) {
                    final BlockPos currentPos = pos.offset(i, k, j);
                    if (!reader.getBlockState(currentPos).isAir(reader, currentPos)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private void generateVines(@NotNull final ISeedReader reader, @NotNull final Random random, @NotNull final BlockPos min,
                               @NotNull final BlockPos max, @NotNull final BlockState vinesState) {
        for (int i = min.getX(); i < max.getX(); i++) {
            for (int j = min.getY(); j < max.getY(); j++) {
                for (int k = min.getZ(); k < max.getZ(); k++) {
                    final BlockPos currentPos = new BlockPos(i, j, k);

                    if (random.nextInt(100) < 69 && reader.getBlockState(currentPos).isAir(reader, currentPos)) {
                        reader.setBlock(new BlockPos(i, j, k), vinesState, 2);
                    }
                }
            }
        }
    }

    private static @NotNull BlockState vineFromDirection(@NotNull Direction direction) {
        return Blocks.VINE.defaultBlockState().setValue(VineBlock.getPropertyForFace(direction), true);
    }

    private void generateTrunk(@NotNull final ISeedReader reader, @NotNull final Random random, @NotNull final BlockPos pos, int treeHeight,
                               @NotNull final BlockState logState) {
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                for (int k = 0; k < treeHeight; k++) {
                    reader.setBlock(pos.offset(i, k, j), logState, 2);
                }
            }
        }

        generateVines(reader, random, pos.offset(-1, 0, -2), pos.offset(2, treeHeight, -1), vineFromDirection(Direction.SOUTH));
        generateVines(reader, random, pos.offset(-1, 0, 2), pos.offset(2, treeHeight, 3), vineFromDirection(Direction.NORTH));
        generateVines(reader, random, pos.offset(-2, 0, -1), pos.offset(-1, treeHeight, 2), vineFromDirection(Direction.EAST));
        generateVines(reader, random, pos.offset(2, 0, -1), pos.offset(3, treeHeight, 2), vineFromDirection(Direction.WEST));
    }

    private static void generateLeaves(@NotNull final ISeedReader reader, @NotNull final Random random, @NotNull final BlockPos pos, @NotNull final Block logBlock,
                                       @NotNull final Block woodBlock, @NotNull final BlockState leafState, @NotNull RubberTreeConfig config) {
        for (int j = -1; j <= 1; j++) {
            for (int k = -1; k <= 1; k++) {
                for (int l = -1; l <= 1; l++) {
                    if (j * j + k * k + l * l > 1 && random.nextInt(3) < 2) {
                        continue;
                    }

                    final BlockPos leafPos = pos.offset(j, k, l);
                    final BlockState state = reader.getBlockState(leafPos);

                    if (state.is(logBlock) || state.is(woodBlock)) {
                        continue;
                    }

                    reader.setBlock(leafPos, leafState, 2);

                    if (random.nextInt(config.getVineChance()) == 0) {
                        for (final Direction direction : VINE_DIRECTIONS) {
                            final Vector3i normal = direction.getNormal();

                            final BlockPos neighborPos = leafPos.offset(normal.getX(), normal.getY(), normal.getZ());
                            if (reader.getBlockState(neighborPos).isAir(reader, neighborPos)) {
                                final int vineLength = random.nextInt(2) + 2;
                                final Direction oppositeDirection = direction.getOpposite();

                                for (int i = 0; i < vineLength; i++) {
                                    final BlockPos vinePos = neighborPos.offset(0, -i, 0);
                                    if (reader.getBlockState(vinePos).isAir(reader, vinePos)) {
                                        reader.setBlock(vinePos, vineFromDirection(oppositeDirection), 2);
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
    public boolean place(@NotNull final ISeedReader reader, @Nullable final ChunkGenerator generator,
                         @NotNull final Random random, @NotNull BlockPos pos, @NotNull final RubberTreeConfig config) {
        final int treeHeight = random.nextInt(config.getMaxHeight() - config.getMinHeight()) + config.getMinHeight();

        final boolean isWorldGen = generator != null;

        if (isWorldGen) {
            pos = pos.offset(0, reader.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, pos.getX(), pos.getZ()), 0);

            if (random.nextInt(config.getSpawnRate()) > 0 || !isGrassUnderTree(reader, pos)) {
                return false;
            }
        } else if (!canPlaceTree(reader, pos, treeHeight)) {
            return false;
        }

        final Block logBlock = ModBlocks.RUBBER_LOG.get();
        final Block woodBlock = ModBlocks.RUBBER_WOOD.get();

        final BlockState logState = logBlock.defaultBlockState();
        final BlockState woodState = woodBlock.defaultBlockState();

        final BlockState leafState = ModBlocks.RUBBER_LEAVES.get().defaultBlockState();

        generateTrunk(reader, random, pos, treeHeight, logState);

        final BlockPos snakeSource = pos.offset(0, treeHeight - 2, 0);

        for (int i = -2; i <= 2; i++) {
            for (int j = -2; j <= 2; j++) {
                final int height = random.nextInt(1) + 4;

                for (int k = 0; k < height; k++) {
                    if (random.nextInt(4) == 0) {
                        final BlockPos currentPos = snakeSource.offset(i, k, j);

                        reader.setBlock(currentPos, random.nextInt(2) == 0 ? woodState : logState, 2);
                        generateLeaves(reader, random, currentPos, logBlock, woodBlock, leafState, config);
                    }
                }
            }
        }

        final int numSnakes = random.nextInt(2) + config.getNumSnakes() - 1;
        final double snakeOffset = random.nextDouble() * Math.PI * 2.0D;

        for (int i = 0; i < numSnakes; i++) {
            final double angle = 2.0D * Math.PI / numSnakes * i + random.nextDouble() * config.getSnakeOffset() - config.getSnakeOffset() / 2.0D + snakeOffset;

            final int length = random.nextInt(config.getMaxSnakeLength() - config.getMinSnakeLength()) + config.getMinSnakeLength();

            Vector3d v = MathUtils.blockPosToVec3d(snakeSource);
            Vector3d a = Vector3d.ZERO;

            double heightIncrease = config.getHeightIncrease();

            Vector3d direction;
            while (a.lengthSqr() < length * length) {
                direction = new Vector3d(Math.cos(angle), heightIncrease, Math.sin(angle)).normalize().scale(STEP_SIZE);

                v = v.add(direction);
                a = a.add(direction);

                final BlockPos currentPos = MathUtils.vec3dToBlockPos(v);
                reader.setBlock(currentPos, logState, 2);

                if (random.nextInt(5) == 0) {
                    int offset = random.nextInt(8);

                    final BlockPos randomPos = currentPos.offset((offset & 1), (offset & 2) >> 1, (offset & 4) >> 2);

                    reader.setBlock(randomPos, logState, 2);
                    generateLeaves(reader, random, randomPos, logBlock, woodBlock, leafState, config);
                }

                generateLeaves(reader, random, currentPos, logBlock, woodBlock, leafState, config);

                heightIncrease -= config.getHeightDecrease();
            }
        }

        return true;
    }
}
