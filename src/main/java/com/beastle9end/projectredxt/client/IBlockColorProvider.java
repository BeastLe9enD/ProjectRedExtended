package com.beastle9end.projectredxt.client;

import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public interface IBlockColorProvider {
    @OnlyIn(Dist.CLIENT)
    @NotNull IBlockColor getBlockColor();
}
