package com.beastle9end.projectredxt.client.gui.screen;

import com.beastle9end.projectredxt.container.SeedBagContainer;
import com.beastle9end.projectredxt.util.Constants;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class SeedBagScreen extends BasicContainerScreen<SeedBagContainer> {
    private static final ResourceLocation CONTAINER_BACKGROUND = new ResourceLocation(Constants.MODID, "textures/gui/container/seed_bag.png");

    public SeedBagScreen(@NotNull final SeedBagContainer container, @NotNull final PlayerInventory inventory, @NotNull final ITextComponent component) {
        super(container, inventory, component);
    }

    @Override
    protected @NotNull ResourceLocation getTextureLocation() {
        return CONTAINER_BACKGROUND;
    }
}