package de.xthelegend.projectredextended.newpart;


import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import de.xthelegend.projectredextended.MainClass;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class TileBlulectricEngineRenderer extends TileEntitySpecialRenderer {

	
	IModelCustom modelBase = AdvancedModelLoader.loadModel(new ResourceLocation(MainClass.MODID, "textures\\obj\\engineBase.obj"));
	IModelCustom modelSlide= AdvancedModelLoader.loadModel(new ResourceLocation(MainClass.MODID, "textures\\obj\\engineSlide.obj"));
	IModelCustom modelGear= AdvancedModelLoader.loadModel(new ResourceLocation(MainClass.MODID, "textures\\obj\\engineGear.obj"));
	
	ResourceLocation texture = new ResourceLocation(MainClass.MODID, "textures\\obj\blulectricEngine.png");
	
	public TileBlulectricEngineRenderer()
	{
		
	}
	
	@Override
	public void renderTileEntityAt(TileEntity entity, double x,
			double y, double z, float rotation) {
		//bindTexture(texture);
		
		TileBlulectricEngine tileEngine = (TileBlulectricEngine) entity;
		Tessellator tessellator = Tessellator.instance;
		
		
		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef((float) x + 0.5F, (float) y + 0.0F, (float) z + 0.5F);
		GL11.glScalef(0.09375F, 0.09375F, 0.09375F);
		
		tessellator.startDrawingQuads();
		
		modelBase.renderAll();
		
		
		if(tileEngine.Active)
		{
			rotation /= (float) tileEngine.PumpTick;
			
			if(tileEngine.PumpSpeed > 0)
			{
				rotation /= (float) tileEngine.PumpSpeed;
			}
		}
		else
		{
			rotation = 0.0F;
		}
		
		modelGear.renderAll();
		modelSlide.renderAll();
		
	}


}
