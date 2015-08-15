package de.xthelegend.projectredextended;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockLittleMarbleBricks extends Block{

	/*protected*/public BlockLittleMarbleBricks(Material p_i45394_1_) {
		super(p_i45394_1_);
		this.setHardness(0.8F);
        this.setBlockName(MainClass.MODID + ":" + "marblebrick_little");
        this.setBlockTextureName(MainClass.MODID + ":" + "marblebrick_little");
        setCreativeTab(MainClass.tab_exploration);
		// TODO Auto-generated constructor stub
	}

}
