package com.beastle9end.projectredxt.init;

import com.beastle9end.projectredxt.util.Constants;
import com.beastle9end.projectredxt.world.gen.IndigoFlowerConfig;
import com.beastle9end.projectredxt.world.gen.IndigoFlowerFeature;
import com.beastle9end.projectredxt.world.gen.RubberTreeConfig;
import com.beastle9end.projectredxt.world.gen.RubberTreeFeature;
import com.google.common.collect.ImmutableSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModFeatures {
    private static final ImmutableSet<Biome.BiomeCategory> BIOME_BLACKLIST = ImmutableSet.of(
            Biome.BiomeCategory.MUSHROOM, Biome.BiomeCategory.ICY, Biome.BiomeCategory.NETHER, Biome.BiomeCategory.THEEND
    );

    public static final Feature<IndigoFlowerConfig> INDIGO_FLOWER = new IndigoFlowerFeature();
    public static final Feature<RubberTreeConfig> RUBBER_TREE = new RubberTreeFeature();

    public static final ConfiguredFeature<IndigoFlowerConfig, ?> INDIGO_FLOWER_CONFIGURED = INDIGO_FLOWER.configured(new IndigoFlowerConfig(4, 20));
    public static final ConfiguredFeature<RubberTreeConfig, ?> RUBBER_TREE_CONFIGURED = RUBBER_TREE.configured(new RubberTreeConfig(13, 16, 8, 6, 9, 14, 17, 1.45D, 0.0215D, 0.48D, 16, 15));

    private static <T> void addFeature(@NotNull final IForgeRegistry<Feature<?>> registry,
                                       @NotNull final Feature<?> feature,
                                       @NotNull final String name) {
        final ResourceLocation location = new ResourceLocation(Constants.MODID, name);
        registry.register(feature.setRegistryName(location));
    }

    @SubscribeEvent
    public static void registerFeatures(@NotNull final RegistryEvent.Register<Feature<?>> event) {
        final IForgeRegistry<Feature<?>> registry = event.getRegistry();

        addFeature(registry, INDIGO_FLOWER, "indigo_flower_gen");
        addFeature(registry, RUBBER_TREE, "rubber_tree_gen");
    }

    public static void onBiomeLoad(@NotNull final BiomeLoadingEvent event) {
        final Biome.BiomeCategory category = event.getCategory();
        if (!BIOME_BLACKLIST.contains(category)) {
            event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, INDIGO_FLOWER_CONFIGURED.placed());
        }
        if (category == Biome.BiomeCategory.JUNGLE) {
            event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RUBBER_TREE_CONFIGURED.placed());
        }
    }
}
