package com.beastle9end.projectredxt.world.gen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class IndigoFlowerConfig implements FeatureConfiguration {
    public static final Codec<IndigoFlowerConfig> CODEC = RecordCodecBuilder.create(it -> it.group(
            Codec.INT.fieldOf("count").forGetter(IndigoFlowerConfig::getCount),
            Codec.INT.fieldOf("chance").forGetter(IndigoFlowerConfig::getChance)
    ).apply(it, IndigoFlowerConfig::new));

    private final int count;
    private final int chance;

    public IndigoFlowerConfig(final int count, final int chance) {
        this.count = count;
        this.chance = chance;
    }

    public int getCount() {
        return count;
    }

    public int getChance() {
        return chance;
    }
}
