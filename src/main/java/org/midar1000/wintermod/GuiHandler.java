package org.midar1000.wintermod;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.options.OptionsScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.bus.api.SubscribeEvent;

public class GuiHandler {

    private SnowDrop[] activeSnowDrops = new SnowDrop[0];

    @SubscribeEvent
    public void onGuiDraw(ScreenEvent.Render.Post event) {

        Screen screen = event.getScreen();
        int screenWidth = screen.width;
        int screenHeight = screen.height;

        if (screenWidth <= 0 || screenHeight <= 0)
            return;

        boolean isMenu =
                screen instanceof TitleScreen ||
                        screen instanceof OptionsScreen ||
                        screen instanceof JoinMultiplayerScreen;

        boolean isInventory =
                screen instanceof InventoryScreen ||
                        screen instanceof CreativeModeInventoryScreen;

        if (isMenu) {

            int count = WinterConfig.CLIENT.menuSnowCount.get();
            double speed = WinterConfig.CLIENT.menuSnowSpeed.get();

            ensureSnowflakes(count, screenWidth, screenHeight, speed);
            drawSnow(activeSnowDrops, event, screenWidth, screenHeight);

        } else if (isInventory && WinterConfig.CLIENT.enableInventorySnow.get()) {

            int count = WinterConfig.CLIENT.inventorySnowCount.get();
            double speed = WinterConfig.CLIENT.inventorySnowSpeed.get();

            ensureSnowflakes(count, screenWidth, screenHeight, speed);
            drawSnow(activeSnowDrops, event, screenWidth, screenHeight);

        } else {
            clearSnowflakes();
        }
    }

    private void ensureSnowflakes(int count, int width, int height, double speedMultiplier) {
        if (activeSnowDrops.length != count) {
            activeSnowDrops = new SnowDrop[count];
            for (int i = 0; i < count; i++) {
                activeSnowDrops[i] = new SnowDrop(width, height, speedMultiplier);
            }
        }
    }

    private void drawSnow(SnowDrop[] snowDrops, ScreenEvent.Render.Post event, int width, int height) {

        double mouseX = event.getMouseX();
        double mouseY = event.getMouseY();

        for (int i = 0; i < snowDrops.length; i++) {
            SnowDrop snowDrop = snowDrops[i];

            if (snowDrop != null) {

                snowDrop.updatePosition(width, height, mouseX, mouseY);
                snowDrop.draw(event.getGuiGraphics());

                if (snowDrop.isDead()) {
                    snowDrops[i] = new SnowDrop(width, height, snowDrop.speedMultiplier);
                }
            }
        }
    }

    private void clearSnowflakes() {
        activeSnowDrops = new SnowDrop[0];
    }
}
