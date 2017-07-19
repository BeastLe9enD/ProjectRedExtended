package de.beastlegend.prextended.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

/**
 * Created by BeastLe9enD on 19.07.2017.
 */
public class InventoryItem extends InventoryBasic {
    private ItemStack item;
    private EntityPlayer player;
    private boolean reading = false;


    public InventoryItem(String title, boolean customName, int slotCount, ItemStack item) {
        super(title, customName, slotCount);
        this.player=player;
        this.item=item;
        if(!hasInventory()) {
            createInventory();
        }
    }

    public static InventoryItem getItemInventory(ItemStack stack, String name, int size) {
        return getItemInventory(null, stack, name, size);
    }

    public static InventoryItem getItemInventory(EntityPlayer player, ItemStack stack, String name, int size) {
        return new InventoryItem(name, false, size, stack);
    }

    public ItemStack getItem() {
        return item;
    }

    @Override
    public void openInventory(EntityPlayer player) {
        loadInventory();
    }

    @Override
    public void closeInventory(EntityPlayer player) {
        closeInventoryStack(null);
    }

    public void closeInventoryStack(ItemStack stack) {
        saveInventory(stack);
    }

    private boolean hasInventory() {
        if(item.getTagCompound() == null) {
            return false;
        }
        return item.getTagCompound().getTag("Inventory") != null;
    }

    private void createInventory() {
        writeToNBT();
    }

    protected void writeToNBT() {
        if (item.getTagCompound() == null) {
            item.writeToNBT(new NBTTagCompound()); //MAYBE?
        }
        NBTTagList itemList = new NBTTagList();
        for (int i = 0; i < getSizeInventory(); i++) {
            if (getStackInSlot(i) != null) {
                NBTTagCompound slotEntry = new NBTTagCompound();
                slotEntry.setByte("Slot", (byte) i);
                getStackInSlot(i).writeToNBT(slotEntry);
                itemList.appendTag(slotEntry);
            }
        }
        NBTTagCompound inventory = new NBTTagCompound();
        inventory.setTag("Items", itemList);
        item.getTagCompound().setTag("Inventory", inventory);
    }

    public void loadInventory() {
        readFromNBT();
    }

    @Override
    public void markDirty() {
        super.markDirty();
        if (!reading) {
            saveInventory(null);
        }
    }

    protected void setNBT(ItemStack stack) {
        if (stack == null && player != null) {
            stack = player.getActiveItemStack(); // MAYBE?
        }

        if (stack != null && stack.getItem() == this.item.getItem()) {
            stack.setTagCompound(item.getTagCompound());
        }
    }

    protected void readFromNBT() {
        reading = true;
        NBTTagList itemList = (NBTTagList) ((NBTTagCompound) item.getTagCompound().getTag("Inventory")).getTag("Items");
        for (int i = 0; i < itemList.tagCount(); i++) {
            NBTTagCompound slotEntry = itemList.getCompoundTagAt(i);
            int j = slotEntry.getByte("Slot") & 0xff;
            if (j >= 0 && j < getSizeInventory()) {
                setInventorySlotContents(j, ItemStack.loadItemStackFromNBT(slotEntry));
            }
        }
        reading = false;
    }

    public void saveInventory(ItemStack stack) {
        writeToNBT();
        setNBT(stack);
    }

}
