package de.xthelegend.projectredextended;

import net.minecraft.block.Block;

//package de.xthelegend.projectredextended;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockRubberLeaves extends BlockLeaves
{
    int[] adjacentTreeBlocks;

    public BlockRubberLeaves()
    {
        super();
        this.setTickRandomly(true);
        this.setHardness(0.2F);
        this.setLightOpacity(1);
        this.setStepSound(Block.soundTypeGrass);
        setCreativeTab(MainClass.tab_exploration);
        this.setBlockName(MainClass.MODID + ":" + "rubber_leaves");
        //Blocks.fire.setFireInfo(this, 30, 60);
  ;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getBlockColor()
    {
        double d0 = 0.5D;
        double d1 = 1.0D;
        return ColorizerFoliage.getFoliageColor(d0, d1);
    }

    @SideOnly(Side.CLIENT)
    public int getRenderColor(int p_149741_1_)
    {
        return ColorizerFoliage.getFoliageColorBasic();
    }

    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess p_149720_1_, int p_149720_2_, int p_149720_3_, int p_149720_4_)
    {
        int l = 0;
        int i1 = 0;
        int j1 = 0;

        for (int k1 = -1; k1 <= 1; ++k1)
        {
            for (int l1 = -1; l1 <= 1; ++l1)
            {
                int i2 = p_149720_1_.getBiomeGenForCoords(p_149720_2_ + l1, p_149720_4_ + k1).getBiomeFoliageColor(p_149720_2_ + l1, p_149720_3_, p_149720_4_ + k1);
                l += (i2 & 16711680) >> 16;
                i1 += (i2 & 65280) >> 8;
                j1 += i2 & 255;
            }
        }

        return (l / 9 & 255) << 16 | (i1 / 9 & 255) << 8 | j1 / 9 & 255;
    }

    @Override
    public boolean isOpaqueCube ()
    {
    	return false;
    }
 
    
    @Override
    public void updateTick (World world, int x, int y, int z, Random random)
    {
        if (!world.isRemote)
        {
            int meta = world.getBlockMetadata(x, y, z);

            if ((meta & 4) == 0)
            {
                boolean nearbyTree = false;
                byte range = 4;
                for (int posX = x - range; posX <= x + range; posX++)
                {
                    for (int posY = y - range; posY <= y + range; posY++)
                    {
                        for (int posZ = z - range; posZ <= z + range; posZ++)
                        {
                            Block block = world.getBlock(posX, posY, posZ);
                            if (block != null && block.canSustainLeaves(world, posX, posY, posZ))
                                nearbyTree = true;
                        }
                    }
                }

                if (!nearbyTree)
                    this.removeLeaves(world, x, y, z);
            }
        }
    }

    public void removeLeaves (World world, int x, int y, int z)
    {
        this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
        world.setBlock(x, y, z, Blocks.air, 0, 7);
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    @Override
    public int quantityDropped (Random var1)
    {
        return var1.nextInt(20) == 0 ? 1 : 0;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    @Override
    public Item getItemDropped (int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return Item.getItemFromBlock(MainClass.BlockRubberSapling);
    }

    @Override
    public void dropBlockAsItemWithChance (World par1World, int par2, int par3, int par4, int par5, float par6, int par7)
    {
        if (!par1World.isRemote)
        {
            ArrayList<ItemStack> items = getDrops(par1World, par2, par3, par4, par5, par7);

            for (ItemStack item : items)
            {
                if (par1World.rand.nextFloat() <= par6)
                {
                    this.dropBlockAsItem(par1World, par2, par3, par4, item);
                }
            }
        }
    }

    public IIcon fastIcons;
    public IIcon fancyIcons;

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon (int side, int metadata)
    {
        if (!field_150121_P)
            return fancyIcons;
        else
            return fastIcons;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons (IIconRegister iconRegister)
    {
        this.fastIcons = iconRegister.registerIcon(MainClass.MODID + ":" + "rubber"+ "_leaves_fast");
        this.fancyIcons = iconRegister.registerIcon(MainClass.MODID + ":" + "rubber"+ "_leaves_fancy");
        
    }

    /**
     * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
     * coordinates.  Args: blockAccess, x, y, z, side
     */

    @Override
    public boolean shouldSideBeRendered (IBlockAccess var1, int var2, int var3, int var4, int var5)
    {
        return this.field_150121_P ? super.shouldSideBeRendered(var1, var2, var3, var4, var5) : true;
    }

    

    public int getDamageValue (World par1World, int par2, int par3, int par4)
    {
        return this.damageDropped(par1World.getBlockMetadata(par2, par3, par4)) % 3;
    }

    @Override
    public int getLightOpacity (IBlockAccess world, int x, int y, int z)
    {
        int meta = world.getBlockMetadata(x, y, z) % 4;
        if (meta == 0)
        {
            return 255;
        }
        return super.getLightOpacity(world, x, y, z);//this.getLightOpacity(world, x, y, z);//lightOpacity[blockID];
    }

    @Override
    public String[] func_150125_e ()
    {
        return null;
    }
}