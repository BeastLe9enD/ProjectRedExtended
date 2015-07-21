package de.xthelegend.projectredextended.seed_bag;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import de.xthelegend.projectredextended.MainClass;
import de.xthelegend.projectredextended.lib.base.GUIContainerBase;

public class GUISeedBag extends GUIContainerBase {

    private static final ResourceLocation resLoc = new ResourceLocation(MainClass.MODID, "textures/gui/seedBag.png");

    public GUISeedBag(ItemStack bag, IInventory playerInventory, IInventory seedBagInventory) {

        super(seedBagInventory, new ContainerSeedBag(bag, playerInventory, seedBagInventory), resLoc);
    }
}