package de.beastlegend.prextended;

import de.beastlegend.prextended.proxy.ClientProxy;
import de.beastlegend.prextended.proxy.IProxy;
import de.beastlegend.prextended.proxy.ServerProxy;
import de.beastlegend.prextended.reference.Reference;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.core.jmx.Server;

/**
 * Created by BeastLe9enD on 18.07.2017.
 */

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class ProjRedExtended {
    @Mod.Instance(value = Reference.MODID)
    public static ProjRedExtended INSTANCE;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY)
    public static IProxy PROXY_INSTANCE;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event){

    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }
}
