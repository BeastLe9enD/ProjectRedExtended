package com.beastle9end.projectredxt.client.gui.screen;

import com.beastle9end.projectredxt.container.ImprovedBagContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class ImprovedBagScreen extends BasicContainerScreen<ImprovedBagContainer> {
    private static final ResourceLocation CONTAINER_BACKGROUND = new ResourceLocation("minecraft", "textures/gui/container/generic_54.png");

    public ImprovedBagScreen(@NotNull final ImprovedBagContainer container, @NotNull final PlayerInventory inventory, @NotNull final ITextComponent component) {
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