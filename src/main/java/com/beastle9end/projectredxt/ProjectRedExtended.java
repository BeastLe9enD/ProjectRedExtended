package com.beastle9end.projectredxt;

import com.beastle9end.projectredxt.init.ModBlocks;
import com.beastle9end.projectredxt.init.ModContainers;
import com.beastle9end.projectredxt.init.ModFeatures;
import com.beastle9end.projectredxt.init.ModItems;
import com.beastle9end.projectredxt.itemgroup.ModItemGroup;
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

    public static final ModItemGroup ITEM_GROUP = new ModItemGroup();

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

        ModBlocks.registerRenderTypes();
        ModItems.registerItemColors();

        ModContainers.registerScreens();
    }
}