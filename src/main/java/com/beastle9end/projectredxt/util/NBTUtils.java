package com.beastle9end.projectredxt.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

public class NBTUtils {
    public static void saveStackHandler(@NotNull ItemStack stack, @NotNull final ItemStackHandler handler) {
        if (!stack.hasTag()) {
            stack.setTag(new CompoundNBT());
        }
        if (stack.getTag() != null) {
            stack.getTag().put("inventory", handler.serializeNBT());
        }
    }

    public static ItemStackHandler loadStackHandler(@NotNull ItemStack stack, int numSlots) {
        final ItemStackHandler handler = new ItemStackHandler(numSlots);
        if (stack.hasTag()) {
            handler.deserializeNBT(stack.getTag().getCompound("inventory"));
        }
        return handler;
    }
}
