package org.midar1000.wintermod;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

import java.util.Random;

public class SnowDrop {
    private static final ResourceLocation SNOW_TEXTURE = ResourceLocation.tryParse("wintermod:textures/snowflake.png");

    private final Random random;
    private double x, y; // Используем double для более точных расчётов
    private int textureX, textureY; // Координаты текстуры снежинки
    private int size; // Размер снежинки
    private double speed; // Скорость падения
    private boolean dead;

    private double swayAmplitude; // Амплитуда подёргивания
    private double swayOffset;    // Смещение для синусоиды

    public SnowDrop(int width, int height) {
        this.random = new Random();

        // Равномерное появление по ширине экрана сверху
        this.x = random.nextDouble() * (width - 16); // Случайная позиция по X в пределах экрана
        this.y = -random.nextInt(height / 2) - 16; // Рандомное распределение выше экрана

        this.size = 16;

        int snowflakeIndex = random.nextInt(12); // Случайная текстура снежинки
        this.textureX = (snowflakeIndex % 4) * size;
        this.textureY = (snowflakeIndex / 4) * size;

        // Уменьшение скорости падения
        this.speed = 0.5 + random.nextDouble() * 0.8; // Скорость падения (0.5 - 1.3)

        // Лёгкое подёргивание
        this.swayAmplitude = random.nextDouble() * 1.0 + 0.3; // Амплитуда (0.3 - 1.3 пикселя)
        this.swayOffset = random.nextDouble() * Math.PI * 2; // Начальное смещение
    }

    public void updatePosition(int width, int height, double mouseX, double mouseY) {
        // Движение вниз
        this.y += speed;

        // Лёгкое колебание влево и вправо
        this.x += Math.sin((this.y / 80.0) + swayOffset) * swayAmplitude;

        // Убедимся, что X остаётся в пределах экрана (на случай ошибок)
        this.x = Math.max(0, Math.min(width - size, this.x));

        // Отталкивание от мышки
        double distance = Math.sqrt(Math.pow(this.x - mouseX, 2) + Math.pow(this.y - mouseY, 2));
        if (distance < 35) { // Радиус отталкивания пикселей
            double pushStrength = 3.5; // Сила отталкивания
            double angle = Math.atan2(this.y - mouseY, this.x - mouseX);
            this.x += Math.cos(angle) * pushStrength;
            this.y += Math.sin(angle) * pushStrength;

            // Ограничение для X после отталкивания
            this.x = Math.max(0, Math.min(width - size, this.x));
        }

        // Проверка выхода за нижний край экрана
        if (this.y > height + size) {
            this.dead = true; // Умирает, если упала ниже экрана
        }
    }

    public void draw(GuiGraphics guiGraphics) {
        if (dead) return;

        // Рисуем снежинку
        guiGraphics.blit(SNOW_TEXTURE, (int) this.x, (int) this.y, this.textureX, this.textureY, size, size);
    }

    public boolean isDead() {
        return this.dead;
    }
}
