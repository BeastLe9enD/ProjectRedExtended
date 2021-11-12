package com.beastle9end.projectredxt.block;

import net.minecraft.block.FlowerBlock;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.potion.Effect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.PlantType;
import org.jetbrains.annotations.NotNull;

public class BaseFlowerBlock extends FlowerBlock implements IBlockRenderTypeProvider {
    private final PlantType type;

    public BaseFlowerBlock(@NotNull final Effect effect, final int duration, @NotNull final PlantType type, @NotNull final Properties properties) {
        super(effect, duration, properties);

        this.type = type;
    }

    @Override
    public @NotNull PlantType getPlantType(final IBlockReader world, final BlockPos pos) {
        return type;
    }

    @Override
    public @NotNull RenderType getRenderType() {
        return RenderType.cutoutMipped();
    }
}
