package com.beastle9end.projectredxt.container.slot;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class SeedBagSlot extends SlotItemHandler {
    public SeedBagSlot(@NotNull final ItemStackHandler handler, int slot, int x, int y) {
        super(handler, slot, x, y);
    }

    @Override
    public boolean mayPlace(@NotNull final ItemStack stack) {
        final Block block = Block.byItem(stack.getItem());
        if (!(block instanceof IPlantable)) {
            return false;
        }
        return true;
    }
}
