package de.xthelegend.projectredextended.newpart;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBlulectricEngine extends BlockContainer{

	public BlockBlulectricEngine(Material mat) {
		super(mat);
		// TODO Auto-generated constructor stub
	}


	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	
	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		/*// TODO Auto-generated method stub
		TileEntity entity = (TileEntity) new TileBlulectricEngineRenderer();
		return new  */
		return null;
	}

}
