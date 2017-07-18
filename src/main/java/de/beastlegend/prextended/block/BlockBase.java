package de.beastlegend.prextended.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

/**
 * Created by BeastLe9enD on 18.07.2017.
 */
public class BlockBase extends Block {
    protected String name;
    public BlockBase(String blockName, Material material) {
        super(material);

        blockHardness = 3.0F;
        setSoundType(SoundType.STONE);
    }
}
