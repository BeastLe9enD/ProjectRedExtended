package com.beastle9end.projectredxt.item;

import com.beastle9end.projectredxt.menu.ImprovedBagMenu;
import com.beastle9end.projectredxt.util.ColorUtils;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class ImprovedBagItem extends BasicBagItem implements ItemColor {
    private final DyeColor color;

    public ImprovedBagItem(@NotNull final DyeColor color) {
        super(new Properties().stacksTo(1));

        this.color = color;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return new TranslatableComponent(String.format("container.projectredxt.improved_bag_%s", color.getName()));
    }

    @Override
    public @NotNull AbstractContainerMenu createMenu(final int id, @NotNull final Inventory inventory, @NotNull final Player player) {
        return new ImprovedBagMenu(id, inventory);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public int getColor(@NotNull final ItemStack stack, final int other) {
        return ColorUtils.rgbColorFromDyeColor(color);
    }
}
