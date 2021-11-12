package com.beastle9end.projectredxt.itemgroup;

import com.beastle9end.projectredxt.init.ModBlocks;
import com.beastle9end.projectredxt.util.Constants;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ModItemGroup extends ItemGroup {
    public ModItemGroup() {
        super(Constants.MODID);
    }

    @Override
    public @NotNull ItemStack makeIcon() {
        return new ItemStack(ModBlocks.INDIGO_FLOWER.get());
    }
}
