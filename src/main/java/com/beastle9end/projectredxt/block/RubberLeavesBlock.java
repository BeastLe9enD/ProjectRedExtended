package com.beastle9end.projectredxt.block;

import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class RubberLeavesBlock extends LeavesBlock implements BlockColor, ItemColor {

    public RubberLeavesBlock() {
        super(BlockBehaviour.Properties.of(Material.LEAVES)
                .strength(0.2F)
                .sound(SoundType.GRASS)
                .noOcclusion()
                .isValidSpawn((state, reader, pos, entityType) -> entityType == EntityType.OCELOT || entityType == EntityType.PARROT)
                .isSuffocating((state, reader, pos) -> false)
                .isViewBlocking((state, reader, pos) -> false));
    }

    @Override
    public boolean isRandomlyTicking(@NotNull final BlockState state) {
        return false;
    }

    @Override
    public void tick(@NotNull final BlockState state, @NotNull final ServerLevel level, @NotNull final BlockPos pos, @NotNull final Random random) {
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public int getColor(@NotNull final BlockState state, @NotNull final BlockAndTintGetter getter, @NotNull final BlockPos pos, final int other) {
        return BiomeColors.getAverageFoliageColor(getter, pos);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public int getColor(@NotNull final ItemStack stack, final int other) {
        return 0x19BF13;
    }
}
