package de.beastlegend.prextended.gui.slot;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

/**
 * Created by BeastLe9enD on 19.07.2017.
 */
public class LockedSlot extends Slot {
    public LockedSlot(IInventory inventory, int index, int xPosition, int yPosition) {
        super(inventory, index, xPosition, yPosition);
    }

    @Override
    public boolean canTakeStack(EntityPlayer entityPlayer) {
        return false;
    }
}
