package com.beastle9end.projectredxt.client.gui.screen;

import com.beastle9end.projectredxt.menu.ImprovedBagMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class ImprovedBagScreen extends BasicContainerScreen<ImprovedBagMenu> {
    private static final ResourceLocation CONTAINER_BACKGROUND = new ResourceLocation("minecraft", "textures/gui/container/generic_54.png");

    public ImprovedBagScreen(@NotNull final ImprovedBagMenu container, @NotNull final Inventory inventory, @NotNull final Component component) {
        super(container, inventory, component);

        passEvents = false;
        imageHeight = 114 + 6 * 18;
        inventoryLabelY = imageHeight - 94;
    }

    @Override
    protected @NotNull ResourceLocation getTextureLocation() {
        return CONTAINER_BACKGROUND;
    }
}