package de.xthelegend.projectredextended.debug;

import de.xthelegend.projectredextended.MainClass;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class Namefinder extends Item{
	public Namefinder()
	{
		setUnlocalizedName("namefinder");
		setTextureName(null);
		setCreativeTab(MainClass.tabProjectExtended);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer entityplayer)
    {
		
        return stack;
    }
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer entityplayer, World wworld, int x, int y, int z, int meta, float f, float f1, float f2)
    {
		Block block = wworld.getBlock(x, y, z);
		
		try {
			MainClass.serverConfigManager.sendChatMsg(new ChatComponentText("BlockInfo:"
					+ "BlockUnlocalizedName:" + block.getUnlocalizedName()
					+ "BlockLocalizedName:" + block.getLocalizedName()));
					
		    Thread.sleep(1);                
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
        return false;
    }
	
}
