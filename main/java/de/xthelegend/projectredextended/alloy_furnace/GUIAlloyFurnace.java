package de.xthelegend.projectredextended.alloy_furnace;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import de.xthelegend.projectredextended.MainClass;
import de.xthelegend.projectredextended.lib.base.BaseContainer;
import de.xthelegend.projectredextended.lib.base.GUIContainerBase;

public class GUIAlloyFurnace extends GUIContainerBase {

    private static final ResourceLocation resLoc = new ResourceLocation(MainClass.MODID, "textures/gui/alloy_furnace.png");
    private final TileAlloyFurnace furnace;

    public GUIAlloyFurnace(InventoryPlayer invPlayer, TileAlloyFurnace furnace) {

        super(furnace, new ContainerAlloyFurnace(invPlayer, furnace), resLoc);
        this.furnace = furnace;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {

        super.drawGuiContainerBackgroundLayer(f, i, j);

        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;

        mc.renderEngine.bindTexture(resLoc);

        int burningPercentage = (int) (furnace.getBurningPercentage() * 13);
        if (burningPercentage > 0)
            drawTexturedModalRect(x + 22, y + 54 + 13 - burningPercentage, 177, 13 - burningPercentage, 14, burningPercentage + 1);

        int processPercentage = (int) (furnace.getProcessPercentage() * 22);
        drawTexturedModalRect(x + 103, y + 35, 178, 14, processPercentage, 15);
    }

}