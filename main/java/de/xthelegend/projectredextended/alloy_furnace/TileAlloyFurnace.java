package de.xthelegend.projectredextended.alloy_furnace;


import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;

import de.xthelegend.projectredextended.lib.base.BaseTile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import de.xthelegend.projectredextended.MainClass;


/**
 *
 * @author MineMaarten, Koen Beckers (K4Unl), amadornes
 */

public class TileAlloyFurnace extends BaseTile implements ISidedInventory {

    private boolean isActive;
    public int currentBurnTime;
    public int currentProcessTime;
    public int maxBurnTime;
    private ItemStack[] inventory;
    private ItemStack fuelInventory;
    private ItemStack outputInventory;
    private IAlloyFurnaceRecipe currentRecipe;
    private boolean updatingRecipe = true;

    public TileAlloyFurnace() {

        inventory = new ItemStack[9];
    }

    /*************** BASIC TE FUNCTIONS **************/

    /**
     * This function gets called whenever the world/chunk loads
     */
    @Override
    public void readFromNBT(NBTTagCompound tCompound) {

        super.readFromNBT(tCompound);

        for (int i = 0; i < 9; i++) {
            NBTTagCompound tc = tCompound.getCompoundTag("inventory" + i);
            inventory[i] = ItemStack.loadItemStackFromNBT(tc);
        }
        fuelInventory = ItemStack.loadItemStackFromNBT(tCompound.getCompoundTag("fuelInventory"));
        outputInventory = ItemStack.loadItemStackFromNBT(tCompound.getCompoundTag("outputInventory"));

    }

    /**
     * This function gets called whenever the world/chunk is saved
     */
    @Override
    public void writeToNBT(NBTTagCompound tCompound) {

        super.writeToNBT(tCompound);

        for (int i = 0; i < 9; i++) {
            if (inventory[i] != null) {
                NBTTagCompound tc = new NBTTagCompound();
                inventory[i].writeToNBT(tc);
                tCompound.setTag("inventory" + i, tc);
            }
        }
        if (fuelInventory != null) {
            NBTTagCompound fuelCompound = new NBTTagCompound();
            fuelInventory.writeToNBT(fuelCompound);
            tCompound.setTag("fuelInventory", fuelCompound);
        }

        if (outputInventory != null) {
            NBTTagCompound outputCompound = new NBTTagCompound();
            outputInventory.writeToNBT(outputCompound);
            tCompound.setTag("outputInventory", outputCompound);
        }

    }

    @Override
    public void readFromPacketNBT(NBTTagCompound tag) {

        super.readFromPacketNBT(tag);
        isActive = tag.getBoolean("isActive");

        currentBurnTime = tag.getInteger("currentBurnTime");
        currentProcessTime = tag.getInteger("currentProcessTime");
        maxBurnTime = tag.getInteger("maxBurnTime");
        markForRenderUpdate();
    }

    @Override
    public void writeToPacketNBT(NBTTagCompound tag) {

        super.writeToPacketNBT(tag);
        tag.setInteger("currentBurnTime", currentBurnTime);
        tag.setInteger("currentProcessTime", currentProcessTime);
        tag.setInteger("maxBurnTime", maxBurnTime);
        tag.setBoolean("isActive", isActive);
    }

    /**
     * Function gets called every tick. Do not forget to call the super method!
     */
    @Override
    public void updateEntity() {

        super.updateEntity();

        if (!worldObj.isRemote) {
            setIsActive(currentBurnTime > 0);
            if (isActive) {
                currentBurnTime--;
            }
            if (updatingRecipe) {
                currentRecipe = AlloyFurnaceRegistry.getInstance().getMatchingRecipe(inventory, outputInventory);
                updatingRecipe = false;
            }
            if (currentRecipe != null) {
                if (currentBurnTime <= 0) {
                    if (TileEntityFurnace.isItemFuel(fuelInventory)) {
                        // Put new item in
                        currentBurnTime = maxBurnTime = TileEntityFurnace.getItemBurnTime(fuelInventory) + 1;
                        if (fuelInventory != null) {
                            fuelInventory.stackSize--;
                            if (fuelInventory.stackSize <= 0) {
                                fuelInventory = fuelInventory.getItem().getContainerItem(fuelInventory);
                            }
                        }
                    } else {
                        currentProcessTime = 0;
                    }
                }

                if (++currentProcessTime >= 200) {
                    currentProcessTime = 0;
                    if (outputInventory != null) {
                        outputInventory.stackSize += currentRecipe.getCraftingResult(inventory).stackSize;
                    } else {
                        outputInventory = currentRecipe.getCraftingResult(inventory).copy();
                    }
                    currentRecipe.useItems(inventory);
                    updatingRecipe = true;
                }
            } else {
                currentProcessTime = 0;
            }
        }
    }

