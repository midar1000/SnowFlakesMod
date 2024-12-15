package org.midar1000.wintermod;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.options.OptionsScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.bus.api.SubscribeEvent;

public class GuiHandler {
    private SnowDrop[] activeSnowDrops;

    public GuiHandler() {
        activeSnowDrops = new SnowDrop[0];
    }

    @SubscribeEvent
    public void onGuiDraw(ScreenEvent.Render.Post event) {
        Screen screen = event.getScreen();

        // Проверяем высоту и ширину экрана
        int screenWidth = screen.width;
        int screenHeight = screen.height;

        if (screenHeight <= 0 || screenWidth <= 0) {
            System.out.println("Ошибка: Неверные размеры экрана.");
            return;
        }

        // Проверяем, нужно ли показывать снежинки
        if (screen instanceof TitleScreen || screen instanceof OptionsScreen || screen instanceof JoinMultiplayerScreen) {
            ensureSnowflakes(500, screenWidth, screenHeight);
            drawSnow(activeSnowDrops, event, screenWidth, screenHeight, event.getMouseX(), event.getMouseY());
        } else if (screen instanceof InventoryScreen || screen instanceof CreativeModeInventoryScreen) {
            ensureSnowflakes(180, screenWidth, screenHeight);
            drawSnow(activeSnowDrops, event, screenWidth, screenHeight, event.getMouseX(), event.getMouseY());
        } else {
            clearSnowflakes();
        }
    }

    private void ensureSnowflakes(int count, int width, int height) {
        if (activeSnowDrops.length != count) {
            activeSnowDrops = new SnowDrop[count];
            for (int i = 0; i < activeSnowDrops.length; i++) {
                activeSnowDrops[i] = new SnowDrop(width, height);
            }
        }
    }

    private void drawSnow(SnowDrop[] snowDrops, ScreenEvent.Render.Post event, int width, int height, double mouseX, double mouseY) {
        for (int i = 0; i < snowDrops.length; i++) {
            SnowDrop snowDrop = snowDrops[i];

            if (snowDrop != null) {
                snowDrop.updatePosition(width, height, mouseX, mouseY);

                snowDrop.draw(event.getGuiGraphics());

                if (snowDrop.isDead()) {
                    snowDrops[i] = new SnowDrop(width, height);
                }
            }
        }
    }

    private void clearSnowflakes() {
        activeSnowDrops = new SnowDrop[0];
    }
}
