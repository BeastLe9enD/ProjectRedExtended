package de.xthelegend.projectredextended.seed_bag.lib;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class LockedSlot extends Slot {
    
    public LockedSlot(IInventory par1iInventory, int par2, int par3, int par4) {
    
        super(par1iInventory, par2, par3, par4);
    }
    
    @Override
    public boolean canTakeStack(EntityPlayer par1EntityPlayer) {
    
        return false;
    }
}