package de.xthelegend.projectredextended.lib;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import uk.co.qmunity.lib.client.gui.widget.GuiAnimatedStat;
import uk.co.qmunity.lib.client.gui.widget.IGuiAnimatedStat;



public class WidgetTabItemLister extends GuiAnimatedStat {


    private static final int MAX_ITEMS_X = 5;

    public WidgetTabItemLister(GuiScreen gui, int backgroundColor) {

        super(gui, backgroundColor);
    }

    public WidgetTabItemLister(GuiScreen gui, int backgroundColor, ItemStack icon) {

        super(gui, backgroundColor, icon);
    }

    public WidgetTabItemLister(GuiScreen gui, int backgroundColor, String texture) {

        super(gui, backgroundColor, texture);
    }

    public WidgetTabItemLister(GuiScreen gui, String title, ItemStack icon, int xPos, int yPos, int backGroundColor,
            IGuiAnimatedStat affectingStat, boolean leftSided) {

        super(gui, title, icon, xPos, yPos, backGroundColor, affectingStat, leftSided);
    }

    public WidgetTabItemLister(GuiScreen gui, String title, String texture, int xPos, int yPos, int backGroundColor,
            IGuiAnimatedStat affectingStat, boolean leftSided) {

        super(gui, title, texture, xPos, yPos, backGroundColor, affectingStat, leftSided);
    }



    

   

}