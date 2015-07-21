package de.xthelegend.projectredextended.lib.base;

import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;
import de.xthelegend.projectredextended.lib.base.BlockBase_render;

public class BaseRender extends BaseContainer {

    public BaseRender(Material material, Class<? extends BaseTile> tileEntityClass) {

        super(material, tileEntityClass);
    }

    @Override
    protected String getIconName(BlockBase_render.EnumFaceType faceType, boolean ejecting, boolean powered) {

        String iconName = textureName + "_" + faceType.toString().toLowerCase();
        if (faceType == BlockBase_render.EnumFaceType.FRONT) {
            if (ejecting)
                iconName += "_active";
        }
        return iconName;
    }

    @Override
    public boolean canConnectRedstone(IBlockAccess world, int x, int y, int z, int side) {

        BaseTile tb = (BaseTile) world.getTileEntity(x, y, z);
        if (tb == null)
            return false;

        return tb.canConnectRedstone();
    }
}