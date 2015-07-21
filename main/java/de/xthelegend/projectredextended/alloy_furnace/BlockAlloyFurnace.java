package de.xthelegend.projectredextended.alloy_furnace;


import java.util.Random;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.xthelegend.projectredextended.lib.base.BaseContainer;

import de.xthelegend.projectredextended.MainClass;
import de.xthelegend.projectredextended.lib.base.GUI_NUMBER_IDS;


public class BlockAlloyFurnace extends BaseContainer {

    public static IIcon textureTop;
    private IIcon textureBottom;
    private IIcon textureSide;
    private IIcon textureFrontOn;
    private IIcon textureFrontOff;
    public IIcon textureFront;
    
    public static boolean isActive = false;
    
    public BlockAlloyFurnace() {

        super(Material.rock, TileAlloyFurnace.class);
        setBlockName(MainClass.MODID + ":" + "alloy_furnace");
        setCreativeTab(MainClass.tab_expansion);

    }
    

    
    public static int getDirection(int side)
    {
        return side & 3;
    }
    
    
   
    @Override
    public IIcon getIcon(int side, int meta) {

    	
        ForgeDirection s = ForgeDirection.getOrientation(side);
        // If is facing

        if (3 == side) {
            return textureFrontOff;
        }
        switch (s) {
        case UP:
            return textureTop;
        case DOWN:
            return textureBottom;
        case EAST:
        case NORTH:
        case SOUTH:
        case WEST:
        case UNKNOWN:
            return textureSide;
        default:
            break;

        }
        
        ForgeDirection forgeSide = ForgeDirection.getOrientation(side);
    	if (meta == 3)
    	{
    		return isActive ? textureFrontOn : textureFrontOff;
    	}
    	
        return null;
    }
    
public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
    	
        TileAlloyFurnace te = (TileAlloyFurnace) world.getTileEntity(x, y, z);
        isActive = te.getIsActive();
        ForgeDirection forgeSide = ForgeDirection.getOrientation(side);
        if (forgeSide == ForgeDirection.UP)
            return textureTop;
        if (forgeSide == ForgeDirection.DOWN)
            return textureBottom;
        if (forgeSide == te.getFacingDirection() || side == 0)
        {
        	return te.getIsActive() ? textureFrontOn : textureFrontOff;
        }
            
        else return textureSide;
    }
    
    
    
    
    
    @Override
    public boolean isOpaqueCube() {

        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rnd) {

        TileAlloyFurnace te = (TileAlloyFurnace) world.getTileEntity(x, y, z);
        if (te.getIsActive()) {
            int l = te.getFacingDirection().ordinal();
            float f = x + 0.5F;
            float f1 = y + 0.0F + rnd.nextFloat() * 6.0F / 16.0F;
            float f2 = z + 0.5F;
            float f3 = 0.52F;
            float f4 = rnd.nextFloat() * 0.6F - 0.3F;

            if (l == 4) {
                world.spawnParticle("smoke", f - f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
                world.spawnParticle("flame", f - f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
            } else if (l == 5) {
                world.spawnParticle("smoke", f + f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
                world.spawnParticle("flame", f + f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
            } else if (l == 2) {
                world.spawnParticle("smoke", f + f4, f1, f2 - f3, 0.0D, 0.0D, 0.0D);
                world.spawnParticle("flame", f + f4, f1, f2 - f3, 0.0D, 0.0D, 0.0D);
            } else if (l == 3) {
                world.spawnParticle("smoke", f + f4, f1, f2 + f3, 0.0D, 0.0D, 0.0D);
                world.spawnParticle("flame", f + f4, f1, f2 + f3, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    // Not sure if you need this function.
    @Override
    public Item getItemDropped(int p_149650_1_, Random random, int p_149650_3_) {

        return Item.getItemFromBlock(MainClass.BlockAlloyFurnace);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {

        textureTop = iconRegister.registerIcon(MainClass.MODID + ":" + "alloyfurnace" + "_top");
        textureBottom = iconRegister.registerIcon(MainClass.MODID + ":"  + "alloyfurnace" + "_top");
        textureSide = iconRegister.registerIcon(MainClass.MODID + ":"  + "alloyfurnace" + "_side");
        textureFrontOn = iconRegister.registerIcon(MainClass.MODID + ":"  + "alloyfurnace" + "_front_active");
        textureFrontOff = iconRegister.registerIcon(MainClass.MODID + ":" + "alloyfurnace"  + "_front");
    }

    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z) {

        TileAlloyFurnace te = (TileAlloyFurnace) world.getTileEntity(x, y, z);
        return te.getIsActive() ? 13 : 0;
    }

    @Override
    public GUI_NUMBER_IDS getGuiID() {

        return GUI_NUMBER_IDS.ALLOY_FURNACE;
    }



    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderType() {

        return 0;
    }
}