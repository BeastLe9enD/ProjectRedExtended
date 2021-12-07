package com.beastle9end.projectredxt;

import com.beastle9end.projectredxt.client.ClientRegistry;
import com.beastle9end.projectredxt.init.ModBlocks;
import com.beastle9end.projectredxt.init.ModFeatures;
import com.beastle9end.projectredxt.init.ModItems;
import com.beastle9end.projectredxt.init.ModMenus;
import com.beastle9end.projectredxt.tab.ModCreativeModTab;
import com.beastle9end.projectredxt.util.Constants;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Constants.MODID)
public class ProjectRedExtended {
    public static final Logger LOGGER = LogManager.getLogger();

    public static final ModCreativeModTab CREATIVE_MOD_TAB = new ModCreativeModTab();

    public ProjectRedExtended() {
        final IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(this::onSetup);
        modBus.addListener(this::onSetupClient);

        ModBlocks.REGISTRY.register(modBus);
        ModItems.REGISTRY.register(modBus);

        final IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        forgeBus.addListener(EventPriority.HIGH, ModFeatures::onBiomeLoad);
    }

    private void onSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Setup common");
    }

    private void onSetupClient(final FMLClientSetupEvent event) {
        LOGGER.info("Setup client");

        ClientRegistry.register();

        ModMenus.registerScreens();
    }
}
