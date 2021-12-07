package com.beastle9end.projectredxt.item;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

public abstract class BasicBagItem extends BasicItem implements MenuProvider {
    public BasicBagItem(@NotNull final Item.Properties properties) {
        super(properties);
    }

    protected boolean needsToSneak() {
        return false;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull final Level level, @NotNull final Player player, @NotNull final InteractionHand hand) {
        final ItemStack stack = player.getMainHandItem();

        if (!level.isClientSide && (player.isShiftKeyDown() || !needsToSneak())) {
            NetworkHooks.openGui((ServerPlayer) player, this);
            return InteractionResultHolder.success(stack);
        }

        return InteractionResultHolder.pass(stack);
    }
}
