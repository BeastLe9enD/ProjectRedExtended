package com.beastle9end.projectredxt.block;

import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public interface IBlockRenderTypeProvider {
    @NotNull RenderType getRenderType();
}
