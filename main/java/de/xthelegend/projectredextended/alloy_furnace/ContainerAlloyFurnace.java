package de.xthelegend.projectredextended.alloy_furnace;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;



import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.xthelegend.projectredextended.lib.inout.SlotMachineInput;
import de.xthelegend.projectredextended.lib.inout.SlotMachineOutput;

/**
 * @author MineMaarten
 */
public class ContainerAlloyFurnace extends Container {
    
    private final TileAlloyFurnace tileFurnace;
    
    private int                    currentBurnTime;
    private int                    maxBurnTime;
    private int                    currentProcessTime;
    
    public ContainerAlloyFurnace(InventoryPlayer invPlayer, TileAlloyFurnace furnace) {
    
        tileFurnace = furnace;
        
        addSlotToContainer(new SlotMachineInput(furnace, 0, 21, 35));
        addSlotToContainer(new SlotMachineOutput(furnace, 1, 134, 35));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                addSlotToContainer(new SlotMachineInput(furnace, i * 3 + j + 2, 47 + j * 18, 17 + i * 18));
            }
        }
        bindPlayerInventory(invPlayer);
        
    }
    
    protected void bindPlayerInventory(InventoryPlayer invPlayer) {
    
        // Render inventory
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        
        // Render hotbar
        for (int j = 0; j < 9; j++) {
            addSlotToContainer(new Slot(invPlayer, j, 8 + j * 18, 142));
        }
    }
    
    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
    
        ItemStack var3 = null;
        Slot var4 = (Slot) inventorySlots.get(par2);
        
        if (var4 != null && var4.getHasStack()) {
            ItemStack var5 = var4.getStack();
            var3 = var5.copy();
            
            if (par2 < 11) {
                if (!mergeItemStack(var5, 11, 47, false)) return null;
                var4.onSlotChange(var5, var3);
            } else {
                if (TileEntityFurnace.isItemFuel(var5) && mergeItemStack(var5, 0, 1, false)) {
                    
                } else if (!mergeItemStack(var5, 2, 11, false)) return null;
                var4.onSlotChange(var5, var3);
            }
            
            if (var5.stackSize == 0) {
                var4.putStack((ItemStack) null);
            } else {
                var4.onSlotChanged();
            }
            
            if (var5.stackSize == var3.stackSize) return null;
            
            var4.onPickupFromSlot(par1EntityPlayer, var5);
        }
        
        return var3;
    }
    
    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    @Override
    public void detectAndSendChanges() {
    
        super.detectAndSendChanges();
        
        for (Object crafter : crafters) {
            ICrafting icrafting = (ICrafting) crafter;
            
            if (currentBurnTime != tileFurnace.currentBurnTime) {
                icrafting.sendProgressBarUpdate(this, 0, tileFurnace.currentBurnTime);
            }
            
            if (maxBurnTime != tileFurnace.maxBurnTime) {
                icrafting.sendProgressBarUpdate(this, 1, tileFurnace.maxBurnTime);
            }
            
            if (currentProcessTime != tileFurnace.currentProcessTime) {
                icrafting.sendProgressBarUpdate(this, 2, tileFurnace.currentProcessTime);
            }
        }
        
        currentBurnTime = tileFurnace.currentBurnTime;
        maxBurnTime = tileFurnace.maxBurnTime;
        currentProcessTime = tileFurnace.currentProcessTime;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2) {
    
        if (par1 == 0) {
            tileFurnace.currentBurnTime = par2;
        }
        
        if (par1 == 1) {
            tileFurnace.maxBurnTime = par2;
        }
        
        if (par1 == 2) {
            tileFurnace.currentProcessTime = par2;
        }
    }
    
    @Override
    public boolean canInteractWith(EntityPlayer entityplayer) {
    
        return tileFurnace.isUseableByPlayer(entityplayer);
    }
    
}