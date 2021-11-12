package com.beastle9end.projectredxt.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class BasicCropsBlock extends CropsBlock implements IBlockRenderTypeProvider, ISuppressBlockItem {

    public BasicCropsBlock() {
        super(AbstractBlock.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public @NotNull RenderType getRenderType() {
        return RenderType.cutoutMipped();
    }
}
