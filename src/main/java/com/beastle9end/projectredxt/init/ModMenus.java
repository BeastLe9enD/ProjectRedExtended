package com.beastle9end.projectredxt.init;

import com.beastle9end.projectredxt.client.gui.screen.ImprovedBagScreen;
import com.beastle9end.projectredxt.client.gui.screen.SeedBagScreen;
import com.beastle9end.projectredxt.menu.ImprovedBagMenu;
import com.beastle9end.projectredxt.menu.SeedBagMenu;
import com.beastle9end.projectredxt.util.Constants;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModMenus {
    public static MenuType<SeedBagMenu> SEED_BAG;
    public static MenuType<ImprovedBagMenu> IMPROVED_BAG;

    @SuppressWarnings("unchecked")
    private static <T extends AbstractContainerMenu> @NotNull MenuType<T> registerType(@NotNull final IForgeRegistry<MenuType<?>> registry,
                                                                                       @NotNull final String name,
                                                                                       @NotNull final MenuType.MenuSupplier<T> supplier) {
        final MenuType<T> type = (MenuType<T>) new MenuType<>(supplier).setRegistryName(new ResourceLocation(Constants.MODID, name));
        registry.register(type);
        return type;
    }

    @SubscribeEvent
    public static void registerMenus(@NotNull final RegistryEvent.Register<MenuType<?>> event) {
        final IForgeRegistry<MenuType<?>> registry = event.getRegistry();

        SEED_BAG = registerType(registry, "seed_bag", SeedBagMenu::new);
        IMPROVED_BAG = registerType(registry, "improved_bag", ImprovedBagMenu::new);
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerScreens() {
        MenuScreens.register(SEED_BAG, SeedBagScreen::new);
        MenuScreens.register(IMPROVED_BAG, ImprovedBagScreen::new);
    }
}
