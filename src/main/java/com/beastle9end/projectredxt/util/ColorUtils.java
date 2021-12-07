package com.beastle9end.projectredxt.util;

import net.minecraft.world.item.DyeColor;
import org.jetbrains.annotations.NotNull;

public class ColorUtils {
    public static int rgbColorFromDyeColor(@NotNull final DyeColor color) {
        final float[] diffuseColors = color.getTextureDiffuseColors();

        int r = (int) (diffuseColors[0] * 255.0F);
        int g = (int) (diffuseColors[1] * 255.0F);
        int b = (int) (diffuseColors[2] * 255.0F);

        return r << 16 | g << 8 | b;
    }
}
