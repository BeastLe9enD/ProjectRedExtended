package com.beastle9end.projectredxt.client.gui.screen;

import com.beastle9end.projectredxt.menu.SeedBagMenu;
import com.beastle9end.projectredxt.util.Constants;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class SeedBagScreen extends BasicContainerScreen<SeedBagMenu> {
    private static final ResourceLocation CONTAINER_BACKGROUND = new ResourceLocation(Constants.MODID, "textures/gui/container/seed_bag.png");

    public SeedBagScreen(@NotNull final SeedBagMenu container, @NotNull final Inventory inventory, @NotNull final Component component) {
        super(container, inventory, component);
    }

    @Override
    protected @NotNull ResourceLocation getTextureLocation() {
        return CONTAINER_BACKGROUND;
    }
}