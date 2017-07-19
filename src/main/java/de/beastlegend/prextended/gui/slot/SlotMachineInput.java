package de.beastlegend.prextended.gui.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by BeastLe9enD on 19.07.2017.
 */
public class SlotMachineInput extends Slot{

    private IInventory inventory;
    public SlotMachineInput(IInventory inventory, int index, int xPosition, int yPosition) {
        super(inventory, index, xPosition, yPosition);
        this.inventory = inventory;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return inventory.isItemValidForSlot(this.getSlotIndex(), stack);
    }
}
