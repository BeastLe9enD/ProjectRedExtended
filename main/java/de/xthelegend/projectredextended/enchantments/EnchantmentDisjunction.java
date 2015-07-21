package de.xthelegend.projectredextended.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.util.StatCollector;

public class EnchantmentDisjunction extends Enchantment {

    public EnchantmentDisjunction(int par1, int par2) {

        super(par1, par2, EnumEnchantmentType.weapon);
    }

    @Override
    public int getMaxLevel() {

        return 5;
    }

    @Override
    public int getMinEnchantability(int par1) {

        return 5 + (par1 - 1) * 8;
    }

    @Override
    public int getMaxEnchantability(int par1) {

        return getMinEnchantability(par1) + 20;
    }

    @Override
    public String getTranslatedName(int level) {

        return StatCollector.translateToLocal("enchantment.zprojectxtended:disjunction.name") + " "
                + StatCollector.translateToLocal("enchantment.level." + level);
    }
}