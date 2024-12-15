package org.midar1000.wintermod;

import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.bus.api.IEventBus;

@Mod(Snowtime.MOD_ID)
public class Snowtime {
    public static final String MOD_ID = "wintermod";

    public Snowtime(IEventBus modEventBus) {
        modEventBus.addListener(this::setupCommon);
        modEventBus.addListener(this::setupClient);
        NeoForge.EVENT_BUS.register(new GuiHandler());
    }

    private void setupCommon(FMLCommonSetupEvent event) {
    }

    private void setupClient(FMLClientSetupEvent event) {
    }
}
