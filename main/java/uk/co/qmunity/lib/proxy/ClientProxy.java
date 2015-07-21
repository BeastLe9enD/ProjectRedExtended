package uk.co.qmunity.lib.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;

import org.lwjgl.input.Keyboard;

import uk.co.qmunity.lib.client.renderer.RenderMultipart;
import uk.co.qmunity.lib.client.renderer.RenderPartPlacement;
import uk.co.qmunity.lib.client.renderer.RenderParticle;
import uk.co.qmunity.lib.effect.EntityFXParticle;
import uk.co.qmunity.lib.tile.TileMultipart;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @Override
    public void registerRenders() {

        RenderingRegistry.registerEntityRenderingHandler(EntityFXParticle.class, new RenderParticle());

        RenderMultipart multipartRenderer = new RenderMultipart();
        RenderingRegistry.registerBlockHandler(multipartRenderer);
        ClientRegistry.bindTileEntitySpecialRenderer(TileMultipart.class, multipartRenderer);

        RenderPartPlacement renderPartPlacement = new RenderPartPlacement();
        FMLCommonHandler.instance().bus().register(renderPartPlacement);
        MinecraftForge.EVENT_BUS.register(renderPartPlacement);
    }

    @Override
    public EntityPlayer getPlayer() {

        return Minecraft.getMinecraft().thePlayer;
    }

    @Override
    public boolean isSneakingInGui() {

        return Keyboard.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode());
    }

    private float frame = 0;

    @Override
    public float getFrame() {

        return frame;
    }

    @Override
    public void setFrame(float frame) {

        this.frame = frame;
    }

}
