package com.beastle9end.projectredxt.block;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class BasicCropBlock extends CropBlock implements BlockRenderTypeProvider, SuppressBlockItem {

    public BasicCropBlock() {
        super(BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public @NotNull RenderType getRenderType() {
        return RenderType.cutoutMipped();
    }
}
