package com.beastle9end.projectredxt.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

public abstract class BasicBagItem extends BasicItem implements INamedContainerProvider {
    public BasicBagItem(@NotNull Properties properties) {
        super(properties);
    }

    protected boolean needsToSneak() {
        return false;
    }

    @Override
    public @NotNull ActionResult<ItemStack> use(@NotNull final World world, @NotNull final PlayerEntity player, @NotNull final Hand hand) {
        final ItemStack stack = player.getMainHandItem();

        if (!world.isClientSide && (player.isShiftKeyDown() || !needsToSneak())) {
            NetworkHooks.openGui((ServerPlayerEntity) player, this);
            return ActionResult.success(stack);
        }

        return ActionResult.pass(stack);
    }
}
