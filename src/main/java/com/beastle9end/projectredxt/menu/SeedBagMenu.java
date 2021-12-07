package com.beastle9end.projectredxt.menu;

import com.beastle9end.projectredxt.init.ModMenus;
import com.beastle9end.projectredxt.item.SeedBagItem;
import com.beastle9end.projectredxt.menu.slot.ReadOnlySlot;
import com.beastle9end.projectredxt.menu.slot.SeedBagSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class SeedBagMenu extends BasicItemMenu {

    public SeedBagMenu(final int windowId, @NotNull final Inventory inventory) {
        super(ModMenus.SEED_BAG, windowId, inventory, SeedBagItem.class, 9);

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
    public @NotNull ItemStack quickMoveStack(@NotNull final Player player, int index) {
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