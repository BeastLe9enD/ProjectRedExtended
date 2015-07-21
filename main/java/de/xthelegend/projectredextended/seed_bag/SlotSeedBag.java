package de.xthelegend.projectredextended.seed_bag;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;

public class SlotSeedBag extends Slot {
    
    public SlotSeedBag(IInventory par1iInventory, int par2, int par3, int par4) {
    
        super(par1iInventory, par2, par3, par4);
    }
    
    @Override
    public boolean isItemValid(ItemStack itemstack) {
    
        itemstack = itemstack.copy();
        itemstack.stackSize = 1;
        if (itemstack.getItem() instanceof ItemSeeds) {
            ItemStack seedType = null;
            
            for (int i = 0; i < this.inventory.getSizeInventory(); i++) {
                ItemStack is = this.inventory.getStackInSlot(i);
                if (is != null) {
                    seedType = is.copy();
                    seedType.stackSize = 1;
                    break;
                }
            }
            
            if (seedType == null) {
                return true;
            } else {
                return ItemStack.areItemStacksEqual(itemstack, seedType);
            }
        }
        
        return false;
    }
}