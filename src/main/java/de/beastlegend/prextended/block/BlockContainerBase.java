package de.beastlegend.prextended.block;

import de.beastlegend.prextended.tile.TileEntityBase;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Map;

/**
 * Created by BeastLe9enD on 18.07.2017.
 */
public class BlockContainerBase extends BlockBase implements ITileEntityProvider {

    @SideOnly(Side.CLIENT)
    protected Map<String, TextureAtlasSprite> textures;

    private Class<? extends TileEntityBase> tileEntityClass;
    private boolean isRedstoneEmitter;
    private boolean isSilkyRemoving;

    public BlockContainerBase(String blockName, Material material, Class<? extends TileEntityBase> tileEntityClass) {
        super(blockName, material);
        this.tileEntityClass = tileEntityClass;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return null;
    }
}
