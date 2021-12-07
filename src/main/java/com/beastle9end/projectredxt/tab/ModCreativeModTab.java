package com.beastle9end.projectredxt.tab;

import com.beastle9end.projectredxt.init.ModBlocks;
import com.beastle9end.projectredxt.util.Constants;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ModCreativeModTab extends CreativeModeTab {
    public ModCreativeModTab() {
        super(Constants.MODID);
    }

    @Override
    public @NotNull ItemStack makeIcon() {
        return new ItemStack(ModBlocks.INDIGO_FLOWER.get());
    }
}
