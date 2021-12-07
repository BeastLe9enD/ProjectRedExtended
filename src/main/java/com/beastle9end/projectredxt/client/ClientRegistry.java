package com.beastle9end.projectredxt.client;

import com.beastle9end.projectredxt.block.BlockRenderTypeProvider;
import com.beastle9end.projectredxt.init.ModBlocks;
import com.beastle9end.projectredxt.init.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

@OnlyIn(Dist.CLIENT)
public class ClientRegistry {
    private static final ArrayList<Pair<ItemColor, Item>> blockItems = new ArrayList<>();

    public static void addBlockItem(@NotNull final ItemColor color, @NotNull final Item item) {
        blockItems.add(Pair.of(color, item));
    }

    private static void registerBlockColors() {
        final BlockColors colors = Minecraft.getInstance().getBlockColors();
        final Collection<RegistryObject<Block>> entries = ModBlocks.REGISTRY.getEntries();

        for (final RegistryObject<Block> entry : entries) {
            final Block block = entry.get();

            if (!(block instanceof BlockColor)) {
                continue;
            }

            colors.register((BlockColor) block, block);
        }
    }

    private static void registerItemColors() {
        final ItemColors colors = Minecraft.getInstance().getItemColors();
        final Collection<RegistryObject<Item>> entries = ModItems.REGISTRY.getEntries();

        for (final RegistryObject<Item> entry : entries) {
            final Item item = entry.get();

            if (!(item instanceof ItemColor)) {
                continue;
            }

            colors.register((ItemColor) item, item);
        }

        for (final Pair<ItemColor, Item> blockItem : blockItems) {
            colors.register(blockItem.getLeft(), blockItem.getRight());
        }
    }

    private static void registerRenderTypes() {
        final Collection<RegistryObject<Block>> entries = ModBlocks.REGISTRY.getEntries();

        for (final RegistryObject<Block> entry : entries) {
            final Block block = entry.get();
            if (!(block instanceof BlockRenderTypeProvider)) {
                continue;
            }

            ItemBlockRenderTypes.setRenderLayer(block, ((BlockRenderTypeProvider) block).getRenderType());
        }
    }

    public static void register() {
        registerRenderTypes();

        registerBlockColors();
        registerItemColors();
    }
}