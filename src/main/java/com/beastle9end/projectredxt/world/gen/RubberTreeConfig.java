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
            Codec.INT.fieldOf("max_snake_length").forGetter(RubberTreeConfig::getMaxSnakeLength)
    ).apply(it, RubberTreeConfig::new));

    private final int minHeight;
    private final int maxHeight;
    private final int numSnakes;
    private final int minSnakeHeight;
    private final int maxSnakeHeight;
    private final int minSnakeLength;
    private final int maxSnakeLength;

    public RubberTreeConfig(final int minHeight, final int maxHeight, final int numSnakes, final int minSnakeHeight, final int maxSnakeHeight,
                            final int minSnakeLength, final int maxSnakeLength) {
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
        this.numSnakes = numSnakes;
        this.minSnakeHeight = minSnakeHeight;
        this.maxSnakeHeight = maxSnakeHeight;
        this.minSnakeLength = minSnakeLength;
        this.maxSnakeLength = maxSnakeLength;
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
}
