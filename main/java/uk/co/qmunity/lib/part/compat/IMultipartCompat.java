package uk.co.qmunity.lib.part.compat;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import uk.co.qmunity.lib.part.IMicroblock;
import uk.co.qmunity.lib.part.IPart;
import uk.co.qmunity.lib.part.ITilePartHolder;
import uk.co.qmunity.lib.vec.Vec3dCube;
import uk.co.qmunity.lib.vec.Vec3i;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public interface IMultipartCompat {

    public boolean addPartToWorld(IPart part, World world, Vec3i location, boolean simulated);

    public boolean addPartToWorldBruteforce(IPart part, World world, Vec3i location);

    public boolean placePartInWorld(IPart part, World world, Vec3i location, ForgeDirection clickedFace, EntityPlayer player,
            ItemStack item, int pass, boolean simulated);

    public int getPlacementPasses();

    public boolean isMultipart(World world, Vec3i location);

    public boolean canBeMultipart(World world, Vec3i location);

    public int getStrongRedstoneOuput(World world, Vec3i location, ForgeDirection side, ForgeDirection face);

    public int getWeakRedstoneOuput(World world, Vec3i location, ForgeDirection side, ForgeDirection face);

    public boolean canConnectRedstone(World world, Vec3i location, ForgeDirection side, ForgeDirection face);

    public ITilePartHolder getPartHolder(World world, Vec3i location);

    public boolean checkOcclusion(World world, Vec3i location, Vec3dCube cube);

    public void preInit(FMLPreInitializationEvent event);

    public void init(FMLInitializationEvent event);

    public void postInit(FMLPostInitializationEvent event);

    public List<IMicroblock> getMicroblocks(World world, Vec3i location);

}
