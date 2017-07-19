package de.beastlegend.prextended.block;

import codechicken.lib.vec.Vector3;
import de.beastlegend.prextended.ProjRedExtended;
import de.beastlegend.prextended.gui.EnumGuiID;
import de.beastlegend.prextended.tile.TileEntityBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.util.vector.Vector3f;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * Created by BeastLe9enD on 18.07.2017.
 */
public class BlockContainerBase extends BlockBase implements ITileEntityProvider {

    @SideOnly(Side.CLIENT)
    protected Map<String, TextureAtlasSprite> textures;
    private EnumGuiID guiID = EnumGuiID.INVALID;
    private Class<? extends TileEntityBase> tileEntityClass;
    private boolean isRedstoneEmitter;
    private boolean isSilkyRemoving;

    public BlockContainerBase(String blockName, Material material, Class<? extends TileEntityBase> tileEntityClass) {
        super(blockName, material);
        this.tileEntityClass = tileEntityClass;
        isBlockContainer = true;
    }


    public BlockContainerBase setGuiID(EnumGuiID guiID) {
        this.guiID = guiID;
        return this;
    }

    public BlockContainerBase setTileEntityClass(Class<? extends TileEntityBase>tileEntityClass) {
        this.tileEntityClass = tileEntityClass;
        return this;
    }

    public BlockContainerBase emitsRedstone() {
        isRedstoneEmitter = true;
        return this;
    }

    protected Class<? extends TileEntity> getTileEntity() {
        return tileEntityClass;
    }

    protected TileEntityBase getTileEntity(IBlockAccess world, BlockPos tilePosition) {
        TileEntity tileEntity = world.getTileEntity(tilePosition);
        if(tileEntity == null || !(tileEntity instanceof TileEntityBase)) {
            return null;
        }
        return (TileEntityBase)tileEntity;
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos position, Block block) {
        super.neighborChanged(state, world, position, block);
        if(!world.isRemote) {
            TileEntityBase tileEntity = getTileEntity(world, position);
            if(tileEntity != null) {
                tileEntity.onNeighbourBlockChanged();
            }
        }
    }

    @Override
    public boolean canProvidePower(IBlockState state) {
        return isRedstoneEmitter;
    }

    @Override
    public int getWeakPower(IBlockState state, IBlockAccess world, BlockPos position, EnumFacing side)
    {
        TileEntityBase tileEntity = getTileEntity(world, position);
        if(tileEntity != null) {
            return tileEntity.getOutputtingRedstone();
        }
        return 0;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos position, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if(player.isSneaking()) {
            if(player.getHeldItem(EnumHand.MAIN_HAND) != null) {
                if(player.getHeldItem(EnumHand.MAIN_HAND).getItem() == mrtjp.projectred.ProjectRedCore.itemScrewdriver()) {
                    return false;
                }
            }
        }

        if(player.isSneaking()) {
            return false;
        }

        TileEntityBase tileEntity = getTileEntity(world, position);
        if(tileEntity == null) {
            return false;
        }

        if(getGuiId() != EnumGuiID.INVALID) {
            if(!world.isRemote) {
                player.openGui(ProjRedExtended.INSTANCE, getGuiId().ordinal(), world, position.getX(), position.getY(), position.getZ());
                return true;
            }
        }

        return false;
    }

    @Override
    public void breakBlock(World world, BlockPos position, IBlockState state) {
        super.breakBlock(world, position, state);
        world.removeTileEntity(position);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos position, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        TileEntityBase tileEntity = getTileEntity(world, position);
        if(tileEntity != null) {
            int value = (int)(MathHelper.floor_double(placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            tileEntity.setFacingDirection(EnumFacing.getHorizontal(value));
        }
    }

    private boolean canRotateVertical() {
        return true;
    }

    @Override
    public boolean rotateBlock(World world, BlockPos pos, EnumFacing axis) {
        TileEntityBase tileEntity = getTileEntity(world, pos);
        if(tileEntity == null) {
            return false;
        }
        EnumFacing direction = tileEntity.getFacingDirection();
        direction = direction.rotateAround(axis.getAxis()); //TRY
        if(direction != EnumFacing.UP && direction != EnumFacing.DOWN || canRotateVertical()) {
            tileEntity.setFacingDirection(direction);
            return true;
        }
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        try {
            TileEntity tileEntity = tileEntityClass.newInstance();
            return tileEntity;
        }
        catch(InstantiationException ex) {
            return null;
        }
        catch(IllegalAccessException ex) {
            return null;
        }
    }

    public EnumGuiID getGuiId() {
        return guiID;
    }

    @Override
    public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side)
    {
        TileEntityBase tileEntity = getTileEntity(world, pos);
        if(tileEntity == null) {
            return false;
        }
        return tileEntity.canConnectRedstone();
    }
}
