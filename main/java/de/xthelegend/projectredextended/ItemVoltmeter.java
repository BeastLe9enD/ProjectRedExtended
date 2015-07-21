package de.xthelegend.projectredextended;


import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
import mrtjp.projectred.api.IBundledTileInteraction;
import mrtjp.projectred.core.IPowerConnectable;
import mrtjp.projectred.core.PowerConductor;
import mrtjp.projectred.core.TCachedPowerConductor;

public class ItemVoltmeter extends Item{
	public ItemVoltmeter()
    {
        super();
        this.setMaxStackSize(1);
        this.setUnlocalizedName(MainClass.MODID + ":" + "voltmeter");
        this.setTextureName((MainClass.MODID + ":" + "voltmeter"));
        this.setCreativeTab(MainClass.tab_expansion);
    }
	
	private boolean measureBlue(EntityPlayer player, World world, int x, int y, int z, int var6)
    {
		
		IPowerConnectable powerConnectable = (IPowerConnectable)world.getTileEntity( x, y, z);

        if (powerConnectable == null)
        {
            return false;
        }
        else
        {
        	PowerConductor powerConnector =  powerConnectable.conductor(var6);
            double readPower = powerConnector.power();
     
            //CoreLib.writeChat(player, String.format("Reading %.2fV %.2fA (%.2fW)", new Object[] {Double.valueOf(readPower), Double.valueOf(powerConnector.Itot), Double.valueOf(readPower * powerConnector.Itot)}));
            return true;
        }
    }
	
	
	private boolean itemUseShared(ItemStack var1, EntityPlayer var2, World var3, int var4, int var5, int var6, int var7)
    {
        return this.measureBlue(var2, var3, var4, var5, var6, var7);
    }

    
	@Override
    public boolean onItemUse(ItemStack var1, EntityPlayer var2, World var3, int var4, int var5, int var6, int var7, float var8, float var9, float var10)
    {
        return var2.isSneaking() ? false : this.itemUseShared(var1, var2, var3, var4, var5, var6, var7);
    }

    @Override
    public boolean onItemUseFirst(ItemStack var1, EntityPlayer var2, World var3, int var4, int var5, int var6, int var7, float var8, float var9, float var10)
    {
        return var3.isRemote ? false : (!var2.isSneaking() ? false : this.itemUseShared(var1, var2, var3, var4, var5, var6, var7));
    }

}
