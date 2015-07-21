package de.xthelegend.projectredextended.lib;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class istutils {

    public static boolean isItemFuzzyEqual(ItemStack stack1, ItemStack stack2) {

        if (isSameOreDictStack(stack1, stack2)) {
            return true;
        }
        if (stack1.getItem() != stack2.getItem())
            return false;
        return stack1.getItemDamage() == stack2.getItemDamage() || stack1.getItemDamage() == OreDictionary.WILDCARD_VALUE
                || stack2.getItemDamage() == OreDictionary.WILDCARD_VALUE;
    }

    public static boolean isSameOreDictStack(ItemStack stack1, ItemStack stack2) {

        int ids[] = OreDictionary.getOreIDs(stack1);
        for (int id : ids) {
            String name = OreDictionary.getOreName(id);
            List<ItemStack> oreDictStacks = OreDictionary.getOres(name);
            for (ItemStack oreDictStack : oreDictStacks) {
                if (OreDictionary.itemMatches(stack2, oreDictStack, false)) {
                    return true;
                }
            }
        }
        return false;
    }


}
