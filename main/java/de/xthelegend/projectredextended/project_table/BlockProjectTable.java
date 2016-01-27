package de.xthelegend.projectredextended.project_table;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.xthelegend.projectredextended.MainClass;
import de.xthelegend.projectredextended.alloy_furnace.TileAlloyFurnace;
import de.xthelegend.projectredextended.lib.base.BaseContainer;
import de.xthelegend.projectredextended.lib.base.BaseTile;
import net.minecraft.block.BlockPumpkin;

public class BlockProjectTable extends BaseContainer {
	protected IIcon textureTop;
    protected IIcon textureBottom;
    protected IIcon textureSide;
    protected IIcon textureFront;

    public BlockProjectTable() {

        super(Material.wood, TileProjectTable.class);
        setBlockName(MainClass.MODID + ":" + "project_table");
        setCreativeTab(MainClass.tab_expansion);
        
        
    }

    public BlockProjectTable(Class<? extends BaseTile> tileClass) {

        super(Material.wood, tileClass);
    }


    
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {

    	TileProjectTable te = (TileProjectTable) world.getTileEntity(x, y, z);
        ForgeDirection s = ForgeDirection.getOrientation(side);
        // If is facing

        if (te.getFacingDirection() == s) {
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

        textureTop = iconRegister.registerIcon(MainClass.MODID + ":" + "projecttable_" + "up");
        textureBottom = iconRegister.registerIcon(MainClass.MODID + ":" + "projecttable_" + "down");
        textureSide = iconRegister.registerIcon(MainClass.MODID + ":" + "projecttable"+ "_side");
        textureFront = iconRegister.registerIcon(MainClass.MODID + ":" + "projecttable" + "_side_front");
    }



    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderType() {

        return 0;
    }
    

}
