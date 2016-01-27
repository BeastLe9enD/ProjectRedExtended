package de.xthelegend.projectredextended;

import cpw.mods.fml.client.registry.RenderingRegistry;
import de.xthelegend.projectredextended.lib.base.BlockBase_render;

public class ClientProxy extends ServerProxy{
	
	public static void preInitClientProxy()
	{
		RenderingRegistry.registerBlockHandler(new BlockBase_render());
	}
	
	public static void InitClientProxy()
	{
		
	}
	
	public static void postInitClientProxy()
	{
		
	}



}
