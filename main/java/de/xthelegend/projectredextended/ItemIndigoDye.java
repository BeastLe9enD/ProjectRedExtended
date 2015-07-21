package de.xthelegend.projectredextended;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
public class ItemIndigoDye extends Item {
	public ItemIndigoDye()
	{
		this.setCreativeTab(CreativeTabs.tabDecorations);
        setCreativeTab(MainClass.tab_exploration);
        this.setUnlocalizedName(MainClass.MODID + ":" + "indigo_dye");
        setTextureName(MainClass.MODID + ":" + "indigo_dye");
	}
	
	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity) {
	
		if (entity instanceof EntitySheep) {
			EntitySheep sheep = (EntitySheep) entity;
			if (!sheep.getSheared() && sheep.getFleeceColor() != 4) {
				sheep.setFleeceColor(4);
				--stack.stackSize;
			}
			return true;
		}
		return false;
	}
}	
