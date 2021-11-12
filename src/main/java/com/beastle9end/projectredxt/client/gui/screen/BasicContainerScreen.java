package com.beastle9end.projectredxt.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public abstract class BasicContainerScreen<T extends Container> extends ContainerScreen<T> implements IHasContainer<T> {
    public BasicContainerScreen(@NotNull final T container, @NotNull final PlayerInventory inventory, @NotNull final ITextComponent component) {
        super(container, inventory, component);
    }

    protected abstract @NotNull ResourceLocation getTextureLocation();

    @Override
    public void render(@NotNull final MatrixStack matrixStack, final int mouseX, final int mouseY, final float partialTicks) {
        renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(@NotNull final MatrixStack matrixStack, final float partialTicks, final int mouseX, final int mouseY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        getMinecraft().getTextureManager().bind(getTextureLocation());

        final int offsetX = (width - imageWidth) / 2, offsetY = (height - imageHeight) / 2;
        blit(matrixStack, offsetX, offsetY, 0, 0, imageWidth, imageHeight);
    }
}
