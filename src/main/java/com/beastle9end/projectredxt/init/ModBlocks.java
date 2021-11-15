package com.beastle9end.projectredxt.init;

import com.beastle9end.projectredxt.ProjectRedExtended;
import com.beastle9end.projectredxt.block.*;
import com.beastle9end.projectredxt.client.ClientRegistry;
import com.beastle9end.projectredxt.util.Constants;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.potion.Effects;
import net.minecraft.util.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.PlantType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBlocks {
    public static DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, Constants.MODID);

    public static RegistryObject<BaseFlowerBlock> INDIGO_FLOWER = REGISTRY.register("indigo_flower", () -> new BaseFlowerBlock(Effects.LUCK, 5, PlantType.PLAINS, AbstractBlock.Properties.of(Material.PLANT)
            .noCollission()
            .instabreak()
            .sound(SoundType.GRASS)));
    public static RegistryObject<TallCropsBlock> FLAX_CROP = REGISTRY.register("flax", () -> new TallCropsBlock(AbstractBlock.Properties.of(Material.PLANT)
            .noCollission()
            .randomTicks()
            .instabreak()
            .sound(SoundType.CROP), ModItems.FLAX_SEEDS, true));
    public static RegistryObject<TallCropsBlock> INDIGO_CROP = REGISTRY.register("indigo", () -> new TallCropsBlock(AbstractBlock.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP), ModItems.INDIGO_SEEDS, false));

    public static RegistryObject<BasicCropsBlock> RUBY_CROP = REGISTRY.register("ruby", BasicCropsBlock::new);
    public static RegistryObject<BasicCropsBlock> SAPPHIRE_CROP = REGISTRY.register("sapphire", BasicCropsBlock::new);
    public static RegistryObject<BasicCropsBlock> PERIDOT_CROP = REGISTRY.register("peridot", BasicCropsBlock::new);

    public static RegistryObject<RubberSaplingBlock> RUBBER_SAPLING = REGISTRY.register("rubber_sapling", RubberSaplingBlock::new);
    public static RegistryObject<RotatedPillarBlock> RUBBER_LOG = REGISTRY.register("rubber_log", () -> log(MaterialColor.WOOD, MaterialColor.PODZOL));
    public static RegistryObject<RotatedPillarBlock> RUBBER_WOOD = REGISTRY.register("rubber_wood", () -> new RotatedPillarBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD)
            .strength(2.0F)
            .sound(SoundType.WOOD)));
    public static RegistryObject<RubberLeavesBlock> RUBBER_LEAVES = REGISTRY.register("rubber_leaves", RubberLeavesBlock::new);

    private static @NotNull RotatedPillarBlock log(@NotNull final MaterialColor topColor, @NotNull final MaterialColor sideColor) {
        return new RotatedPillarBlock(AbstractBlock.Properties.of(Material.WOOD,
                state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? topColor : sideColor).strength(2.0F).sound(SoundType.WOOD));
    }

    @SubscribeEvent
    public static void registerBlockItems(@NotNull final RegistryEvent.Register<Item> event) {
        final IForgeRegistry<Item> registry = event.getRegistry();
        for (final RegistryObject<Block> entry : REGISTRY.getEntries()) {
            final Block block = entry.get();

            if (block instanceof ISuppressBlockItem) {
                continue;
            }

            final Item item = new BlockItem(block, new Item.Properties().tab(ProjectRedExtended.ITEM_GROUP))
                    .setRegistryName(block.getRegistryName());

            registry.register(item);

            if (block instanceof IItemColor) {
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                    ClientRegistry.addBlockItem((IItemColor) block, item);
                });
            }
        }
    }
}
