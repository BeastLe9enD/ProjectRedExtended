package de.beastlegend.prextended.block;

import de.beastlegend.prextended.reference.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

/**
 * Created by BeastLe9enD on 18.07.2017.
 */
public class BlockBase extends Block {

    public BlockBase(String blockName, Material material) {
        super(material);

        blockHardness = 3.0F;
        setSoundType(SoundType.STONE);
        setRegistryName(new ResourceLocation(Reference.MODID, blockName));
    }
}
