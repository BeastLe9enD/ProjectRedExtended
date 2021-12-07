package com.beastle9end.projectredxt.block;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraftforge.common.PlantType;
import org.jetbrains.annotations.NotNull;

public class BaseFlowerBlock extends FlowerBlock implements BlockRenderTypeProvider {
    private final PlantType type;

    public BaseFlowerBlock(@NotNull final MobEffect effect, final int duration, @NotNull final PlantType type, @NotNull final Properties properties) {
        super(effect, duration, properties);

        this.type = type;
    }

    @Override
    public @NotNull PlantType getPlantType(final BlockGetter world, final BlockPos pos) {
        return type;
    }

    @Override
    public @NotNull RenderType getRenderType() {
        return RenderType.cutoutMipped();
    }
}
