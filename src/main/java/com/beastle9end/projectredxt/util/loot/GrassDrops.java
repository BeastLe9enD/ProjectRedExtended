package com.beastle9end.projectredxt.util.loot;

import com.beastle9end.projectredxt.init.ModItems;
import com.beastle9end.projectredxt.util.Constants;
import com.google.gson.JsonObject;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Mod.EventBusSubscriber(modid = Constants.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GrassDrops {
    private static void register(@NotNull final IForgeRegistry<GlobalLootModifierSerializer<?>> registry, @NotNull final String name,
                                 @NotNull final RegistryObject<? extends Item> item) {
        registry.register(new CropDropSerializer(item).setRegistryName(Constants.MODID, name));
    }

    @SubscribeEvent
    public static void registerModifiers(@NotNull final RegistryEvent.Register<GlobalLootModifierSerializer<?>> event) {
        final IForgeRegistry<GlobalLootModifierSerializer<?>> registry = event.getRegistry();

        register(registry, "flax_seed_drops", ModItems.FLAX_SEEDS);
        register(registry, "indigo_seed_drops", ModItems.INDIGO_SEEDS);
    }

    private static class CropDropSerializer extends GlobalLootModifierSerializer<CropDropModifier> {
        private final RegistryObject<? extends Item> item;

        public CropDropSerializer(@NotNull final RegistryObject<? extends Item> item) {
            this.item = item;
        }

        @Override
        public CropDropModifier read(@NotNull final ResourceLocation location, @NotNull final JsonObject object, @NotNull final ILootCondition[] conditions) {
            return new CropDropModifier(item, conditions);
        }

        @Override
        public @NotNull JsonObject write(@NotNull final CropDropModifier instance) {
            return new JsonObject();
        }
    }

    private static class CropDropModifier extends LootModifier {
        private final RegistryObject<? extends Item> item;

        protected CropDropModifier(@NotNull final RegistryObject<? extends Item> item, @NotNull final ILootCondition[] conditions) {
            super(conditions);

            this.item = item;
        }

        @Override
        protected @NotNull List<ItemStack> doApply(@NotNull final List<ItemStack> generatedLoot, @NotNull final LootContext context) {
            generatedLoot.add(new ItemStack(item.get()));
            return generatedLoot;
        }
    }
}
