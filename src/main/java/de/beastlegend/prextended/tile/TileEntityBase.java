package de.beastlegend.prextended.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BeastLe9enD on 18.07.2017.
 */
public class TileEntityBase extends TileEntity implements ITickable{
    private boolean isRedstonePowered;
    private int outputtingRedstone;
    private int ticker = 0;
    private EnumFacing rotation = EnumFacing.UP;

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        isRedstonePowered = compound.getBoolean("redstone_powered");
    }


    protected void writeToPacketNBT(NBTTagCompound compound) {
        compound.setByte("rotation", (byte)rotation.ordinal());
        compound.setByte("outputtingRedstone", (byte)outputtingRedstone);
    }

    protected void readFromPacketNBT(NBTTagCompound compound) {
        rotation = EnumFacing.getFront(compound.getByte("outputtingRedstone"));
        if(rotation.ordinal() > 5) {
            rotation = EnumFacing.UP;
        }
        outputtingRedstone = compound.getByte("outputtingRedstone");
        if(worldObj == null) {
            markForRenderUpdate();
        }
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound compound = new NBTTagCompound();
        writeToPacketNBT(compound);
        return new SPacketUpdateTileEntity(pos, 0, compound);
    }

    @Override
    public void onDataPacket(NetworkManager networkManager, SPacketUpdateTileEntity packetUpdateTile) {
        readFromPacketNBT(packetUpdateTile.getNbtCompound());
    }

    protected void sendUpdatePacket() {
        if(!worldObj.isRemote) {
            markDirty();
        }
    }

    protected void markForRenderUpdate() {
        if(worldObj != null) {
            worldObj.markBlockRangeForRenderUpdate(pos, pos);
        }
    }

    @Override
    public void markDirty(){
        super.markDirty();

        if(this.worldObj != null){
            IBlockState state = getWorld().getBlockState(this.pos);

            if(state != null){
                state.getBlock().updateTick(this.worldObj, this.pos, state, this.worldObj.rand);
                worldObj.notifyBlockUpdate(pos, state, state, 3);
            }
        }
    }

    protected void notifyNeighborBlockUpdate() {
        IBlockState state = worldObj.getBlockState(pos);
        if(state != null) {
            worldObj.notifyBlockOfStateChange(pos, state.getBlock());
        }
    }

    @Override
    public void update(){
        if(ticker == 0) {
            onTileLoaded();
        }
        ticker++;
    }

    public void onNeighbourBlockChanged() {
        checkRedstonePower();
    }

    public void checkRedstonePower() {
        int powerLevel = worldObj.isBlockIndirectlyGettingPowered(pos);
        if(powerLevel > 0 && !getIsRedstonePowered()) {
            redstoneChanged(true);
        }
        else if(getIsRedstonePowered() && powerLevel <= 0) {
            redstoneChanged(false);
        }
    }

    public void setOutputtingRedstone(boolean newValue) {
        setOutputtingRedstone(newValue ? 15 : 0);
    }

    public void setOutputtingRedstone(int value) {
        value = Math.max(0, value);
        value = Math.min(15, value);
        if(outputtingRedstone != value) {
            outputtingRedstone = value;
            notifyNeighborBlockUpdate();
        }
    }

    public int getOutputtingRedstone() {
        return outputtingRedstone;
    }

    protected void redstoneChanged(boolean newValue) {
        isRedstonePowered = newValue;
    }

    public boolean getIsRedstonePowered() {
        return isRedstonePowered;
    }

    public int getTicker() {
        return ticker;
    }

    protected void onTileLoaded() {
        if(!worldObj.isRemote) {
            onNeighbourBlockChanged();
        }
    }

    public List<ItemStack> getDrops() {
        return new ArrayList<ItemStack>();
    }

    public void setFacingDirection(EnumFacing direction) {
        rotation = direction;
        if(worldObj != null) {
            sendUpdatePacket();
            notifyNeighborBlockUpdate();
        }
    }

    public EnumFacing getFacingDirection() {
        return rotation;
    }

    public boolean canConnectRedstone() {
        return false;
    }
}

