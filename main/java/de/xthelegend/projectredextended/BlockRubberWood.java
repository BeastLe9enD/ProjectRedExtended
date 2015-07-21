package de.xthelegend.projectredextended;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.block.BlockLog;

public class BlockRubberWood extends Block{

	protected IIcon textureTop;
	protected IIcon textureBottom;
	protected IIcon textureSide;
	protected IIcon textureFront;
	
	
	
	public BlockRubberWood(Material p_i45394_1_) {
		super(Material.wood);
		// TODO Auto-generated constructor stub
		this.setBlockName(MainClass.MODID + ":" + "rubberwood");
		this.setHardness(0.7F);
		this.setResistance(0.7F);
		this.setStepSound(Block.soundTypeWood);
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
	 public boolean isWood(IBlockAccess world, int x, int y, int z)
	    {
	         return true;
	    }

	  @Override
	    public boolean canSustainLeaves(IBlockAccess world, int x, int y, int z)
	    {
	        return true;
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
    	String r = MainClass.MODID + ":" + "rubberwood";
        textureTop = iconRegister.registerIcon(r+"_top");
        textureBottom = iconRegister.registerIcon(r+"_top");
        textureSide = iconRegister.registerIcon(r+"_side");
        textureFront = iconRegister.registerIcon(r+"_side");
    }
}
