package com.beastle9end.projectredxt.init;

import com.beastle9end.projectredxt.client.gui.screen.ImprovedBagScreen;
import com.beastle9end.projectredxt.client.gui.screen.SeedBagScreen;
import com.beastle9end.projectredxt.container.ImprovedBagContainer;
import com.beastle9end.projectredxt.container.SeedBagContainer;
import com.beastle9end.projectredxt.util.Constants;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModContainers {
    public static ContainerType<SeedBagContainer> SEED_BAG;
    public static ContainerType<ImprovedBagContainer> IMPROVED_BAG;

    @SuppressWarnings("unchecked")
    private static <T extends Container> ContainerType<T> registerType(@NotNull final IForgeRegistry<ContainerType<?>> registry,
                                                                       @NotNull final String name,
                                                                       @NotNull final ContainerType.IFactory<T> factory) {
        final ContainerType<T> type = (ContainerType<T>) new ContainerType<>(factory).setRegistryName(new ResourceLocation(Constants.MODID, name));
        registry.register(type);
        return type;
    }

    @SubscribeEvent
    public static void registerContainers(@NotNull final RegistryEvent.Register<ContainerType<?>> event) {
        final IForgeRegistry<ContainerType<?>> registry = event.getRegistry();

        SEED_BAG = registerType(registry, "seed_bag", SeedBagContainer::new);
        IMPROVED_BAG = registerType(registry, "improved_bag", ImprovedBagContainer::new);
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerScreens() {
        ScreenManager.register(SEED_BAG, SeedBagScreen::new);
        ScreenManager.register(IMPROVED_BAG, ImprovedBagScreen::new);
    }
}
