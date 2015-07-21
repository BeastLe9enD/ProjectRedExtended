package de.xthelegend.projectredextended;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockStone;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockCarvedMarble extends BlockStone{
	
	protected IIcon textureTop;
	protected IIcon textureBottom;
	protected IIcon textureSide;
	protected IIcon textureFront;
	
	public BlockCarvedMarble()
	{
		this.setHardness(0.8F);
        this.setBlockName(MainClass.MODID + ":" + "marble_carved");
        this.setBlockTextureName(MainClass.MODID + ":" + "marble_carved");
        setCreativeTab(MainClass.tab_exploration);
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {

      
        ForgeDirection s = ForgeDirection.getOrientation(side);
        
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
        return null;
    }

	@Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {

        ForgeDirection s = ForgeDirection.getOrientation(side);
        if (meta == side) {
            return textureFront;
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
        return null;
    }

    @Override
    public boolean isOpaqueCube() {

        return true;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
    	String r = MainClass.MODID + ":" + "marble_carved";
        textureTop = iconRegister.registerIcon(r+"");
        textureBottom = iconRegister.registerIcon(r+"");
        textureSide = iconRegister.registerIcon(r+"_side");
        textureFront = iconRegister.registerIcon(r+"_side");
    }
}
