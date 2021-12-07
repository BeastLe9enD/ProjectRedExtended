package com.beastle9end.projectredxt.item;

import com.beastle9end.projectredxt.menu.SeedBagMenu;
import com.beastle9end.projectredxt.util.ColorUtils;
import com.beastle9end.projectredxt.util.NBTUtils;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

public class SeedBagItem extends BasicBagItem implements ItemColor {
    private static final int MAX_ITEMS = 9 * 64;

    private final DyeColor color;

    public SeedBagItem(@NotNull final DyeColor color) {
        super(new Properties().stacksTo(1));

        this.color = color;
    }

    @Override
    protected boolean needsToSneak() {
        return true;
    }

    @Override
    public int getUseDuration(@NotNull final ItemStack stack) {
        int durability = MAX_ITEMS;

        if (stack.getTag() == null) {
            return durability;
        }

        final ItemStackHandler handler = NBTUtils.loadStackHandler(stack, 9);
        for (int i = 0; i < 9; i++) {
            final ItemStack currentStack = handler.getStackInSlot(i);
            if (currentStack.isEmpty()) continue;

            durability -= currentStack.getCount() * 64 / currentStack.getMaxStackSize();
        }

        return durability;
    }

    @Override
    public int getMaxDamage(@NotNull final ItemStack stack) {
        return MAX_ITEMS;
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull final UseOnContext context) {
        final Player player = context.getPlayer();
        final Level level = context.getLevel();
        final BlockPos pos = context.getClickedPos();

        final ItemStack stack = player.getMainHandItem();
        final ItemStackHandler handler = NBTUtils.loadStackHandler(stack, 9);

        int seedsPlaced = 0;

        for (int i = -2; i < 3; i++) {
            for (int j = -2; j < 3; j++) {
                final BlockPos currentPos = pos.offset(i, 0, j);
                final BlockState currentState = level.getBlockState(currentPos);

                ItemStack seedStack = ItemStack.EMPTY;
                int seedIndex = -1;
                for (int k = 0; k < handler.getSlots(); k++) {
                    final ItemStack currentStack = handler.getStackInSlot(k);
                    if (currentStack.isEmpty()) {
                        continue;
                    }

                    seedStack = currentStack;
                    seedIndex = k;
                    break;
                }

                final Block block = Block.byItem(seedStack.getItem());
                if (!(block instanceof IPlantable)) {
                    System.out.println(String.format("Block %s from item %s does not implement IPlantable", block.getRegistryName(), seedStack.getItem().getRegistryName()));
                    return InteractionResult.PASS;
                }

                final IPlantable plantable = (IPlantable) block;

                if (currentState.getBlock().canSustainPlant(currentState, level, currentPos, Direction.UP, plantable)) {
                    final BlockPos abovePos = currentPos.above();
                    if (level.getBlockState(abovePos).isAir()) {
                        level.setBlock(abovePos, block.defaultBlockState(), 0);
                        handler.extractItem(seedIndex, 1, false);
                        seedsPlaced++;

                        NBTUtils.saveStackHandler(stack, handler);
                    }
                }
            }
        }

        if (seedsPlaced == 0) {
            return InteractionResult.PASS;
        }

        if (level.isClientSide) {
            player.swing(context.getHand());
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return new TranslatableComponent(String.format("container.projectredxt.seed_bag_%s", color.getName()));
    }

    @Override
    public @NotNull AbstractContainerMenu createMenu(final int id, @NotNull final Inventory inventory, @NotNull final Player player) {
        return new SeedBagMenu(id, inventory);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public int getColor(@NotNull final ItemStack stack, final int other) {
        return ColorUtils.rgbColorFromDyeColor(color);
    }
}
