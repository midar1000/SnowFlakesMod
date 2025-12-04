package org.midar1000.wintermod;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

import java.util.Random;

public class SnowDrop {

    private static final ResourceLocation SNOW_TEXTURE =
            ResourceLocation.tryParse("wintermod:textures/snowflake.png");

    private final Random random = new Random();
    public final double speedMultiplier;

    private double x, y;
    private int textureX, textureY;
    private int size;
    private double speed;
    private boolean dead;

    private double swayAmplitude;
    private double swayOffset;

    public SnowDrop(int width, int height, double speedMultiplier) {

        this.speedMultiplier = speedMultiplier;

        this.x = random.nextDouble() * (width - 16);
        this.y = -random.nextInt(height / 2) - 16;

        this.size = 16;

        int snowflakeIndex = random.nextInt(12);
        this.textureX = (snowflakeIndex % 4) * size;
        this.textureY = (snowflakeIndex / 4) * size;

        this.speed = (0.5 + random.nextDouble() * 0.8) * speedMultiplier;

        this.swayAmplitude = random.nextDouble() * 1.0 + 0.3;
        this.swayOffset = random.nextDouble() * Math.PI * 2;
    }

    public void updatePosition(int width, int height, double mouseX, double mouseY) {

        // Движение вниз
        this.y += speed;

        // Колебания
        this.x += Math.sin((this.y / 80.0) + swayOffset) * swayAmplitude;

        // TORUS-режим по оси X (выход → вход с другой стороны)
        if (this.x < -size) {
            this.x = width;
        } else if (this.x > width) {
            this.x = -size;
        }

        // Отталкивание от мышки
        double distance = Math.sqrt(Math.pow(this.x - mouseX, 2) + Math.pow(this.y - mouseY, 2));
        if (distance < 35) {
            double pushStrength = 3.5;
            double angle = Math.atan2(this.y - mouseY, this.x - mouseX);
            this.x += Math.cos(angle) * pushStrength;
            this.y += Math.sin(angle) * pushStrength;

            // Повторная проверка тороидального перехода
            if (this.x < -size) this.x = width;
            else if (this.x > width) this.x = -size;
        }

        // Умерла при падении вниз
        if (this.y > height + size) {
            this.dead = true;
        }
    }

    public void draw(GuiGraphics guiGraphics) {
        guiGraphics.blit(SNOW_TEXTURE, (int) x, (int) y, textureX, textureY, size, size);
    }

    public boolean isDead() {
        return dead;
    }
}
