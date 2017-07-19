package de.beastlegend.prextended.util;

/**
 * Created by BeastLe9enD on 19.07.2017.
 */
import net.minecraft.item.ItemStack;


public interface IAlloyFurnaceRecipe {
    boolean matches(ItemStack[] input);
    void useItems(ItemStack[] input);
    ItemStack getCraftingResult(ItemStack[] input);
}