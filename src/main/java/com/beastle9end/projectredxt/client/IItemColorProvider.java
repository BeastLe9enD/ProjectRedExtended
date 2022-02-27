package com.beastle9end.projectredxt.client;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public interface IItemColorProvider {
    @OnlyIn(Dist.CLIENT)
    @NotNull IItemColor getItemColor();
}
