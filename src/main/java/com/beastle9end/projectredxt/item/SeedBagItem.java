package com.beastle9end.projectredxt.item;

import com.beastle9end.projectredxt.container.SeedBagContainer;
import com.beastle9end.projectredxt.util.MathUtils;
import com.beastle9end.projectredxt.util.NBTUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

public class SeedBagItem extends BasicBagItem implements IItemColor {
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
    public double getDurabilityForDisplay(@NotNull final ItemStack stack) {
        double fullness = 0.0D;

        final ItemStackHandler handler = NBTUtils.loadStackHandler(stack, 9);
        for (int i = 0; i < 9; i++) {
            final ItemStack currentStack = handler.getStackInSlot(i);
            if (currentStack.isEmpty()) continue;

            fullness += MathUtils.ONE_OVER_NINE * currentStack.getCount() / (double) currentStack.getMaxStackSize();
        }

        return 1.0D - fullness;
    }

    @Override
    public boolean showDurabilityBar(@NotNull final ItemStack stack) {
        return stack.getTag() != null;
    }

    @Override
    public int getMaxDamage(@NotNull final ItemStack stack) {
        return MAX_ITEMS;
    }

    @Override
    public ActionResultType useOn(@NotNull final ItemUseContext context) {
        final PlayerEntity player = context.getPlayer();
        final World world = context.getLevel();
        final BlockPos pos = context.getClickedPos();

        final ItemStack stack = player.getMainHandItem();
        final ItemStackHandler handler = NBTUtils.loadStackHandler(stack, 9);

        int seedsPlaced = 0;

        for (int i = -2; i < 3; i++) {
            for (int j = -2; j < 3; j++) {
                final BlockPos currentPos = pos.offset(i, 0, j);
                final BlockState currentState = world.getBlockState(currentPos);

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
                    return ActionResultType.PASS;
                }

                final IPlantable plantable = (IPlantable) block;

                if (currentState.getBlock().canSustainPlant(currentState, world, currentPos, Direction.UP, plantable)) {
                    final BlockPos abovePos = currentPos.above();
                    if (world.getBlockState(abovePos).isAir(world, abovePos)) {
                        world.setBlock(abovePos, block.defaultBlockState(), 0);
                        handler.extractItem(seedIndex, 1, false);
                        seedsPlaced++;

                        NBTUtils.saveStackHandler(stack, handler);
                    }
                }
            }
        }

        if (seedsPlaced == 0) {
            return ActionResultType.PASS;
        }

        if(world.isClientSide) {
            player.swing(context.getHand());
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public @NotNull ITextComponent getDisplayName() {
        return new TranslationTextComponent(String.format("container.projectredxt.seed_bag_%s", color.getName()));
    }

    @Override
    public @NotNull Container createMenu(final int id, @NotNull final PlayerInventory inventory, @NotNull final PlayerEntity player) {
        return new SeedBagContainer(id, inventory);
    }

    @Override
    public int getColor(@NotNull final ItemStack stack, final int other) {
        return color.getColorValue();
    }
}
