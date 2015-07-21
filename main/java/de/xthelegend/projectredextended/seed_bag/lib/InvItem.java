package de.xthelegend.projectredextended.seed_bag.lib;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class InvItem extends InventoryBasic {
    
    private ItemStack    item;
    private EntityPlayer player;
    
    private boolean      reading = false;
    
    public InvItem(EntityPlayer player, ItemStack item, String name, boolean customName, int size) {
    
        super(name, customName, size);
        
        this.player = player;
        this.item = item;
        
        if (!hasInventory()) {
            createInventory();
        }
    }
    
    public static InvItem getItemInventory(ItemStack is, String name, int size) {
    
        return getItemInventory(null, is, name, size);
    }
    
    public static InvItem getItemInventory(EntityPlayer player, ItemStack is, String name, int size) {
    
        return new InvItem(player, is, name, false, size);
    }
    
    public ItemStack getItem() {
    
        return item;
    }
    
    @Override
    public void openInventory() {
    
        loadInventory();
    }
    
    @Override
    public void closeInventory() {
    
        closeInventory(null);
    }
    
    public void closeInventory(ItemStack is) {
    
        saveInventory(is);
    }
    
    private boolean hasInventory() {
    
        if (item.stackTagCompound == null) { return false; }
        return item.stackTagCompound.getTag("Inventory") != null;
    }
    
    private void createInventory() {
    
        writeToNBT();
    }
    
    protected void writeToNBT() {
    
        if (item.stackTagCompound == null) {
            item.stackTagCompound = new NBTTagCompound();
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
        item.stackTagCompound.setTag("Inventory", inventory);
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
    
    protected void setNBT(ItemStack is) {
    
        if (is == null && player != null) {
            is = player.getCurrentEquippedItem();
        }
        
        if (is != null && is.getItem() == this.item.getItem()) {
            is.setTagCompound(item.getTagCompound());
        }
    }
    
    protected void readFromNBT() {
    
        reading = true;
        
        NBTTagList itemList = (NBTTagList) ((NBTTagCompound) item.stackTagCompound.getTag("Inventory")).getTag("Items");
        for (int i = 0; i < itemList.tagCount(); i++) {
            NBTTagCompound slotEntry = itemList.getCompoundTagAt(i);
            int j = slotEntry.getByte("Slot") & 0xff;
            
            if (j >= 0 && j < getSizeInventory()) {
                setInventorySlotContents(j, ItemStack.loadItemStackFromNBT(slotEntry));
            }
        }
        reading = false;
    }
    
    public void saveInventory(ItemStack is) {
    
        writeToNBT();
        setNBT(is);
    }
}