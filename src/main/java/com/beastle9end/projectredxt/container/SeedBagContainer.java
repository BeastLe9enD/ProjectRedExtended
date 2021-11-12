package com.beastle9end.projectredxt.container;

import com.beastle9end.projectredxt.container.slot.ReadOnlySlot;
import com.beastle9end.projectredxt.container.slot.SeedBagSlot;
import com.beastle9end.projectredxt.init.ModContainers;
import com.beastle9end.projectredxt.item.SeedBagItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class SeedBagContainer extends BasicItemContainer {

    public SeedBagContainer(final int windowId, @NotNull final PlayerInventory inventory) {
        super(ModContainers.SEED_BAG, windowId, inventory, SeedBagItem.class, 9);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                addSlot(new SeedBagSlot(handler, i * 3 + j, j * 18 + 62, i * 18 + 17));
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlot(new Slot(inventory, j + i * 9 + 9, j * 18 + 8, i * 18 + 84));
            }
        }

        for (int i = 0; i < 9; i++) {
            if (inventory.selected == i) {
                addSlot(new ReadOnlySlot(inventory, i, i * 18 + 8, 142));
            } else {
                addSlot(new Slot(inventory, i, i * 18 + 8, 142));
            }
        }
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull final PlayerEntity player, int index) {
        ItemStack stack = ItemStack.EMPTY;
        final Slot slot = slots.get(index);

        if (slot != null && slot.hasItem()) {
            final ItemStack nextStack = slot.getItem();
            stack = nextStack.copy();

            if (index < 9) {
                if (!moveItemStackTo(nextStack, 9, 45, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!moveItemStackTo(nextStack, 0, 9, false)) {
                return ItemStack.EMPTY;
            }

            if (nextStack.getCount() == 0) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (nextStack.getCount() == stack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onQuickCraft(stack, nextStack);
        }

        return stack;
    }
}