    @Override
    protected void redstoneChanged(boolean newValue) {

        // setIsActive(newValue);
    }

    @SideOnly(Side.CLIENT)
    public void setBurnTicks(int _maxBurnTime, int _currentBurnTime) {

        maxBurnTime = _maxBurnTime;
        currentBurnTime = _currentBurnTime;
    }

    public float getBurningPercentage() {

        if (maxBurnTime > 0) {
            return (float) currentBurnTime / (float) maxBurnTime;
        } else {
            return 0;
        }
    }

    public float getProcessPercentage() {

        return (float) currentProcessTime / 200;
    }

    /**
     * ************* ADDED FUNCTIONS *************
     */

    public boolean getIsActive() {

        return isActive;
    }

    public void setIsActive(boolean _isActive) {

        if (_isActive != isActive) {
            isActive = _isActive;
            sendUpdatePacket();
        }
    }

    /**
     * ************ IINVENTORY ****************
     */

    @Override
    public int getSizeInventory() {

        return 9 + 1 + 1; // 9 inventory, 1 fuel, 1 output
    }

    @Override
    public ItemStack getStackInSlot(int var1) {
        updatingRecipe = true;
        if (var1 == 0) {
            return fuelInventory;
        } else if (var1 == 1) {
            return outputInventory;
        } else if (var1 < 11) {
            return inventory[var1 - 2];
        }
        return null;
    }

    @Override
    public ItemStack decrStackSize(int var1, int var2) {

        ItemStack tInventory = getStackInSlot(var1);

        if (tInventory == null) {
            return null;
        }

        ItemStack ret = null;
        if (tInventory.stackSize < var2) {
            ret = tInventory;
            inventory = null;
        } else {
            ret = tInventory.splitStack(var2);
            if (tInventory.stackSize <= 0) {
                if (var1 == 0) {
                    fuelInventory = null;
                } else if (var1 == 1) {
                    outputInventory = null;
                } else {
                    inventory[var1 - 2] = null;
                }
            }
        }

        return ret;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int var1) {

        return getStackInSlot(var1);
    }

    @Override
    public void setInventorySlotContents(int var1, ItemStack itemStack) {

        if (var1 == 0) {
            fuelInventory = itemStack;
        } else if (var1 == 1) {
            outputInventory = itemStack;
        } else {
            inventory[var1 - 2] = itemStack;
        }
        updatingRecipe = true;
    }

    @Override
    public String getInventoryName() {

        return MainClass.BlockAlloyFurnace.getUnlocalizedName();
    }

    @Override
    public boolean hasCustomInventoryName() {

        return false;
    }

    @Override
    public int getInventoryStackLimit() {

        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer var1) {

        // Todo: Some fancy code here that detects whether the player is far
        // away
        return true;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack itemStack) {

        if (slot == 0) {
            return TileEntityFurnace.isItemFuel(itemStack);
        } else if (slot == 1) { // Output slot
            return false;
        } else {
            return true;
        }
    }

    @Override
    public List<ItemStack> getDrops() {

        List<ItemStack> drops = super.getDrops();
        if (fuelInventory != null)
            drops.add(fuelInventory);
        if (outputInventory != null)
            drops.add(outputInventory);
        for (ItemStack stack : inventory)
            if (stack != null)
                drops.add(stack);
        return drops;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {

        return new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack item, int side) {

        return isItemValidForSlot(slot, item);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack item, int side) {

        return slot == 1;
    }
}