package com.beastle9end.projectredxt.menu.slot;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class SeedBagSlot extends SlotItemHandler {
    public SeedBagSlot(@NotNull final ItemStackHandler handler, final int slot, final int x, final int y) {
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
