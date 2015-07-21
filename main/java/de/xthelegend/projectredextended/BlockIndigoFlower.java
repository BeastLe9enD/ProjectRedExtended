package de.xthelegend.projectredextended;

import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockFlower;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.EnumPlantType;



import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockIndigoFlower extends BlockBush {

    public static final String[] field_149858_b = new String[] { "indigo_flower" };
    @SideOnly(Side.CLIENT)
    private IIcon icon;

    // private int meta;

    public BlockIndigoFlower(String name) {

        super();
        this.setHardness(0.0F);
        this.setStepSound(soundTypeGrass);
        this.setBlockName(MainClass.MODID + ":" + "indigo_flower");
        setCreativeTab(MainClass.tab_exploration);
    }

    
    String getUnwrappedUnlocalizedName(String name) {

        return name.substring(name.indexOf(".") + 1);
    }

    public static BlockFlower func_149857_e(String name) {

        String[] astring = field_149858_b;
        int i = astring.length;
        int j;
        String s1;

        for (j = 0; j < i; ++j) {
            s1 = astring[j];

            if (s1.equals(name)) {
                return (BlockFlower) MainClass.BlockIndigoFlower;
            }
        }
        return null;
    }

    public static int func_149856_f(String name) {

        int i;

        for (i = 0; i < field_149858_b.length; ++i) {
            if (field_149858_b[i].equals(name)) {
                return i;
            }
        }
        return 0;
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z) {

        return EnumPlantType.Plains;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta) {

        return this.icon;
    }

    @Override
    public int damageDropped(int damage) {

        return damage;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {

        this.icon = iconRegister.registerIcon(MainClass.MODID + ":" + "indigo_flower");
    }
}
