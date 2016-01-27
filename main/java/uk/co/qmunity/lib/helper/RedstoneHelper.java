package uk.co.qmunity.lib.helper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockDropper;
import net.minecraft.block.BlockLever;
import net.minecraft.block.BlockNote;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockRedstoneComparator;
import net.minecraft.block.BlockRedstoneLight;
import net.minecraft.block.BlockRedstoneRepeater;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.BlockTNT;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityComparator;
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import uk.co.qmunity.lib.part.compat.IMultipartCompat;
import uk.co.qmunity.lib.part.compat.MultipartSystem;
import uk.co.qmunity.lib.vec.Vec3i;

public class RedstoneHelper {

    public static int getVanillaSignalStrength(World world, int x, int y, int z, ForgeDirection side, ForgeDirection face) {

        if (face != ForgeDirection.DOWN && face != ForgeDirection.UNKNOWN)
            return 0;

        Block block = world.getBlock(x, y, z);

        if (block == Blocks.redstone_wire) {
            if (side == ForgeDirection.DOWN)
                return world.getBlockMetadata(x, y, z);
            if (side == ForgeDirection.UP)
                return 0;
            int d = Direction.getMovementDirection(side.offsetX, side.offsetZ);
            if (BlockRedstoneWire.isPowerProviderOrWire(world, x, y, z, d)
                    || BlockRedstoneWire.isPowerProviderOrWire(world, x + side.offsetX + side.offsetX, y + side.offsetY + side.offsetY, z
                            + side.offsetZ + side.offsetZ, (d + 2) % 4)) {
                return world.getBlockMetadata(x, y, z);
            }
        }
        if (block instanceof BlockRedstoneComparator)

            if (block == Blocks.unpowered_repeater) {
                return 0;
            }
        if (block == Blocks.powered_repeater) {
            if (side == ForgeDirection.DOWN || side == ForgeDirection.UP)
                return 0;
            int d = Direction.getMovementDirection(side.offsetX, side.offsetZ);
            return d == (world.getBlockMetadata(x, y, z) % 4) ? 15 : 0;
        }
        if (block instanceof BlockRedstoneComparator) {
            if (side == ForgeDirection.DOWN || side == ForgeDirection.UP)
                return 0;
            int d = Direction.getMovementDirection(side.offsetX, side.offsetZ);
            return d == (world.getBlockMetadata(x, y, z) % 4) ? ((TileEntityComparator) world.getTileEntity(x, y, z)).getOutputSignal() : 0;
        }

        return 0;
    }

    public static boolean canConnectVanilla(World world, int x, int y, int z, ForgeDirection side, ForgeDirection face) {

        if (side == ForgeDirection.UNKNOWN)
            return false;

        Block block = world.getBlock(x, y, z);
        int meta = world.getBlockMetadata(x, y, z);
        int d = Direction.getMovementDirection(side.offsetX, side.offsetZ);

        if ((block == Blocks.unpowered_repeater || block == Blocks.powered_repeater)
                && (face == ForgeDirection.DOWN || face == ForgeDirection.UNKNOWN))
            if (d % 2 == meta % 2)
                return true;

        if (block instanceof BlockLever) {
            meta = meta % 8;
            ForgeDirection leverFace = ((meta == 0 || meta == 7) ? ForgeDirection.UP : ((meta == 5 || meta == 6) ? ForgeDirection.DOWN
                    : (meta == 1 ? ForgeDirection.WEST : (meta == 2 ? ForgeDirection.EAST : (meta == 3 ? ForgeDirection.NORTH
                            : (meta == 4 ? ForgeDirection.SOUTH : ForgeDirection.UNKNOWN))))));
            if (face != ForgeDirection.UNKNOWN && face != leverFace)
                return false;
            return side != leverFace.getOpposite();
        }

        if (block instanceof BlockRedstoneComparator && (face == ForgeDirection.DOWN || face == ForgeDirection.UNKNOWN))
            return side != ForgeDirection.UP;

        if (block instanceof BlockRedstoneWire)
            return face == ForgeDirection.UNKNOWN || face == ForgeDirection.DOWN;

        return block instanceof BlockDoor || block instanceof BlockRedstoneLight || block instanceof BlockTNT
                || block instanceof BlockDispenser || block instanceof BlockDropper || block instanceof BlockNote
                || block instanceof BlockPistonBase;// true;
    }

    private static boolean isVanillaBlock(World world, int x, int y, int z) {

        Block b = world.getBlock(x, y, z);
        return b instanceof BlockRedstoneRepeater || b instanceof BlockLever || b instanceof BlockRedstoneWire
                || b instanceof BlockRedstoneComparator || b instanceof BlockDoor || b instanceof BlockRedstoneLight
                || b instanceof BlockTNT || b instanceof BlockDispenser || b instanceof BlockDropper || b instanceof BlockNote
                || b instanceof BlockPistonBase;
    }

