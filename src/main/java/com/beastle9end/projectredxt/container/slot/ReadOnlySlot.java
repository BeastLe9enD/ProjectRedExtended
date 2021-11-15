package com.beastle9end.projectredxt.container.slot;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import org.jetbrains.annotations.NotNull;

public class ReadOnlySlot extends Slot {
    public ReadOnlySlot(@NotNull final IInventory inventory, final int slot, final int x, final int y) {
        super(inventory, slot, x, y);
    }

    @Override
    public boolean mayPickup(@NotNull final PlayerEntity player) {
        return false;
    }
}
