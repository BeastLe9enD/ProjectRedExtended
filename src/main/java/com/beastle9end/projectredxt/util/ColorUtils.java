package com.beastle9end.projectredxt.util;

import net.minecraft.item.DyeColor;
import net.minecraft.util.text.TextFormatting;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;

public class ColorUtils {
    private static final EnumMap<DyeColor, TextFormatting> dyeColorToFormatting = new EnumMap<DyeColor, TextFormatting>(DyeColor.class);

    static {
        dyeColorToFormatting.put(DyeColor.WHITE, TextFormatting.WHITE);
        dyeColorToFormatting.put(DyeColor.ORANGE, TextFormatting.GOLD);
        dyeColorToFormatting.put(DyeColor.MAGENTA, TextFormatting.LIGHT_PURPLE);
        dyeColorToFormatting.put(DyeColor.LIGHT_BLUE, TextFormatting.AQUA);
        dyeColorToFormatting.put(DyeColor.YELLOW, TextFormatting.YELLOW);
        dyeColorToFormatting.put(DyeColor.LIME, TextFormatting.GREEN);
        dyeColorToFormatting.put(DyeColor.PINK, TextFormatting.RED);
        dyeColorToFormatting.put(DyeColor.GRAY, TextFormatting.DARK_GRAY);
        dyeColorToFormatting.put(DyeColor.LIGHT_GRAY, TextFormatting.GRAY);
        dyeColorToFormatting.put(DyeColor.CYAN, TextFormatting.DARK_AQUA);
        dyeColorToFormatting.put(DyeColor.PURPLE, TextFormatting.DARK_PURPLE);
        dyeColorToFormatting.put(DyeColor.BLUE, TextFormatting.DARK_BLUE);
        dyeColorToFormatting.put(DyeColor.BROWN, TextFormatting.BLUE); //TODO:
        dyeColorToFormatting.put(DyeColor.GREEN, TextFormatting.DARK_GREEN);
        dyeColorToFormatting.put(DyeColor.RED, TextFormatting.DARK_RED);
        dyeColorToFormatting.put(DyeColor.BLACK, TextFormatting.BLACK);
    }

    public static @NotNull TextFormatting formattingFromDyeColor(@NotNull DyeColor color) {
        return dyeColorToFormatting.get(color);
    }
}
