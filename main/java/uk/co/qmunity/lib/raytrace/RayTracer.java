package uk.co.qmunity.lib.raytrace;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.util.ForgeDirection;
import uk.co.qmunity.lib.part.IPart;
import uk.co.qmunity.lib.part.IPartSelectable;
import uk.co.qmunity.lib.vec.Vec3d;
import uk.co.qmunity.lib.vec.Vec3dCube;
import uk.co.qmunity.lib.vec.Vec3i;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class RayTracer {

    private static RayTracer instance = new RayTracer();

    public static RayTracer instance() {

        return instance;
    }

    private RayTracer() {

    }

    public QMovingObjectPosition rayTraceCubes(IPartSelectable part, Vec3d start, Vec3d end) {

        try {
            QMovingObjectPosition mop = rayTraceCubes(part.getSelectionBoxes(), start, end,
                    new Vec3i(((IPart) part).getX(), ((IPart) part).getY(), ((IPart) part).getZ()));
            if (mop == null)
                return null;

            return new QMovingObjectPosition(mop, part, mop.getCube());
        } catch (Exception ex) {
        }
        return null;
    }

    public QMovingObjectPosition rayTraceCubes(List<Vec3dCube> cubes, Vec3d start, Vec3d end, Vec3i blockPos) {

        if (cubes == null)
            return null;

        Vec3d start_ = start.clone().sub(blockPos.getX(), blockPos.getY(), blockPos.getZ());
        Vec3d end_ = end.clone().sub(blockPos.getX(), blockPos.getY(), blockPos.getZ());

        QMovingObjectPosition mop = rayTraceCubes(cubes, start_, end_);
        if (mop == null)
            return null;

        mop.blockX += blockPos.getX();
        mop.blockY += blockPos.getY();
        mop.blockZ += blockPos.getZ();
        mop.hitVec = mop.hitVec.addVector(blockPos.getX(), blockPos.getY(), blockPos.getZ());

        return mop;
    }

    public QMovingObjectPosition rayTraceCubes(List<Vec3dCube> cubes, Vec3d start, Vec3d end) {

        QMovingObjectPosition closest = null;
        double dist = Double.MAX_VALUE;

        for (Vec3dCube c : cubes) {
            QMovingObjectPosition mop = rayTraceCube(c, start, end);
            if (mop == null)
                continue;
            double d = mop.distanceTo(start);
            if (d < dist) {
                dist = d;
                closest = mop;
            }
        }

        return closest;
    }

    public QMovingObjectPosition rayTraceCube(Vec3dCube cube, Vec3d start, Vec3d end) {

        Vec3d closest = null;
        double dist = Double.MAX_VALUE;
        ForgeDirection f = null;

        for (ForgeDirection face : ForgeDirection.VALID_DIRECTIONS) {
            Vec3d v = rayTraceFace(cube, face, start, end);
            if (v == null)
                continue;
            double d = v.distanceTo(start);
            if (d < dist) {
                dist = d;
                closest = v;
                f = face;
            }
        }

        if (closest == null)
            return null;

        return new QMovingObjectPosition(new MovingObjectPosition(0, 0, 0, f.ordinal(), closest.toVec3()), cube);
    }

    private Vec3d rayTraceFace(Vec3dCube cube, ForgeDirection face, Vec3d start, Vec3d end) {

        Vec3d director = end.clone().sub(start).normalize();
        Vec3d normal = getNormal(face);
        Vec3d point = getPoint(cube, face).clone();

        if (normal.dot(director) == 0)
            return null;

        double t = (point.dot(normal) - start.dot(normal)) / director.dot(normal);
        double x = start.getX() + (t * director.getX());
        double y = start.getY() + (t * director.getY());
        double z = start.getZ() + (t * director.getZ());

        Vec3d v = new Vec3d(x, y, z);
        Vec3dCube f = getFace(cube, face);

        if (normal.getX() != 0) {
            if (v.getY() < f.getMinY() || v.getY() > f.getMaxY() || v.getZ() < f.getMinZ() || v.getZ() > f.getMaxZ())
                return null;
        } else if (normal.getY() != 0) {
            if (v.getX() < f.getMinX() || v.getX() > f.getMaxX() || v.getZ() < f.getMinZ() || v.getZ() > f.getMaxZ())
                return null;
        } else if (normal.getZ() != 0) {
            if (v.getX() < f.getMinX() || v.getX() > f.getMaxX() || v.getY() < f.getMinY() || v.getY() > f.getMaxY())
                return null;
        } else {
            return null;
        }

        return v;
    }

    private Vec3d getPoint(Vec3dCube cube, ForgeDirection face) {

        if (face.offsetX + face.offsetY + face.offsetZ < 0) {
            return cube.getMin();
        } else {
            return cube.getMax();
        }
    }

    private Vec3dCube getFace(Vec3dCube cube, ForgeDirection face) {

        Vec3d min = cube.getMin().clone();
        Vec3d max = cube.getMax().clone();

        switch (face) {
        case DOWN:
            max.setY(min.getY());
            break;
        case UP:
            min.setY(max.getY());
            break;
        case WEST:
            max.setX(min.getX());
            break;
        case EAST:
            min.setX(max.getX());
            break;
        case NORTH:
            max.setZ(min.getZ());
            break;
        case SOUTH:
            min.setZ(max.getZ());
            break;
        default:
            break;
        }

        return new Vec3dCube(min, max);
    }

    private Vec3d getNormal(ForgeDirection face) {

        return new Vec3d(face.offsetX, face.offsetY, face.offsetZ);
    }

    public static double overrideReachDistance = -1;

    private static double getBlockReachDistance_server(EntityPlayerMP player) {

        return player.theItemInWorldManager.getBlockReachDistance();
    }

    @SideOnly(Side.CLIENT)
    private static double getBlockReachDistance_client() {

        return Minecraft.getMinecraft().playerController.getBlockReachDistance();
    }

    public static double getBlockReachDistance(EntityPlayer player) {

        if (overrideReachDistance > 0)
            return overrideReachDistance;

        return player.worldObj.isRemote ? getBlockReachDistance_client()
                : player instanceof EntityPlayerMP ? getBlockReachDistance_server((EntityPlayerMP) player) : 5D;
    }

    public static Vec3 getCorrectedHeadVector(EntityPlayer player) {

        Vec3d v = new Vec3d(player.posX, player.posY, player.posZ);
        if (player.worldObj.isRemote) {
            v.add(0, player.getEyeHeight() - player.getDefaultEyeHeight(), 0);// compatibility with eye height changing mods
        } else {
            v.add(0, player.getEyeHeight(), 0);
            if (player instanceof EntityPlayerMP && player.isSneaking())
                v.sub(0, 0.08, 0);
        }
        return v.toVec3();
    }

    public static Vec3d getStartVector(EntityPlayer player) {

        return new Vec3d(getCorrectedHeadVector(player));
    }

    public static Vec3d getEndVector(EntityPlayer player) {

        Vec3 headVec = getCorrectedHeadVector(player);
        Vec3 lookVec = player.getLook(1.0F);
        double reach = getBlockReachDistance(player);
        return new Vec3d(headVec.addVector(lookVec.xCoord * reach, lookVec.yCoord * reach, lookVec.zCoord * reach));
    }

}
