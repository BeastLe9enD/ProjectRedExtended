package com.beastle9end.projectredxt.client;

import com.beastle9end.projectredxt.block.IBlockRenderTypeProvider;
import com.beastle9end.projectredxt.init.ModBlocks;
import com.beastle9end.projectredxt.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

@OnlyIn(Dist.CLIENT)
public class ClientRegistry {
    private static final ArrayList<Pair<IItemColor, Item>> blockItems = new ArrayList<>();

    public static void addBlockItem(@NotNull final IItemColor color, @NotNull final Item item) {
        blockItems.add(Pair.of(color, item));
    }

    private static void registerBlockColors() {
        final BlockColors colors = Minecraft.getInstance().getBlockColors();
        final Collection<RegistryObject<Block>> entries = ModBlocks.REGISTRY.getEntries();

        for (final RegistryObject<Block> entry : entries) {
            final Block block = entry.get();

            if (!(block instanceof IBlockColorProvider)) {
                continue;
            }

            final IBlockColor color = ((IBlockColorProvider)block).getBlockColor();
            colors.register(color, block);
        }
    }

    private static void registerItemColors() {
        final ItemColors colors = Minecraft.getInstance().getItemColors();
        final Collection<RegistryObject<Item>> entries = ModItems.REGISTRY.getEntries();

        for (final RegistryObject<Item> entry : entries) {
            final Item item = entry.get();

            if (!(item instanceof IItemColorProvider)) {
                continue;
            }

            final IItemColor color = ((IItemColorProvider)item).getItemColor();
            colors.register(color, item);
        }

        for (final Pair<IItemColor, Item> blockItem : blockItems) {
            colors.register(blockItem.getLeft(), blockItem.getRight());
        }
    }

    private static void registerRenderTypes() {
        final Collection<RegistryObject<Block>> entries = ModBlocks.REGISTRY.getEntries();

        for (final RegistryObject<Block> entry : entries) {
            final Block block = entry.get();
            if (!(block instanceof IBlockRenderTypeProvider)) {
                continue;
            }

            RenderTypeLookup.setRenderLayer(block, ((IBlockRenderTypeProvider) block).getRenderType());
        }
    }

    public static void register() {
        registerRenderTypes();
        registerBlockColors();
        registerItemColors();
    }
}