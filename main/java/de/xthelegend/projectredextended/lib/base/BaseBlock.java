package de.xthelegend.projectredextended.lib.base;

import de.xthelegend.projectredextended.MainClass;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;

public abstract class BaseBlock extends BlockDirectional {

	public BaseBlock(Material material, String name) {
		super(material);
		// TODO Auto-generated constructor stub

        blockHardness = 3.0F;
		setStepSound(soundTypeStone);
	}

}
