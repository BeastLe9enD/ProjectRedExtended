package com.beastle9end.projectredxt.item;

import com.beastle9end.projectredxt.ProjectRedExtended;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

public class BasicItem extends Item {
    public BasicItem(@NotNull final Properties properties) {
        super(properties.tab(ProjectRedExtended.CREATIVE_MOD_TAB));
    }
}
