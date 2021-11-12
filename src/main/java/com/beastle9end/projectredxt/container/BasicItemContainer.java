package com.beastle9end.projectredxt.container;

import com.beastle9end.projectredxt.util.NBTUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BasicItemContainer extends Container {
    protected final ItemStackHandler handler;
    private final Class<?> itemClass;

    public BasicItemContainer(@Nullable final ContainerType<?> type,
                              final int windowId,
                              @NotNull final PlayerInventory inventory,
                              @NotNull final Class<?> itemClass,
                              final int numSlots) {
        super(type, windowId);

        final ItemStack stack = inventory.player.getMainHandItem();
        handler = NBTUtils.loadStackHandler(stack, numSlots);
        this.itemClass = itemClass;
    }

    @Override
    public boolean stillValid(@NotNull final PlayerEntity player) {
        final ItemStack stack = player.getMainHandItem();
        return !stack.isEmpty() && itemClass.isInstance(stack.getItem());
    }

    @Override
    public void removed(@NotNull PlayerEntity player) {
        NBTUtils.saveStackHandler(player.getMainHandItem(), handler);
        super.removed(player);
    }
}
