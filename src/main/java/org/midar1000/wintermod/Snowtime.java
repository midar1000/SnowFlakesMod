package org.midar1000.wintermod;

import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.config.ModConfig;

@Mod(Snowtime.MOD_ID)
public class Snowtime {
    public static final String MOD_ID = "wintermod";

    public Snowtime(IEventBus modEventBus) {

        modEventBus.addListener(this::setupCommon);
        modEventBus.addListener(this::setupClient);

        ModLoadingContext.get()
                .getActiveContainer()
                .registerConfig(ModConfig.Type.CLIENT, WinterConfig.CLIENT_SPEC, "wintermod-client.toml");

        NeoForge.EVENT_BUS.register(new GuiHandler());
    }

    private void setupCommon(FMLCommonSetupEvent event) {
    }

    private void setupClient(FMLClientSetupEvent event) {
    }
}
