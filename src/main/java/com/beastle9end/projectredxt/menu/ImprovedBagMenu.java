package com.beastle9end.projectredxt.menu;

import com.beastle9end.projectredxt.init.ModMenus;
import com.beastle9end.projectredxt.item.ImprovedBagItem;
import com.beastle9end.projectredxt.menu.slot.ReadOnlySlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class ImprovedBagMenu extends BasicItemMenu {

    public ImprovedBagMenu(final int windowId, @NotNull final Inventory inventory) {
        super(ModMenus.IMPROVED_BAG, windowId, inventory, ImprovedBagItem.class, 54);

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 9; j++) {
                addSlot(new SlotItemHandler(handler, j + i * 9, 8 + j * 18, 18 + i * 18));
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlot(new Slot(inventory, j + i * 9 + 9, j * 18 + 8, 103 + i * 18 + 37));
            }
        }

        for (int i = 0; i < 9; i++) {
            if (inventory.selected == i) {
                addSlot(new ReadOnlySlot(inventory, i, i * 18 + 8, 161 + 37));
            } else {
                addSlot(new Slot(inventory, i, i * 18 + 8, 161 + 37));
            }
        }
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull final Player player, final int index) {
        ItemStack stack = ItemStack.EMPTY;
        final Slot slot = slots.get(index);

        if (slot != null && slot.hasItem()) {
            final ItemStack nextStack = slot.getItem();
            stack = nextStack.copy();
            if (index < 6 * 9) {
                if (!moveItemStackTo(nextStack, 6 * 9, slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!moveItemStackTo(nextStack, 0, 6 * 9, false)) {
                return ItemStack.EMPTY;
            }

            if (nextStack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return stack;
    }
}
