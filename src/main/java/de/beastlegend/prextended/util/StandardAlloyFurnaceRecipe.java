package de.beastlegend.prextended.util;

import net.minecraft.item.ItemStack;

/**
 * Created by BeastLe9enD on 19.07.2017.
 */
public class StandardAlloyFurnaceRecipe implements IAlloyFurnaceRecipe {
    private ItemStack craftingResult;
    private ItemStack[] requiredItems;

    public StandardAlloyFurnaceRecipe(ItemStack craftingResult, ItemStack... requiredItems) {
        if (craftingResult == null){
            throw new IllegalArgumentException("Alloy Furnace crafting result can't be null!");
        }

        if (requiredItems.length > 9){
            throw new IllegalArgumentException("There can't be more than 9 crafting ingredients for the Alloy Furnace!");
        }
        for (ItemStack requiredItem : requiredItems) {
            if (requiredItem == null){
                throw new NullPointerException("An Alloy Furnace crafting ingredient can't be null!");
            }

        }
        for (ItemStack stack : requiredItems) {
            for (ItemStack stack2 : requiredItems) {
                if (stack != stack2 && ItemStackUtils.isItemFuzzyEqual(stack, stack2)){
                    throw new IllegalArgumentException("No equivalent Alloy Furnace crafting ingredient can be given twice! This does take OreDict + wildcard values in account.");
                }
            }
        }

        this.craftingResult = craftingResult;
        this.requiredItems = requiredItems;
    }

    @Override
    public boolean matches(ItemStack[] input) {
        for (ItemStack requiredItem : requiredItems) {
            int itemsNeeded = requiredItem.stackSize;
            for (ItemStack inputStack : input) {
                if (inputStack != null && ItemStackUtils.isItemFuzzyEqual(inputStack, requiredItem)) {
                    itemsNeeded -= inputStack.stackSize;
                    if (itemsNeeded <= 0){
                        break;
                    }

                }
            }
            if (itemsNeeded > 0)
                return false;
        }
        return true;
    }

    @Override
    public void useItems(ItemStack[] input) {
        for (ItemStack requiredItem : requiredItems) {
            int itemsNeeded = requiredItem.stackSize;
            for (int i = 0; i < input.length; i++) {
                ItemStack inputStack = input[i];
                if (inputStack != null && ItemStackUtils.isItemFuzzyEqual(inputStack, requiredItem)) {
                    int itemsSubstracted = Math.min(inputStack.stackSize, itemsNeeded);
                    inputStack.stackSize -= itemsSubstracted;
                    if (inputStack.stackSize <= 0){
                        input[i] = null;
                    }

                    itemsNeeded -= itemsSubstracted;
                    if (itemsNeeded <= 0){
                        break;
                    }
                }
            }
            if (itemsNeeded > 0){
                throw new IllegalArgumentException("Alloy Furnace recipe using items, after using still items required?? This is a bug!");
            }
        }
    }

    @Override
    public ItemStack getCraftingResult(ItemStack[] input) {
        return craftingResult;
    }

    public ItemStack[] getRequiredItems() {
        return requiredItems;
    }
}
