package com.beastle9end.projectredxt.item;

import com.beastle9end.projectredxt.container.ImprovedBagContainer;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class ImprovedBagItem extends BasicBagItem implements IItemColor {
    private final DyeColor color;

    public ImprovedBagItem(@NotNull final DyeColor color) {
        super(new Properties().stacksTo(1));

        this.color = color;
    }

    @Override
    public @NotNull ITextComponent getDisplayName() {
        return new TranslationTextComponent(String.format("container.projectredxt.improved_bag_%s", color.getName()));
    }

    @Override
    public @NotNull Container createMenu(final int id, @NotNull final PlayerInventory inventory, @NotNull final PlayerEntity player) {
        return new ImprovedBagContainer(id, inventory);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public int getColor(@NotNull final ItemStack stack, final int other) {
        return color.getColorValue();
    }
}
