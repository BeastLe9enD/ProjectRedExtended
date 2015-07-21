package de.xthelegend.projectredextended;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.IGrowable;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenCanopyTree;
import net.minecraft.world.gen.feature.WorldGenForest;
import net.minecraft.world.gen.feature.WorldGenMegaJungle;
import net.minecraft.world.gen.feature.WorldGenMegaPineTree;
import net.minecraft.world.gen.feature.WorldGenSavannaTree;
import net.minecraft.world.gen.feature.WorldGenTaiga2;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BlockRubberSapling extends BlockBush implements IGrowable{
	public static final String[] field_149882_a = new String[] {"oak"};
	private static final IIcon[] field_149881_b = new IIcon[0];
    private static final String __OBFID = "CL_00000305";

    public BlockRubberSapling()
    {
        float f = 0.4F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setHardness(0.0F);
        this.setStepSound(Block.soundTypeGrass);
        setCreativeTab(MainClass.tab_exploration);
        this.setBlockName(MainClass.MODID + ":" + "rubber_sapling");
        setBlockTextureName(MainClass.MODID + ":" + "rubber_tree_sapling");
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_)
    {
        if (!p_149674_1_.isRemote)
        {
            super.updateTick(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, p_149674_5_);

            if (p_149674_1_.getBlockLightValue(p_149674_2_, p_149674_3_ + 1, p_149674_4_) >= 9 && p_149674_5_.nextInt(7) == 0)
            {
                this.func_149879_c(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, p_149674_5_);
            }
        }
    }

    /**
     * Gets the block's texture. Args: side, meta
     */


    public void func_149879_c(World p_149879_1_, int p_149879_2_, int p_149879_3_, int p_149879_4_, Random p_149879_5_)
    {
        int l = p_149879_1_.getBlockMetadata(p_149879_2_, p_149879_3_, p_149879_4_);

        if ((l & 8) == 0)
        {
            p_149879_1_.setBlockMetadataWithNotify(p_149879_2_, p_149879_3_, p_149879_4_, l | 8, 4);
        }
        else
        {
            this.func_149878_d(p_149879_1_, p_149879_2_, p_149879_3_, p_149879_4_, p_149879_5_);
        }
    }

    public void func_149878_d(World p_149878_1_, int p_149878_2_, int p_149878_3_, int p_149878_4_, Random p_149878_5_)
    {
        if (!net.minecraftforge.event.terraingen.TerrainGen.saplingGrowTree(p_149878_1_, p_149878_5_, p_149878_2_, p_149878_3_, p_149878_4_)) return;
        int l = p_149878_1_.getBlockMetadata(p_149878_2_, p_149878_3_, p_149878_4_) & 7;
        Object object = p_149878_5_.nextInt(10) == 0 ? new de.xthelegend.projectredextended.rubbergen.schematic01() : new de.xthelegend.projectredextended.rubbergen.schematic01();
        int i1 = 0;
        int j1 = 0;
        boolean flag = false;
        object = new de.xthelegend.projectredextended.rubbergen.schematic01();
        
            
        

        Block block = Blocks.air;

        if (flag)
        {
            p_149878_1_.setBlock(p_149878_2_ + i1, p_149878_3_, p_149878_4_ + j1, block, 0, 4);
            p_149878_1_.setBlock(p_149878_2_ + i1 + 1, p_149878_3_, p_149878_4_ + j1, block, 0, 4);
            p_149878_1_.setBlock(p_149878_2_ + i1, p_149878_3_, p_149878_4_ + j1 + 1, block, 0, 4);
            p_149878_1_.setBlock(p_149878_2_ + i1 + 1, p_149878_3_, p_149878_4_ + j1 + 1, block, 0, 4);
        }
        else
        {
            p_149878_1_.setBlock(p_149878_2_, p_149878_3_, p_149878_4_, block, 0, 4);
        }

        if (!((WorldGenerator)object).generate(p_149878_1_, p_149878_5_, p_149878_2_ + i1, p_149878_3_, p_149878_4_ + j1))
        {
            if (flag)
            {
                p_149878_1_.setBlock(p_149878_2_ + i1, p_149878_3_, p_149878_4_ + j1, this, l, 4);
                p_149878_1_.setBlock(p_149878_2_ + i1 + 1, p_149878_3_, p_149878_4_ + j1, this, l, 4);
                p_149878_1_.setBlock(p_149878_2_ + i1, p_149878_3_, p_149878_4_ + j1 + 1, this, l, 4);
                p_149878_1_.setBlock(p_149878_2_ + i1 + 1, p_149878_3_, p_149878_4_ + j1 + 1, this, l, 4);
            }
            else
            {
                p_149878_1_.setBlock(p_149878_2_, p_149878_3_, p_149878_4_, this, l, 4);
            }
        }
    }

    public boolean func_149880_a(World p_149880_1_, int p_149880_2_, int p_149880_3_, int p_149880_4_, int p_149880_5_)
    {
        return p_149880_1_.getBlock(p_149880_2_, p_149880_3_, p_149880_4_) == this && (p_149880_1_.getBlockMetadata(p_149880_2_, p_149880_3_, p_149880_4_) & 7) == p_149880_5_;
    }

    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    public int damageDropped(int p_149692_1_)
    {
        return MathHelper.clamp_int(p_149692_1_ & 7, 0, 5);
    }

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */




    public boolean func_149851_a(World p_149851_1_, int p_149851_2_, int p_149851_3_, int p_149851_4_, boolean p_149851_5_)
    {
        return true;
    }

    public boolean func_149852_a(World p_149852_1_, Random p_149852_2_, int p_149852_3_, int p_149852_4_, int p_149852_5_)
    {
        return (double)p_149852_1_.rand.nextFloat() < 0.45D;
    }

    public void func_149853_b(World p_149853_1_, Random p_149853_2_, int p_149853_3_, int p_149853_4_, int p_149853_5_)
    {
        this.func_149879_c(p_149853_1_, p_149853_3_, p_149853_4_, p_149853_5_, p_149853_2_);
    }
}
