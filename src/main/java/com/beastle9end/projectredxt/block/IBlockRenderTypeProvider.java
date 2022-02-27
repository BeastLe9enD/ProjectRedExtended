package com.beastle9end.projectredxt.block;

import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public interface IBlockRenderTypeProvider {
	@OnlyIn(Dist.CLIENT)
    @NotNull RenderType getRenderType();
}
