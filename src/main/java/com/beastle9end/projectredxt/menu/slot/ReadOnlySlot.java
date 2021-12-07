package com.beastle9end.projectredxt.menu.slot;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.NotNull;

public class ReadOnlySlot extends Slot {
    public ReadOnlySlot(@NotNull final Container container, final int slot, final int x, final int y) {
        super(container, slot, x, y);
    }

    @Override
    public boolean mayPickup(@NotNull final Player player) {
        return false;
    }
}
