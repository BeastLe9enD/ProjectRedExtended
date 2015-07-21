package de.xthelegend.projectredextended.api;

import de.xthelegend.projectredextended.alloy_furnace.AlloyFurnaceRegistry;
import de.xthelegend.projectredextended.alloy_furnace.IAlloyFurnaceRegistry;


import de.xthelegend.projectredextended.api.projectRedExtendedAPI.IBPApi;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import uk.co.qmunity.lib.part.IPart;

public class RedExtendedAPI implements IBPApi 
{


    @Override
    public IAlloyFurnaceRegistry getAlloyFurnaceRegistry() {

        return AlloyFurnaceRegistry.getInstance();
    }

    

}