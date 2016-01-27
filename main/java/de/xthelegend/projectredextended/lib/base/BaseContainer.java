package de.xthelegend.projectredextended.lib.base;

import java.util.HashMap;
import java.util.Map;

import uk.co.qmunity.lib.misc.ForgeDirectionUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.xthelegend.projectredextended.MainClass;
import de.xthelegend.projectredextended.lib.base.BlockBase_render.EnumFaceType;
import net.minecraft.block.material.Material;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;


public class BaseContainer extends BaseBlock implements ITileEntityProvider{

	@SideOnly(Side.CLIENT)
    protected Map<String, IIcon> textures;
    private GUI_NUMBER_IDS guiId = GUI_NUMBER_IDS.INVALID;
    private Class<? extends BaseTile> tileEntityClass;
    private boolean isRedstoneEmitter;
    private boolean isSilkyRemoving;
	
    public static String name;
    
	public BaseContainer(Material material, String name, Class<? extends BaseTile> tileEntityClass) {
		super(material, name);
		this.name = name;
        setCreativeTab(MainClass.tab_expansion);

		// TODO Auto-generated constructor stub
	}
	
	public BaseContainer setGuiId(GUI_NUMBER_IDS guiId) {

        this.guiId = guiId;
        return this;
    }
	
	 public BaseContainer(Material material, Class<? extends BaseTile> tileEntityClass) {

	        super(material, name);
	        isBlockContainer = true;
	        setTileEntityClass(tileEntityClass);
	    }
	
	public BaseContainer setTileEntityClass(Class<? extends BaseTile> tileEntityClass) {

        this.tileEntityClass = tileEntityClass;
        return this;
    }

    public BaseContainer emitsRedstone() {

        isRedstoneEmitter = true;
        return this;
    }
    
    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {

        try {
            return getTileEntity().newInstance();
        } catch (Exception e) {
            return null;
        }
    }
    
    protected Class<? extends TileEntity> getTileEntity() {

        return tileEntityClass;
    }

    protected BaseTile get(IBlockAccess w, int x, int y, int z) {

        TileEntity te = w.getTileEntity(x, y, z);

        if (te == null || !(te instanceof BaseTile))
            return null;

        return (BaseTile) te;
    }
    
    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {

        super.onNeighborBlockChange(world, x, y, z, block);
        // Only do this on the server side.
        if (!world.isRemote) {
            BaseTile tileEntity = get(world, x, y, z);
            if (tileEntity != null) {
                tileEntity.onBlockNeighbourChanged();
            }
        }
    }

    @Override
    public boolean canProvidePower() {

        return isRedstoneEmitter;
    }

    @Override
    public int isProvidingWeakPower(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {

        TileEntity te = get(par1IBlockAccess, par2, par3, par4);
        if (te instanceof BaseTile) {
            BaseTile tileBase = (BaseTile) te;
            return tileBase.getOutputtingRedstone();
        }
        return 0;
    }
    
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {

        if (player.isSneaking()) {
            if (player.getHeldItem() != null) {
                if (player.getHeldItem().getItem() == mrtjp.projectred.ProjectRedCore.itemScrewdriver()) {
                    return false;
                }
            }
        }

        if (player.isSneaking()) {
            return false;
        }

        TileEntity entity = get(world, x, y, z);
        if (entity == null || !(entity instanceof BaseTile)) {
            return false;
        }

        if (getGuiID() != GUI_NUMBER_IDS.INVALID) {
            if (!world.isRemote)
                player.openGui(MainClass.instance, getGuiID().ordinal(), world, x, y, z);
            return true;
        }
        return false;
    }
    
    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) 
    {

        /*if (!isSilkyRemoving) {
            BaseTile tile = get(world, x, y, z);
            if (tile != null) {
                for (ItemStack stack : tile.getDrops()) {
                    IOHelper.spawnItemInWorld(world, stack, x + 0.5, y + 0.5, z + 0.5);
                }
            }
        }*/
        super.breakBlock(world, x, y, z, block, meta);
        world.removeTileEntity(x, y, z);
    }

    /**
     * Method to detect how the block was placed, and what way it's facing.
     */
    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack iStack) 
    {
    	TileEntity te= get(world, x, y, z);
        

        
         ((BaseTile) te).setFacingDirection(ForgeDirectionUtils.getDirectionFacing(player, canRotateVertical()).getOpposite());
    }
    
    private boolean canRotateVertical() {
		// TODO Auto-generated method stub
		return true;
	}

    @Override
    public boolean rotateBlock(World worldObj, int x, int y, int z, ForgeDirection axis) {

        TileEntity te = get(worldObj, x, y, z);
        
        
            ForgeDirection dir = ((BaseTile) te).getFacingDirection();
            dir = dir.getRotation(axis);
            if (dir != ForgeDirection.UP && dir != ForgeDirection.DOWN || canRotateVertical()) {
            	((BaseTile) te).setFacingDirection(dir);
                return true;
            }
        
        return false;
}
    
	@Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {

        textures = new HashMap<String, IIcon>();
        for (EnumFaceType faceType : EnumFaceType.values()) {
            boolean ejecting = false;
            boolean powered = false;

            do {
                do {
                    String iconName = getIconName(faceType, ejecting, powered);
                    if (!textures.containsKey(iconName)) {
                        textures.put(iconName, iconRegister.registerIcon(iconName));
                    }

                    powered = !powered;
                } while (powered);
                ejecting = !ejecting;
            } while (ejecting);
        }
    }
    
    
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(EnumFaceType faceType, boolean ejecting, boolean powered) {

        return textures.get(getIconName(faceType, ejecting, powered));
    }

    @SideOnly(Side.CLIENT)
    protected IIcon getIcon(EnumFaceType faceType, boolean ejecting, boolean powered, int side, TileEntity te) {

        return getIcon(faceType, ejecting, powered);
    }

    @Override
    public Block setBlockName(String name) {

        super.setBlockName(name);
        textureName = MainClass.MODID + ":" + name;
        return this;
    }

    protected String getIconName(EnumFaceType faceType, boolean ejecting, boolean powered) {

        String iconName = textureName + "_" + faceType.toString().toLowerCase();
        if (faceType == EnumFaceType.SIDE) {
            if (ejecting)
                iconName += "_active";

            // TODO: When powersystem is implemented, uncomment this!
            // if (powered) iconName += "_powered";
        }
        return iconName;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {

        if (textures == null)
            return super.getIcon(world, x, y, z, side);
        TileEntity te = get(world, x, y, z);
        EnumFaceType faceType = BlockBase_render.EnumFaceType.SIDE;
        boolean powered = false;
        boolean ejecting = false;
        
        
        return getIcon(faceType, ejecting, powered, side, te);
    	
    }

    /**
     * This method will only be called from the item render method. the meta variable doesn't have any meaning.
     */
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {

        if (textures == null)
            return super.getIcon(side, meta);
        return getIcon(EnumFaceType.values()[side == 0 ? 2 : side == 1 ? 1 : 0], false, false);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderType() {

        return BlockBase_render.RENDER_ID;
    }

    public GUI_NUMBER_IDS getGuiID() {

        return guiId;
    }



    @Override
    public boolean canConnectRedstone(IBlockAccess world, int x, int y, int z, int side) {

        return ((BaseTile) world.getTileEntity(x, y, z)).canConnectRedstone();
    }
}
	


