package de.xthelegend.projectredextended.lib;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import de.xthelegend.projectredextended.MainClass;
import de.xthelegend.projectredextended.alloy_furnace.ContainerAlloyFurnace;
import de.xthelegend.projectredextended.alloy_furnace.GUIAlloyFurnace;
import de.xthelegend.projectredextended.alloy_furnace.TileAlloyFurnace;
import de.xthelegend.projectredextended.lib.base.GUI_NUMBER_IDS;
import de.xthelegend.projectredextended.project_table.ContainerProjectTable;
import de.xthelegend.projectredextended.project_table.GUIProjectTable;
import de.xthelegend.projectredextended.project_table.TileProjectTable;
import de.xthelegend.projectredextended.seed_bag.ContainerSeedBag;
import de.xthelegend.projectredextended.seed_bag.GUISeedBag;
import de.xthelegend.projectredextended.seed_bag.ItemSeedBag;
import de.xthelegend.projectredextended.seed_bag.lib.InvItem;

public class GUIHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

        // This function creates a container
        TileEntity ent = world.getTileEntity(x, y, z);
        // ID is the GUI ID
        switch (GUI_NUMBER_IDS.values()[ID]) {
        case ALLOY_FURNACE:
            return new ContainerAlloyFurnace(player.inventory, (TileAlloyFurnace) ent);
        case PROJECT_TABLE:
            return new ContainerProjectTable(player.inventory, (TileProjectTable) ent);
        case SEED_BAG:
            if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ItemSeedBag) {
                return new ContainerSeedBag(player.getCurrentEquippedItem(), player.inventory, InvItem.getItemInventory(player,
                        player.getCurrentEquippedItem(), "Seed Bag", 9));
            }
            break;
        
        default:
            break;
        }
        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

        TileEntity ent = world.getTileEntity(x, y, z);
        // ID is the GUI ID
        switch (GUI_NUMBER_IDS.values()[ID]) {
        case ALLOY_FURNACE:
            return new GUIAlloyFurnace(player.inventory, (TileAlloyFurnace) ent);
        case PROJECT_TABLE:
            return new GUIProjectTable(player.inventory, (TileProjectTable) ent);
        
        case SEED_BAG:
            if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ItemSeedBag) {
                return new GUISeedBag(player.getCurrentEquippedItem(), player.inventory, InvItem.getItemInventory(player,
                        player.getCurrentEquippedItem(), MainClass.ItemSeedBag.getUnlocalizedName(), 9));
            }
            
        default:
            break;
        }
        return null;
    }
}