package com.beastle9end.projectredxt.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import org.jetbrains.annotations.NotNull;

public class MathUtils {
    public static final double ONE_OVER_NINE = 1.0D / 9.0D;

    public static @NotNull BlockPos vec3dToBlockPos(@NotNull Vector3d v) {
        return new BlockPos((int) v.x(), (int) v.y(), (int) v.z());
    }

    public static @NotNull Vector3d blockPosToVec3d(@NotNull BlockPos pos) {
        return new Vector3d(pos.getX(), pos.getY(), pos.getZ());
    }
}
