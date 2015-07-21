package de.xthelegend.projectredextended.api;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import uk.co.qmunity.lib.part.IPart;


import cpw.mods.fml.common.Loader;
import de.xthelegend.projectredextended.alloy_furnace.IAlloyFurnaceRegistry;


public class projectRedExtendedAPI {

    private static IBPApi instance;

    public static IBPApi getInstance() {

        return instance;
    }

    public static interface IBPApi {

     

        public IAlloyFurnaceRegistry getAlloyFurnaceRegistry();

     
    }


    public static void init(IBPApi inst) {

        if (instance == null && Loader.instance().activeModContainer().getModId().equals("zprojectxtended")) {
            instance = inst;
        } else {
            throw new IllegalStateException("Called-only by ProjectRed Extended");
        }
    }
}