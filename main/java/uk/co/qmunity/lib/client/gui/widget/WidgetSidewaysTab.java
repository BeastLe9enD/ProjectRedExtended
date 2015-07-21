package uk.co.qmunity.lib.client.gui.widget;

import java.util.Arrays;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import org.lwjgl.opengl.GL11;

public class WidgetSidewaysTab extends BaseWidget {

    private final int singleTabWidth;
    private final int tabAmount;
    public final boolean[] enabledTabs;

    public WidgetSidewaysTab(int id, int x, int y, int width, int height, int textureU, int tabAmount, String textureLoc) {

        super(id, x, y, width * tabAmount, height, textureU, 0, textureLoc);
        singleTabWidth = width;
        this.tabAmount = tabAmount;
        enabledTabs = new boolean[tabAmount];
        Arrays.fill(enabledTabs, true);
    }

    @Override
    public void onMouseClicked(int mouseX, int mouseY, int button) {

        int clickedTab = (mouseX - x) / singleTabWidth;
        if (enabledTabs[clickedTab]) {
            value = clickedTab;
            super.onMouseClicked(mouseX, mouseY, button);
        }
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTick) {

        if (textures.length > 0)
            Minecraft.getMinecraft().getTextureManager().bindTexture(textures[0]);

        for (int i = 0; i < tabAmount; i++) {
            if (i == value) {
                GL11.glColor4d(1, 1, 1, 1);
            } else {
                if (enabledTabs[i]) {
                    GL11.glColor4d(0.6, 0.6, 0.6, 1);
                } else {
                    GL11.glColor4d(0.2, 0.2, 0.2, 1);
                }
            }
            Gui.func_146110_a(x + singleTabWidth * i, y, getTextureU(), getTextureV() + singleTabWidth * i, singleTabWidth, height, 256,
                    256);
        }
    }

    @Override
    public void addTooltip(int mouseX, int mouseY, List<String> curTip, boolean shiftPressed) {

        addTooltip((mouseX - x) / singleTabWidth, curTip, shiftPressed);
    }

    protected void addTooltip(int hoveredTab, List<String> curTip, boolean shiftPressed) {

    }
}
