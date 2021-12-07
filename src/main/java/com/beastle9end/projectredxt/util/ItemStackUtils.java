package com.beastle9end.projectredxt.util;

import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ItemStackUtils {
    public static @NotNull ItemStack stackWithSize(@NotNull final ItemStack oldStack, final int count) {
        final ItemStack newStack = oldStack.copy();
        newStack.setCount(count);
        return newStack;
    }
}
