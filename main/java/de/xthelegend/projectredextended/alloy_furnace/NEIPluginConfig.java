package de.xthelegend.projectredextended.alloy_furnace;


import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.guihook.GuiContainerManager;
import de.xthelegend.projectredextended.MainClass;




public class NEIPluginConfig implements IConfigureNEI {

    @Override
    public void loadConfig() {

        API.registerUsageHandler(new AlloyFurnaceNEICompability());
        API.registerRecipeHandler(new AlloyFurnaceNEICompability());
        //API.registerGuiOverlayHandler(GuiProjectTable.class, new ProjectTableOverlayHandler(), "crafting");

    }

    @Override
    public String getName() {

        return "PRNEIHandler";
    }

    @Override
    public String getVersion() {

        return MainClass.MODID + "-" +  MainClass.VERSION;
    }

}