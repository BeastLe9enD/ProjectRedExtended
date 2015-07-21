package de.xthelegend.projectredextended.alloy_furnace;

import net.minecraft.item.ItemStack;


public interface IAlloyFurnaceRecipe {
    

    boolean matches(ItemStack[] input);
    
 
    void useItems(ItemStack[] input);
    
    ItemStack getCraftingResult(ItemStack[] input);
}