    public static int getOutputWeak(World world, int x, int y, int z, ForgeDirection side, ForgeDirection face) {

        Vec3i location = new Vec3i(x, y, z);

        for (MultipartSystem s : MultipartSystem.getAvailableSystems()) {
            IMultipartCompat compat = s.getCompat();
            if (compat.isMultipart(world, location))
                return compat.getWeakRedstoneOuput(world, location, side, face);
        }

        Block block = world.getBlock(x, y, z);

        int power = block.isProvidingWeakPower(world, x, y, z, side.getOpposite().ordinal());
        if (power > 0)
            return power;

        if (block.isNormalCube(world, x, y, z) && block.isOpaqueCube()) {
            for (ForgeDirection d : ForgeDirection.VALID_DIRECTIONS) {
                if (d == side)
                    continue;
                power = Math.max(power,
                        getOutputStrong(world, x + d.offsetX, y + d.offsetY, z + d.offsetZ, d.getOpposite(), ForgeDirection.UNKNOWN));
            }
        }

        return power;
    }

    public static int getOutputStrong(World world, int x, int y, int z, ForgeDirection side, ForgeDirection face) {

        Vec3i location = new Vec3i(x, y, z);

        for (MultipartSystem s : MultipartSystem.getAvailableSystems()) {
            IMultipartCompat compat = s.getCompat();
            if (compat.isMultipart(world, location))
                return compat.getStrongRedstoneOuput(world, location, side, face);
        }

        int power = getVanillaSignalStrength(world, x, y, z, side, face);
        if (power > 0)
            return power;

        return world.getBlock(x, y, z).isProvidingStrongPower(world, x, y, z, side.getOpposite().ordinal());
    }

    public static int getOutputWeak(World world, int x, int y, int z, ForgeDirection side) {

        return getOutputWeak(world, x, y, z, side, ForgeDirection.UNKNOWN);
    }

    public static int getOutputStrong(World world, int x, int y, int z, ForgeDirection side) {

        return getOutputStrong(world, x, y, z, side, ForgeDirection.UNKNOWN);
    }

    public static int getOutput(World world, int x, int y, int z, ForgeDirection side) {

        return Math.max(getOutputWeak(world, x, y, z, side), getOutputStrong(world, x, y, z, side));
    }

    public static int getOutput(World world, int x, int y, int z, ForgeDirection side, ForgeDirection face) {

        return Math.max(getOutputWeak(world, x, y, z, side, face), getOutputStrong(world, x, y, z, side, face));
    }

    public static int getOutput(World world, int x, int y, int z) {

        int power = 0;
        for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS)
            power = Math.max(power, getOutput(world, x, y, z, side));
        return power;
    }

    public static int getInputWeak(World world, int x, int y, int z, ForgeDirection side, ForgeDirection face) {

        return getOutputWeak(world, x + side.offsetX, y + side.offsetY, z + side.offsetZ, side.getOpposite(), face);
    }

    public static int getInputStrong(World world, int x, int y, int z, ForgeDirection side, ForgeDirection face) {

        return getOutputStrong(world, x + side.offsetX, y + side.offsetY, z + side.offsetZ, side.getOpposite(), face);
    }

    public static int getInputWeak(World world, int x, int y, int z, ForgeDirection side) {

        return getOutputWeak(world, x + side.offsetX, y + side.offsetY, z + side.offsetZ, side.getOpposite());
    }

    public static int getInputStrong(World world, int x, int y, int z, ForgeDirection side) {

        return getOutputStrong(world, x + side.offsetX, y + side.offsetY, z + side.offsetZ, side.getOpposite());
    }

    public static int getInput(World world, int x, int y, int z, ForgeDirection side) {

        return getOutput(world, x + side.offsetX, y + side.offsetY, z + side.offsetZ, side.getOpposite());
    }

    public static int getInput(World world, int x, int y, int z, ForgeDirection side, ForgeDirection face) {

        return getOutput(world, x + side.offsetX, y + side.offsetY, z + side.offsetZ, side.getOpposite(), face);
    }

    public static int getInput(World world, int x, int y, int z) {

        int power = 0;
        for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS)
            power = Math.max(power, getInput(world, x, y, z, side));
        return power;
    }

    public static boolean canConnect(World world, int x, int y, int z, ForgeDirection side, ForgeDirection face) {

        Vec3i location = new Vec3i(x, y, z);

        if (isVanillaBlock(world, x, y, z))
            return canConnectVanilla(world, x, y, z, side, face);

        for (MultipartSystem s : MultipartSystem.getAvailableSystems()) {
            IMultipartCompat compat = s.getCompat();
            if (compat.isMultipart(world, location))
                return compat.canConnectRedstone(world, location, side, face);
        }

        try {
            return world.getBlock(x, y, z).canConnectRedstone(world, x, y, z, Direction.getMovementDirection(side.offsetX, side.offsetZ));
        } catch (Exception ex) {
            // ex.printStackTrace();
        }
        return false;
    }

    public static boolean canConnect(World world, int x, int y, int z, ForgeDirection side) {

        return canConnect(world, x, y, z, side, ForgeDirection.UNKNOWN);
    }

    public static void notifyRedstoneUpdate(World world, int x, int y, int z, ForgeDirection direction, boolean strong) {

        int x_ = x + direction.offsetX;
        int y_ = y + direction.offsetY;
        int z_ = z + direction.offsetZ;

        if (world == null)
            return;

        Block block = world.getBlock(x, y, z);

        // Weak/strong
        world.notifyBlockOfNeighborChange(x_, y_, z_, block);

        // Strong
        if (strong)
            for (ForgeDirection d : ForgeDirection.VALID_DIRECTIONS)
                if (d != direction.getOpposite())
                    world.notifyBlockOfNeighborChange(x_ + d.offsetX, y_ + d.offsetY, z_ + d.offsetZ, block);
    }

	

}
