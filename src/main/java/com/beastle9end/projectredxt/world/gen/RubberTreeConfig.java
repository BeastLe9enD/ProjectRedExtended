package com.beastle9end.projectredxt.world.gen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class RubberTreeConfig implements IFeatureConfig {
    public static final Codec<RubberTreeConfig> CODEC = RecordCodecBuilder.create(it -> it.group(
            Codec.INT.fieldOf("min_height").forGetter(RubberTreeConfig::getMinHeight),
            Codec.INT.fieldOf("max_height").forGetter(RubberTreeConfig::getMaxHeight),
            Codec.INT.fieldOf("num_snakes").forGetter(RubberTreeConfig::getNumSnakes),
            Codec.INT.fieldOf("min_snake_height").forGetter(RubberTreeConfig::getMinSnakeHeight),
            Codec.INT.fieldOf("max_snake_height").forGetter(RubberTreeConfig::getMaxSnakeHeight),
            Codec.INT.fieldOf("min_snake_length").forGetter(RubberTreeConfig::getMinSnakeLength),
            Codec.INT.fieldOf("max_snake_length").forGetter(RubberTreeConfig::getMaxSnakeLength),
            Codec.DOUBLE.fieldOf("height_increase").forGetter(RubberTreeConfig::getHeightIncrease),
            Codec.DOUBLE.fieldOf("height_decrease").forGetter(RubberTreeConfig::getHeightDecrease),
            Codec.DOUBLE.fieldOf("snake_offset").forGetter(RubberTreeConfig::getSnakeOffset),
            Codec.INT.fieldOf("vine_chance").forGetter(RubberTreeConfig::getVineChance),
            Codec.INT.fieldOf("spawn_rate").forGetter(RubberTreeConfig::getSpawnRate)
    ).apply(it, RubberTreeConfig::new));

    private final int minHeight;
    private final int maxHeight;
    private final int numSnakes;
    private final int minSnakeHeight;
    private final int maxSnakeHeight;
    private final int minSnakeLength;
    private final int maxSnakeLength;
    private final double heightIncrease;
    private final double heightDecrease;
    private final double snakeOffset;
    private final int vineChance;

    private final int spawnRate;

    public RubberTreeConfig(final int minHeight, final int maxHeight, final int numSnakes, final int minSnakeHeight, final int maxSnakeHeight,
                            final int minSnakeLength, final int maxSnakeLength, final double heightIncrease, final double heightDecrease,
                            final double snakeOffset, final int vineChance, final int spawnRate) {
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
        this.numSnakes = numSnakes;
        this.minSnakeHeight = minSnakeHeight;
        this.maxSnakeHeight = maxSnakeHeight;
        this.minSnakeLength = minSnakeLength;
        this.maxSnakeLength = maxSnakeLength;
        this.heightIncrease = heightIncrease;
        this.heightDecrease = heightDecrease;
        this.snakeOffset = snakeOffset;
        this.vineChance = vineChance;
        this.spawnRate = spawnRate;
    }

    public int getMinHeight() {
        return minHeight;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public int getNumSnakes() {
        return numSnakes;
    }

    public int getMinSnakeHeight() {
        return minSnakeHeight;
    }

    public int getMaxSnakeHeight() {
        return maxSnakeHeight;
    }

    public int getMinSnakeLength() {
        return minSnakeLength;
    }

    public int getMaxSnakeLength() {
        return maxSnakeLength;
    }

    public double getHeightIncrease() {
        return heightIncrease;
    }

    public double getHeightDecrease() {
        return heightDecrease;
    }

    public double getSnakeOffset() {
        return snakeOffset;
    }

    public int getVineChance() {
        return vineChance;
    }

    public int getSpawnRate() {
        return spawnRate;
    }
}
