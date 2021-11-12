package com.beastle9end.projectredxt.init;

import com.beastle9end.projectredxt.ProjectRedExtended;
import com.beastle9end.projectredxt.block.*;
import com.beastle9end.projectredxt.util.Constants;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.potion.Effects;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.PlantType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBlocks {
    public static DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, Constants.MODID);

    public static RegistryObject<BaseFlowerBlock> INDIGO_FLOWER = REGISTRY.register("indigo_flower", () -> new BaseFlowerBlock(Effects.LUCK, 5, PlantType.PLAINS, AbstractBlock.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS)));
    public static RegistryObject<TallCropsBlock> FLAX_CROP = REGISTRY.register("flax", () -> new TallCropsBlock(AbstractBlock.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP), ModItems.FLAX_SEEDS, true));
    public static RegistryObject<TallCropsBlock> INDIGO_CROP = REGISTRY.register("indigo", () -> new TallCropsBlock(AbstractBlock.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP), ModItems.INDIGO_SEEDS, false));

    public static RegistryObject<BasicCropsBlock> RUBY_CROP = REGISTRY.register("ruby", BasicCropsBlock::new);
    public static RegistryObject<BasicCropsBlock> SAPPHIRE_CROP = REGISTRY.register("sapphire", BasicCropsBlock::new);
    public static RegistryObject<BasicCropsBlock> PERIDOT_CROP = REGISTRY.register("peridot", BasicCropsBlock::new);

    @SubscribeEvent
    public static void registerBlockItems(@NotNull final RegistryEvent.Register<Item> event) {
        final IForgeRegistry<Item> registry = event.getRegistry();

        for (final RegistryObject<Block> entry : REGISTRY.getEntries()) {
            final Block block = entry.get();

            if (!(block instanceof ISuppressBlockItem)) {
                registry.register(new BlockItem(block, new Item.Properties().tab(ProjectRedExtended.ITEM_GROUP))
                        .setRegistryName(block.getRegistryName()));
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerRenderTypes() {
        for (final RegistryObject<Block> entry : REGISTRY.getEntries()) {
            final Block block = entry.get();
            if (block instanceof IBlockRenderTypeProvider) {
                RenderTypeLookup.setRenderLayer(block, ((IBlockRenderTypeProvider) block).getRenderType());
            }
        }
    }
}
