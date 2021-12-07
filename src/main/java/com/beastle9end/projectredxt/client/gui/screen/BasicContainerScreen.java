package com.beastle9end.projectredxt.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public abstract class BasicContainerScreen<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {
    public BasicContainerScreen(@NotNull final T container, @NotNull final Inventory inventory, @NotNull final Component component) {
        super(container, inventory, component);
    }

    protected abstract @NotNull ResourceLocation getTextureLocation();

    @Override
    public void render(@NotNull final PoseStack poseStack, final int mouseX, final int mouseY, final float partialTicks) {
        renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTicks);
        renderTooltip(poseStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(@NotNull final PoseStack poseStack, final float partialTicks, final int mouseX, final int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, getTextureLocation());

        final int offsetX = (width - imageWidth) / 2, offsetY = (height - imageHeight) / 2;
        blit(poseStack, offsetX, offsetY, 0, 0, imageWidth, imageHeight);
    }
}
