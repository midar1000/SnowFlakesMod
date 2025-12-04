package org.midar1000.wintermod;

import net.neoforged.neoforge.common.ModConfigSpec;

public class WinterConfig {

    public static final ModConfigSpec CLIENT_SPEC;
    public static final Client CLIENT;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        CLIENT = new Client(builder);

        CLIENT_SPEC = builder.build();
    }

    public static class Client {

        public final ModConfigSpec.DoubleValue menuSnowSpeed;
        public final ModConfigSpec.DoubleValue inventorySnowSpeed;

        public final ModConfigSpec.BooleanValue enableInventorySnow;

        public final ModConfigSpec.IntValue menuSnowCount;
        public final ModConfigSpec.IntValue inventorySnowCount;

        Client(ModConfigSpec.Builder builder) {

            builder.push("snow");

            menuSnowSpeed = builder
                    .comment("Speed multiplier for snowflakes in menus")
                    .defineInRange("menuSnowSpeed", 1.0, 0.0, 10.0);

            inventorySnowSpeed = builder
                    .comment("Speed multiplier for snowflakes in inventory")
                    .defineInRange("inventorySnowSpeed", 0.5, 0.0, 10.0);

            enableInventorySnow = builder
                    .comment("Enable snowflakes inside player inventory?")
                    .define("enableInventorySnow", true);

            menuSnowCount = builder
                    .comment("Snowflake amount in menus")
                    .defineInRange("menuSnowCount", 500, 0, 2000);

            inventorySnowCount = builder
                    .comment("Snowflake amount in inventory")
                    .defineInRange("inventorySnowCount", 180, 0, 2000);

            builder.pop();
        }
    }
}
