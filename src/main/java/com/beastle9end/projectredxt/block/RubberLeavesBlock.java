package com.beastle9end.projectredxt.block;

import com.beastle9end.projectredxt.client.IBlockColorProvider;
import com.beastle9end.projectredxt.client.IItemColorProvider;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.BiomeColors;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class RubberLeavesBlock extends LeavesBlock implements IBlockColorProvider, IItemColorProvider {

    public RubberLeavesBlock() {
        super(AbstractBlock.Properties.of(Material.LEAVES)
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
    public void tick(@NotNull final BlockState state, @NotNull final ServerWorld world, @NotNull final BlockPos pos, @NotNull final Random random) {}

    @Override
    public void randomTick(@NotNull final BlockState state, @NotNull final ServerWorld world, @NotNull final BlockPos pos, @NotNull final Random random) {}

    @Override
    public @NotNull IBlockColor getBlockColor() {
        return (state, reader, pos, other) -> BiomeColors.getAverageFoliageColor(reader, pos);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public @NotNull IItemColor getItemColor() {
        return (stack, other) -> 0x19BF13;
    }
}
