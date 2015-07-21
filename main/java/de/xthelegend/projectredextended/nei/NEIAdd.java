package de.xthelegend.projectredextended.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.guihook.GuiContainerManager;
import de.xthelegend.projectredextended.MainClass;
import de.xthelegend.projectredextended.alloy_furnace.AlloyFurnaceNEICompability;
import de.xthelegend.projectredextended.project_table.GUIProjectTable;

public class NEIAdd implements IConfigureNEI {

	
	
    public  void loadConfig() {

        API.registerUsageHandler(new AlloyFurnaceNEICompability());
        API.registerRecipeHandler(new AlloyFurnaceNEICompability());
        //API.registerGuiOverlayHandler(GUIProjectTable.class, new ProjectTableOverlayHandler(), "crafting");
        GuiContainerManager.addTooltipHandler(new DebugTooltipHandler());

    }

    @Override
    public String getName() {

        return "PRNEIHandler";
    }

    @Override
    public String getVersion() {

        return MainClass.VERSION;
    }

}