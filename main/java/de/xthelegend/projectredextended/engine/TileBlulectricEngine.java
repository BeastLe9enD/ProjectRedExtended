package de.xthelegend.projectredextended.engine;

import scala.collection.immutable.Range;
import scala.ref.WeakReference;
import codechicken.lib.data.MCDataInput;
import codechicken.lib.vec.BlockCoord;
import codechicken.lib.vec.Vector3;
import codechicken.multipart.TMultiPart;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import mrtjp.projectred.api.IConnectable;
import mrtjp.projectred.core.PowerConductor;
import mrtjp.projectred.core.TPowerDrawPoint;
import mrtjp.projectred.expansion.TPoweredMachine;
import mrtjp.projectred.expansion.TileProcessingMachine;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileBlulectricEngine extends TileEntity implements IEnergyProvider,IEnergyHandler {

	public EnergyStorage storage = new EnergyStorage(100000, 1024);
	public static int energyStored = 0;
	public static int maxEnergy = 100000;
	
	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		if(from == ForgeDirection.UP)
			return true;
		else return false;
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract,
			boolean simulate) {
		if(from == ForgeDirection.UP);
		{
			return 1024;
		}
		
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return energyStored;
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return maxEnergy;
	}
	
	
	public boolean update = true;
	@Override
	public void updateEntity() 
	{
		
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive,
			boolean simulate) {
		return 0;
	}



}
