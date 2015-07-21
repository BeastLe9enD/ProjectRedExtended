package de.xthelegend.projectredextended.nei;


import java.util.List;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import codechicken.nei.guihook.IContainerTooltipHandler;

public class DebugTooltipHandler implements IContainerTooltipHandler {

    @Override
    public List<String> handleTooltip(GuiContainer gui, int mousex, int mousey, List<String> currenttip) {

        return currenttip;
    }

    @Override
    public List<String> handleItemDisplayName(GuiContainer gui, ItemStack itemstack, List<String> currenttip) {

        return currenttip;
    }

    @Override
    public List<String> handleItemTooltip(GuiContainer gui, ItemStack itemstack, int mousex, int mousey, List<String> currenttip) {

        // Remove comments to show the GameData name of an item when hovering over
        // it.

        // if (itemstack != null) {
        // currenttip.add("GameData name: " + GameData.getItemRegistry().getNameForObject(itemstack.getItem()));
        // int[] ids = OreDictionary.getOreIDs(itemstack);
        // if (ids.length > 0) {
        // currenttip.add("OreDict names:");
        // for (int i : ids)
        // currenttip.add(" - " + OreDictionary.getOreName(i));
        // }
        // }
        return currenttip;
    }
}