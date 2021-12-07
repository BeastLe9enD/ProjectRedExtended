package com.beastle9end.projectredxt.init;

import com.beastle9end.projectredxt.ProjectRedExtended;
import com.beastle9end.projectredxt.block.*;
import com.beastle9end.projectredxt.client.ClientRegistry;
import com.beastle9end.projectredxt.util.Constants;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.core.Direction;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.PlantType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBlocks {
    public static DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, Constants.MODID);

    public static RegistryObject<BaseFlowerBlock> INDIGO_FLOWER = REGISTRY.register("indigo_flower", () -> new BaseFlowerBlock(MobEffects.LUCK, 5, PlantType.PLAINS, BlockBehaviour.Properties.of(Material.PLANT)
            .noCollission()
            .instabreak()
            .sound(SoundType.GRASS)));
    public static RegistryObject<TallCropsBlock> FLAX_CROP = REGISTRY.register("flax", () -> new TallCropsBlock(BlockBehaviour.Properties.of(Material.PLANT)
            .noCollission()
            .randomTicks()
            .instabreak()
            .sound(SoundType.CROP), ModItems.FLAX_SEEDS, true));
    public static RegistryObject<TallCropsBlock> INDIGO_CROP = REGISTRY.register("indigo", () -> new TallCropsBlock(BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP), ModItems.INDIGO_SEEDS, false));

    public static RegistryObject<BasicCropBlock> RUBY_CROP = REGISTRY.register("ruby", BasicCropBlock::new);
    public static RegistryObject<BasicCropBlock> SAPPHIRE_CROP = REGISTRY.register("sapphire", BasicCropBlock::new);
    public static RegistryObject<BasicCropBlock> PERIDOT_CROP = REGISTRY.register("peridot", BasicCropBlock::new);

    public static RegistryObject<RubberSaplingBlock> RUBBER_SAPLING = REGISTRY.register("rubber_sapling", RubberSaplingBlock::new);
    public static RegistryObject<RotatedPillarBlock> RUBBER_LOG = REGISTRY.register("rubber_log", () -> log(MaterialColor.WOOD, MaterialColor.PODZOL));
    public static RegistryObject<RotatedPillarBlock> RUBBER_WOOD = REGISTRY.register("rubber_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOD)
            .strength(2.0F)
            .sound(SoundType.WOOD)));
    public static RegistryObject<RubberLeavesBlock> RUBBER_LEAVES = REGISTRY.register("rubber_leaves", RubberLeavesBlock::new);

    private static @NotNull RotatedPillarBlock log(@NotNull final MaterialColor topColor, @NotNull final MaterialColor sideColor) {
        return new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD,
                state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? topColor : sideColor).strength(2.0F).sound(SoundType.WOOD));
    }

    @SubscribeEvent
    public static void registerBlockItems(@NotNull final RegistryEvent.Register<Item> event) {
        final IForgeRegistry<Item> registry = event.getRegistry();
        for (final RegistryObject<Block> entry : REGISTRY.getEntries()) {
            final Block block = entry.get();

            if (block instanceof SuppressBlockItem) {
                continue;
            }

            final Item item = new BlockItem(block, new Item.Properties().tab(ProjectRedExtended.CREATIVE_MOD_TAB))
                    .setRegistryName(block.getRegistryName());

            registry.register(item);

            if (block instanceof ItemColor) {
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                    ClientRegistry.addBlockItem((ItemColor) block, item);
                });
            }
        }
    }
}
