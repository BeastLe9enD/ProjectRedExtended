package de.xthelegend.projectredextended.project_table;

import invtweaks.api.container.ChestContainer;
import invtweaks.api.container.ContainerSection;
import invtweaks.api.container.ContainerSectionCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.common.util.ForgeDirection;


import de.xthelegend.projectredextended.lib.IOHelper;
import de.xthelegend.projectredextended.project_table.TileProjectTable;


import cpw.mods.fml.common.Optional;


@ChestContainer
public class ContainerProjectTable extends Container {

    public final IInventory craftResult = new InventoryCraftResult();
    private final TileProjectTable projectTable;
    private final InventoryCrafting craftingGrid;
    private int itemsCrafted;
    private boolean isRetrying = false;

    public ContainerProjectTable(InventoryPlayer invPlayer, TileProjectTable projectTable) {

        this.projectTable = projectTable;
        craftingGrid = projectTable.getCraftingGrid(this);
        onCraftMatrixChanged(craftingGrid);

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                addSlotToContainer(new Slot(craftingGrid, j + i * 3, 34 + j * 18, 16 + i * 18));
            }
        }

        addSlotToContainer(new SlotProjectTableCrafting(projectTable, invPlayer.player, craftingGrid, craftResult, 0, 127, 34));

        for (int i = 0; i < 2; ++i) {
            for (int j = 0; j < 9; ++j) {
                addSlotToContainer(new Slot(projectTable, j + i * 9, 8 + j * 18, 79 + i * 18));
            }
        }

        bindPlayerInventory(invPlayer);
    }

    protected void bindPlayerInventory(InventoryPlayer invPlayer) {

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 126 + i * 18));
            }
        }

        for (int j = 0; j < 9; j++) {
            addSlotToContainer(new Slot(invPlayer, j, 8 + j * 18, 184));
        }
    }


    @Override
    public void onCraftMatrixChanged(IInventory p_75130_1_) {

        if (craftingGrid != null)
            craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(craftingGrid, projectTable.getWorldObj()));
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {

        return projectTable.isUseableByPlayer(player);
    }

    @Override
    protected void retrySlotClick(int slot, int p_75133_2_, boolean p_75133_3_, EntityPlayer p_75133_4_) {

        ItemStack stackInSlot = ((Slot) inventorySlots.get(slot)).getStack();
        itemsCrafted += stackInSlot.stackSize;
        isRetrying = true;
        if (slot != 9 || !isLastCraftingOperation() && itemsCrafted < stackInSlot.getMaxStackSize()) {
            slotClick(slot, p_75133_2_, 1, p_75133_4_);
        }
        isRetrying = false;
    }

    private boolean isLastCraftingOperation() {

        for (int i = 0; i < 9; i++) {
            ItemStack stack = craftingGrid.getStackInSlot(i);
            if (stack != null && stack.stackSize == 1 && extractStackFromTable(projectTable, stack, true) == null
                    && (!stack.getItem().hasContainerItem(stack) || stack.getItem().doesContainerItemLeaveCraftingGrid(stack)))
                return true;
        }
        return false;
    }

    public static ItemStack extractStackFromTable(TileProjectTable table, ItemStack stack, boolean simulate) {

        return IOHelper.extract(table, ForgeDirection.UNKNOWN, stack, true, simulate);
    }

    public void clearCraftingGrid() {
        for (int i = 0; i < 9; i++) {
            Slot slot = (Slot) inventorySlots.get(i);
            if (slot.getHasStack()) {
                mergeItemStack(slot.getStack(), 10, 28, false);
                if (slot.getStack().stackSize <= 0)
                    slot.putStack(null);
            }
        }
    }


    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int par2) {

        if (!isRetrying)
            itemsCrafted = 0;

        ItemStack itemstack = null;
        Slot slot = (Slot) inventorySlots.get(par2);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (par2 < 9) {
                if (!mergeItemStack(itemstack1, 10, 28, false))
                    return null;
            } else if (par2 == 9) {
                if (!mergeItemStack(itemstack1, 28, 64, false))
                    return null;
            } else if (par2 < 28) {
                if (!mergeItemStack(itemstack1, 28, 64, false))
                    return null;
            } else {
                if (!mergeItemStack(itemstack1, 10, 28, false))
                    return null;
            }
            if (itemstack1.stackSize == 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }
            if (itemstack1.stackSize != itemstack.stackSize) {
                slot.onPickupFromSlot(player, itemstack1);
            } else {
                return null;
            }
        }
        return itemstack;
    }

    @Optional.Method(modid = de.xthelegend.projectredextended.lib.RequiredImports.INVTWEAKS)
    @ContainerSectionCallback
    public Map<ContainerSection, List<Slot>> getSections() {

        Map<ContainerSection, List<Slot>> sections = new HashMap<ContainerSection, List<Slot>>();
        List<Slot> slotsCraftingIn = new ArrayList<Slot>();
        List<Slot> slotsCraftingOut = new ArrayList<Slot>();
        List<Slot> slotsChest = new ArrayList<Slot>();
        List<Slot> slotsInventory = new ArrayList<Slot>();
        List<Slot> slotsInventoryHotbar = new ArrayList<Slot>();
        for (int i = 0; i < 9; i++) {
            slotsCraftingIn.add(i, (Slot) inventorySlots.get(i));
        }
        slotsCraftingOut.add(0, (Slot) inventorySlots.get(9));
        for (int i = 0; i < 18; i++) {
            slotsChest.add(i, (Slot) inventorySlots.get(i + 9));
        }
        for (int i = 0; i < 27; i++) {
            slotsInventory.add(0, (Slot) inventorySlots.get(i + 27));
        }
        for (int i = 0; i < 9; i++) {
            slotsInventoryHotbar.add(0, (Slot) inventorySlots.get(i + 54));
        }
        sections.put(ContainerSection.CRAFTING_IN, slotsCraftingIn);
        sections.put(ContainerSection.CRAFTING_OUT, slotsCraftingOut);
        sections.put(ContainerSection.CHEST, slotsChest);
        sections.put(ContainerSection.INVENTORY, slotsInventory);
        sections.put(ContainerSection.INVENTORY_HOTBAR, slotsInventoryHotbar);
        return sections;
    }
}