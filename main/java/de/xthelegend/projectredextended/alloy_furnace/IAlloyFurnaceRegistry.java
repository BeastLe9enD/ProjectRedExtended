package de.xthelegend.projectredextended.alloy_furnace;

import net.minecraft.item.ItemStack;


public interface IAlloyFurnaceRegistry {


    void addRecipe(IAlloyFurnaceRecipe recipe);


    void addRecipe(ItemStack output, Object... input);

    
    void addRecyclingRecipe(ItemStack recycledItem, String... blacklist);

    void addRecyclingRecipe(ItemStack recycledItem, ItemStack moltenDownItem, String... blacklist);
}