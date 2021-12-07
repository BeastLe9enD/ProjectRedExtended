package com.beastle9end.projectredxt.init;

import com.beastle9end.projectredxt.ProjectRedExtended;
import com.beastle9end.projectredxt.item.BasicItem;
import com.beastle9end.projectredxt.item.ImprovedBagItem;
import com.beastle9end.projectredxt.item.SeedBagItem;
import com.beastle9end.projectredxt.util.Constants;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Supplier;

public class ModItems {
    public static DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MODID);

    public static RegistryObject<ItemNameBlockItem> FLAX_SEEDS = REGISTRY.register("flax_seeds", () -> new ItemNameBlockItem(ModBlocks.FLAX_CROP.get(), new Item.Properties().tab(ProjectRedExtended.CREATIVE_MOD_TAB)));
    public static RegistryObject<ItemNameBlockItem> INDIGO_SEEDS = REGISTRY.register("indigo_seeds", () -> new ItemNameBlockItem(ModBlocks.INDIGO_CROP.get(), new Item.Properties().tab(ProjectRedExtended.CREATIVE_MOD_TAB)));
    public static RegistryObject<DyeItem> INDIGO_DYE = REGISTRY.register("indigo_dye", () -> new DyeItem(DyeColor.BLUE, new Item.Properties().tab(ProjectRedExtended.CREATIVE_MOD_TAB)));

    public static RegistryObject<ItemNameBlockItem> RUBY_SEEDS = REGISTRY.register("ruby_seeds", () -> new ItemNameBlockItem(ModBlocks.RUBY_CROP.get(), new Item.Properties().tab(ProjectRedExtended.CREATIVE_MOD_TAB)));
    public static RegistryObject<BasicItem> RUBY_SHARDS = REGISTRY.register("ruby_shards", () -> new BasicItem(new Item.Properties()));

    public static RegistryObject<ItemNameBlockItem> SAPPHIRE_SEEDS = REGISTRY.register("sapphire_seeds", () -> new ItemNameBlockItem(ModBlocks.SAPPHIRE_CROP.get(), new Item.Properties().tab(ProjectRedExtended.CREATIVE_MOD_TAB)));
    public static RegistryObject<BasicItem> SAPPHIRE_SHARDS = REGISTRY.register("sapphire_shards", () -> new BasicItem(new Item.Properties()));

    public static RegistryObject<ItemNameBlockItem> PERIDOT_SEEDS = REGISTRY.register("peridot_seeds", () -> new ItemNameBlockItem(ModBlocks.PERIDOT_CROP.get(), new Item.Properties().tab(ProjectRedExtended.CREATIVE_MOD_TAB)));
    public static RegistryObject<BasicItem> PERIDOT_SHARDS = REGISTRY.register("peridot_shards", () -> new BasicItem(new Item.Properties()));

    public static ArrayList<RegistryObject<SeedBagItem>> SEED_BAGS = registerDyedItems(dyeColor -> () -> new SeedBagItem(dyeColor),
            dyeColor -> String.format("seed_bag_%s", dyeColor.getName()));
    public static ArrayList<RegistryObject<ImprovedBagItem>> IMPROVED_BAGS = registerDyedItems(dyeColor -> () -> new ImprovedBagItem(dyeColor),
            dyeColor -> String.format("improved_bag_%s", dyeColor.getName()));

    private static <T extends Item> @NotNull ArrayList<RegistryObject<T>> registerDyedItems(@NotNull final Function<DyeColor, Supplier<T>> itemFactory,
                                                                                            @NotNull final Function<DyeColor, String> nameFactory) {
        final DyeColor[] colors = DyeColor.values();
        final ArrayList<RegistryObject<T>> result = new ArrayList<>(colors.length);

        for (final DyeColor color : colors) {
            result.add(REGISTRY.register(nameFactory.apply(color), itemFactory.apply(color)));
        }

        return result;
    }
}
