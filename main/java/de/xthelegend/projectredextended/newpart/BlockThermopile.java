package de.xthelegend.projectredextended.newpart;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.xthelegend.projectredextended.MainClass;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockPumpkin;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockThermopile extends BlockDirectional{

	private boolean field_149985_a;
    @SideOnly(Side.CLIENT)
    private IIcon field_149984_b;
    @SideOnly(Side.CLIENT)
    private IIcon field_149986_M;
	
	public BlockThermopile(Material mat) {
		super(mat);
		setHardness(3.0F);
		setBlockTextureName(MainClass.MODID +":" +"thermopile");
		setBlockName("thermopile");
		setCreativeTab(MainClass.tabProjectExtended);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int p_149691_1_, int p_149691_2_)
    {
        return p_149691_1_ == 1 ? this.field_149984_b : (p_149691_1_ == 0 ? this.field_149984_b : (p_149691_2_ == 2 && p_149691_1_ == 2 ? this.field_149986_M : (p_149691_2_ == 3 && p_149691_1_ == 5 ? this.field_149986_M : (p_149691_2_ == 0 && p_149691_1_ == 3 ? this.field_149986_M : (p_149691_2_ == 1 && p_149691_1_ == 4 ? this.field_149986_M : this.blockIcon)))));
    }
	
	public void registerBlockIcons(IIconRegister p_149651_1_)
    {
        this.field_149986_M = p_149651_1_.registerIcon(this.getTextureName() + "_face_" + (this.field_149985_a +""));
        this.field_149984_b = p_149651_1_.registerIcon(this.getTextureName() + "_top");
        this.blockIcon = p_149651_1_.registerIcon(this.getTextureName() + "_side");
    }

	@Override
	public void onBlockAdded(World p_149726_1_, int p_149726_2_, int p_149726_3_, int p_149726_4_)
    {
        super.onBlockAdded(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);

        if (p_149726_1_.getBlock(p_149726_2_, p_149726_3_ - 1, p_149726_4_) == Blocks.snow && p_149726_1_.getBlock(p_149726_2_, p_149726_3_ - 2, p_149726_4_) == Blocks.snow)
        {
            if (!p_149726_1_.isRemote)
            {
                p_149726_1_.setBlock(p_149726_2_, p_149726_3_, p_149726_4_, getBlockById(0), 0, 2);
                p_149726_1_.setBlock(p_149726_2_, p_149726_3_ - 1, p_149726_4_, getBlockById(0), 0, 2);
                p_149726_1_.setBlock(p_149726_2_, p_149726_3_ - 2, p_149726_4_, getBlockById(0), 0, 2);
                EntitySnowman entitysnowman = new EntitySnowman(p_149726_1_);
                entitysnowman.setLocationAndAngles((double)p_149726_2_ + 0.5D, (double)p_149726_3_ - 1.95D, (double)p_149726_4_ + 0.5D, 0.0F, 0.0F);
                p_149726_1_.spawnEntityInWorld(entitysnowman);
                p_149726_1_.notifyBlockChange(p_149726_2_, p_149726_3_, p_149726_4_, getBlockById(0));
                p_149726_1_.notifyBlockChange(p_149726_2_, p_149726_3_ - 1, p_149726_4_, getBlockById(0));
                p_149726_1_.notifyBlockChange(p_149726_2_, p_149726_3_ - 2, p_149726_4_, getBlockById(0));
            }

            for (int i1 = 0; i1 < 120; ++i1)
            {
                p_149726_1_.spawnParticle("snowshovel", (double)p_149726_2_ + p_149726_1_.rand.nextDouble(), (double)(p_149726_3_ - 2) + p_149726_1_.rand.nextDouble() * 2.5D, (double)p_149726_4_ + p_149726_1_.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
            }
        }
        else if (p_149726_1_.getBlock(p_149726_2_, p_149726_3_ - 1, p_149726_4_) == Blocks.iron_block && p_149726_1_.getBlock(p_149726_2_, p_149726_3_ - 2, p_149726_4_) == Blocks.iron_block)
        {
            boolean flag = p_149726_1_.getBlock(p_149726_2_ - 1, p_149726_3_ - 1, p_149726_4_) == Blocks.iron_block && p_149726_1_.getBlock(p_149726_2_ + 1, p_149726_3_ - 1, p_149726_4_) == Blocks.iron_block;
            boolean flag1 = p_149726_1_.getBlock(p_149726_2_, p_149726_3_ - 1, p_149726_4_ - 1) == Blocks.iron_block && p_149726_1_.getBlock(p_149726_2_, p_149726_3_ - 1, p_149726_4_ + 1) == Blocks.iron_block;

            if (flag || flag1)
            {
                p_149726_1_.setBlock(p_149726_2_, p_149726_3_, p_149726_4_, getBlockById(0), 0, 2);
                p_149726_1_.setBlock(p_149726_2_, p_149726_3_ - 1, p_149726_4_, getBlockById(0), 0, 2);
                p_149726_1_.setBlock(p_149726_2_, p_149726_3_ - 2, p_149726_4_, getBlockById(0), 0, 2);

                if (flag)
                {
                    p_149726_1_.setBlock(p_149726_2_ - 1, p_149726_3_ - 1, p_149726_4_, getBlockById(0), 0, 2);
                    p_149726_1_.setBlock(p_149726_2_ + 1, p_149726_3_ - 1, p_149726_4_, getBlockById(0), 0, 2);
                }
                else
                {
                    p_149726_1_.setBlock(p_149726_2_, p_149726_3_ - 1, p_149726_4_ - 1, getBlockById(0), 0, 2);
                    p_149726_1_.setBlock(p_149726_2_, p_149726_3_ - 1, p_149726_4_ + 1, getBlockById(0), 0, 2);
                }

                EntityIronGolem entityirongolem = new EntityIronGolem(p_149726_1_);
                entityirongolem.setPlayerCreated(true);
                entityirongolem.setLocationAndAngles((double)p_149726_2_ + 0.5D, (double)p_149726_3_ - 1.95D, (double)p_149726_4_ + 0.5D, 0.0F, 0.0F);
                p_149726_1_.spawnEntityInWorld(entityirongolem);

                for (int l = 0; l < 120; ++l)
                {
                    p_149726_1_.spawnParticle("snowballpoof", (double)p_149726_2_ + p_149726_1_.rand.nextDouble(), (double)(p_149726_3_ - 2) + p_149726_1_.rand.nextDouble() * 3.9D, (double)p_149726_4_ + p_149726_1_.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
                }

                p_149726_1_.notifyBlockChange(p_149726_2_, p_149726_3_, p_149726_4_, getBlockById(0));
                p_149726_1_.notifyBlockChange(p_149726_2_, p_149726_3_ - 1, p_149726_4_, getBlockById(0));
                p_149726_1_.notifyBlockChange(p_149726_2_, p_149726_3_ - 2, p_149726_4_, getBlockById(0));

                if (flag)
                {
                    p_149726_1_.notifyBlockChange(p_149726_2_ - 1, p_149726_3_ - 1, p_149726_4_, getBlockById(0));
                    p_149726_1_.notifyBlockChange(p_149726_2_ + 1, p_149726_3_ - 1, p_149726_4_, getBlockById(0));
                }
                else
                {
                    p_149726_1_.notifyBlockChange(p_149726_2_, p_149726_3_ - 1, p_149726_4_ - 1, getBlockById(0));
                    p_149726_1_.notifyBlockChange(p_149726_2_, p_149726_3_ - 1, p_149726_4_ + 1, getBlockById(0));
                }
            }
        }
    }
}
