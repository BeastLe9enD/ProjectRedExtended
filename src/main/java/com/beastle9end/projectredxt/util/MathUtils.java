package com.beastle9end.projectredxt.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class MathUtils {
    public static @NotNull BlockPos vec3dToBlockPos(@NotNull Vec3 v) {
        return new BlockPos((int) v.x, (int) v.y, (int) v.z);
    }

    public static @NotNull Vec3 blockPosToVec3d(@NotNull BlockPos pos) {
        return new Vec3(pos.getX(), pos.getY(), pos.getZ());
    }
}
