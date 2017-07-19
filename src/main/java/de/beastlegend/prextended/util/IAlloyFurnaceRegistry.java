package de.beastlegend.prextended.util;

import net.minecraft.item.ItemStack;

/**
 * Created by BeastLe9enD on 19.07.2017.
 */
public interface IAlloyFurnaceRegistry {
    void addRecipe(IAlloyFurnaceRecipe recipe);


    void addRecipe(ItemStack output, Object... input);


    void addRecyclingRecipe(ItemStack recycledItem, String... blacklist);

    void addRecyclingRecipe(ItemStack recycledItem, ItemStack moltenDownItem, String... blacklist);
}
