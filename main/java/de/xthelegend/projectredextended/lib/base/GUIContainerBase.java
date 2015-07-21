package de.xthelegend.projectredextended.lib.base;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import invtweaks.api.container.ChestContainer;
import invtweaks.api.container.ContainerSection;
import invtweaks.api.container.ContainerSectionCallback;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;
import codechicken.nei.VisiblityData;
import codechicken.nei.api.INEIGuiHandler;

import cpw.mods.fml.common.Optional;




import codechicken.nei.api.TaggedInventoryArea;



import de.xthelegend.projectredextended.MainClass;
import de.xthelegend.projectredextended.project_table.BlockProjectTable;
import de.xthelegend.projectredextended.project_table.ContainerProjectTable;
import de.xthelegend.projectredextended.project_table.TileProjectTable;
import de.xthelegend.projectredextended.project_table.GUIProjectTable;
import de.xthelegend.projectredextended.project_table.SlotProjectTableCrafting;
import uk.co.qmunity.lib.client.gui.GuiContainerBase;
import uk.co.qmunity.lib.client.gui.widget.GuiAnimatedStat;
import uk.co.qmunity.lib.client.gui.widget.IGuiWidget;
import uk.co.qmunity.lib.ref.Dependencies;
import de.xthelegend.projectredextended.lib.RequiredImports;
import de.xthelegend.projectredextended.lib.WidgetTabItemLister;

@Optional.Interface(iface = "codechicken.nei.api.INEIGuiHandler", modid = RequiredImports.NEI)
public class GUIContainerBase extends GuiContainerBase implements INEIGuiHandler {

    public GUIContainerBase(Container mainContainer, ResourceLocation _resLoc) {

        super(mainContainer, _resLoc);
    }

    public GUIContainerBase(IInventory inventory, Container mainContainer, ResourceLocation _resLoc) {

        super(inventory, mainContainer, _resLoc);
    }

    @Override
    public void initGui() {

        super.initGui();
        lastLeftStat = lastRightStat = null;

        /*if (inventory instanceof TileMachineBase) {
            WidgetTabItemLister backlogTab = new WidgetTabItemLister(this, "gui.bluepower:tab.stuffed", Refs.MODID
                    + ":textures/gui/widgets/gui_stuffed.png", guiLeft + xSize, guiTop + 5, 0xFFc13d40, null, false);
            lastRightStat = backlogTab;
            backlogTab.setItems(((TileMachineBase) inventory).getBacklog());
            addWidget(backlogTab);
        }*/

        String unlocalizedInfo = inventory.getInventoryName() + ".info";
        String localizedInfo = I18n.format(unlocalizedInfo);
        if (!unlocalizedInfo.equals(localizedInfo)) {
            addAnimatedStat("gui.bluepower:tab.info", MainClass.MODID + ":textures/gui/widgets/gui_info.png", 0xFF8888FF, isInfoStatLeftSided()).setText(
                    unlocalizedInfo);

        }
    }

    // -----------NEI support

    @Override
    @Optional.Method(modid = RequiredImports.NEI)
    public VisiblityData modifyVisiblity(GuiContainer gui, VisiblityData currentVisibility) {

        for (IGuiWidget widget : widgets) {
            if (widget instanceof GuiAnimatedStat) {
                GuiAnimatedStat stat = (GuiAnimatedStat) widget;
                if (stat.isLeftSided()) {
                    if (stat.getWidth() > 20) {
                        currentVisibility.showUtilityButtons = false;
                        currentVisibility.showStateButtons = false;
                    }
                } else {
                    if (stat.getAffectedY() < 10) {
                        currentVisibility.showWidgets = false;
                    }
                }
            }
        }
        return currentVisibility;
    }

    /**
     * NEI will give the specified item to the InventoryRange returned if the player's inventory is full. return null for no range
     */
    @Override
    public Iterable<Integer> getItemSpawnSlots(GuiContainer gui, ItemStack item) {

        return null;
    }

    /**
     * @return A list of TaggedInventoryAreas that will be used with the savestates.
     */
    @Override
    @Optional.Method(modid = RequiredImports.NEI)
    public List<TaggedInventoryArea> getInventoryAreas(GuiContainer gui) {

        return null;
    }

    /**
     * Handles clicks while an itemstack has been dragged from the item panel. Use this to set configurable slots and the like. Changes made to the
     * stackSize of the dragged stack will be kept
     *
     * @param gui
     *            The current gui instance
     * @param mousex
     *            The x position of the mouse
     * @param mousey
     *            The y position of the mouse
     * @param draggedStack
     *            The stack being dragged from the item panel
     * @param button
     *            The button presed
     * @return True if the drag n drop was handled. False to resume processing through other routes. The held stack will be deleted if
     *         draggedStack.stackSize == 0
     */
    @Override
    public boolean handleDragNDrop(GuiContainer gui, int mousex, int mousey, ItemStack draggedStack, int button) {

        return false;
    }

    /**
     * Used to prevent the item panel from drawing on top of other gui elements.
     *
     * @param x
     *            The x coordinate of the rectangle bounding the slot
     * @param y
     *            The y coordinate of the rectangle bounding the slot
     * @param w
     *            The w coordinate of the rectangle bounding the slot
     * @param h
     *            The h coordinate of the rectangle bounding the slot
     * @return true if the item panel slot within the specified rectangle should not be rendered.
     */
    @Override
    public boolean hideItemPanelSlot(GuiContainer gui, int x, int y, int w, int h) {

        for (IGuiWidget stat : widgets) {
            if (stat.getBounds().intersects(new Rectangle(x, y, w, h)))
                return true;
        }
        return false;
    }
}