package de.beastlegend.prextended.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Created by BeastLe9enD on 19.07.2017.
 */
public class ItemStackHelper {
    public static boolean areItemStacksEqual(ItemStack itemStack1, ItemStack itemStack2) {
        return itemStack1 == null && itemStack2 == null || !(itemStack1 == null || itemStack2 == null) && itemStack1.getItem() == itemStack2.getItem() && itemStack1.getItemDamage() == itemStack2.getItemDamage() && !(itemStack1.getTagCompound() == null && itemStack2.getTagCompound() != null) && (itemStack1.getTagCompound() == null || itemStack1.getTagCompound().equals(itemStack2.getTagCompound()));
    }

    public static boolean areStacksEqual(ItemStack stack1, ItemStack stack2, int mode) {
        if (stack1 == null && stack2 != null){
            return false;
        }

        if (stack1 != null && stack2 == null){
            return false;
        }

        if (stack1 == null && stack2 == null){
            return true;
        }

        if (mode == 0) {
            return OreDictionary.itemMatches(stack1, stack2, false);
        } else if (mode == 1) {
            return ItemStackUtils.isItemFuzzyEqual(stack1, stack2);
        } else {
            return OreDictionary.itemMatches(stack1, stack2, false) && ItemStack.areItemStackTagsEqual(stack1, stack2);
        }
    }
}
