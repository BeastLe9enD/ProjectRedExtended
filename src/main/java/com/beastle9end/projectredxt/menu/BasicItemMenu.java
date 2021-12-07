package com.beastle9end.projectredxt.menu;

import com.beastle9end.projectredxt.util.NBTUtils;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BasicItemMenu extends AbstractContainerMenu {
    protected final ItemStackHandler handler;
    private final Class<? extends Item> itemClass;

    public BasicItemMenu(@Nullable final MenuType<?> type,
                         final int windowId,
                         @NotNull final Inventory inventory,
                         @NotNull final Class<? extends Item> itemClass,
                         final int numSlots) {
        super(type, windowId);

        final ItemStack stack = inventory.player.getMainHandItem();
        handler = NBTUtils.loadStackHandler(stack, numSlots);
        this.itemClass = itemClass;
    }

    @Override
    public boolean stillValid(@NotNull final Player player) {
        final ItemStack stack = player.getMainHandItem();
        return !stack.isEmpty() && itemClass.isInstance(stack.getItem());
    }

    @Override
    public void removed(@NotNull Player player) {
        NBTUtils.saveStackHandler(player.getMainHandItem(), handler);
        super.removed(player);
    }
}
