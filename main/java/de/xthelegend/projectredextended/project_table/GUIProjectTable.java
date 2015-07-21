package de.xthelegend.projectredextended.project_table;

import java.util.List;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import uk.co.qmunity.lib.client.gui.widget.IGuiWidget;
import uk.co.qmunity.lib.client.gui.widget.WidgetMode;



import de.xthelegend.projectredextended.MainClass;
import de.xthelegend.projectredextended.lib.base.GUIContainerBase;
import de.xthelegend.projectredextended.networking.MainNetworkHandler;
import de.xthelegend.projectredextended.networking.msg.MessageGuiUpdate;

/**
 * @author MineMaarten
 */
public class GUIProjectTable extends GUIContainerBase  {

    private static final ResourceLocation resLoc = new ResourceLocation(MainClass.MODID, "textures/gui/project_table.png");
    private final TileProjectTable projectTable;

    public GUIProjectTable(InventoryPlayer invPlayer, TileProjectTable projectTable) {

        super(projectTable, new ContainerProjectTable(invPlayer, projectTable), resLoc);
        this.projectTable = projectTable;
        ySize = 208;
    }

    /*@Override
    public void initGui() {

        super.initGui();
        addWidget(new WidgetMode(0, guiLeft + 15, guiTop + 20, 176, 1, MainClass.MODID + ":textures/gui/project_table.png") {

            @Override
            public void addTooltip(int mouseX, int mouseY, List<String> curTip, boolean shift) {

                curTip.add("gui.bluepower:projectTable.clearGrid");
            }
        });
    }*/

    @Override
    public void actionPerformed(IGuiWidget button) {

        MainNetworkHandler.INSTANCE.sendToServer(new MessageGuiUpdate(projectTable, 0, 0));
    }
}