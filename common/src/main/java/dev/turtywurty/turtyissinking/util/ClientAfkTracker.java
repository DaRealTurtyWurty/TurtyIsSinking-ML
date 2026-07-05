package dev.turtywurty.turtyissinking.util;

import dev.turtywurty.turtyissinking.network.ServerboundNotifyAfkPacket;
import dev.turtywurty.turtyissinking.platform.Services;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public final class ClientAfkTracker {
    private static final int AFK_TICKS = 30 * 20;

    private static int inactiveTicks;
    private static boolean afk;

    public static void markActive() {
        inactiveTicks = 0;
        afk = false;
    }

    public static void tick() {
        inactiveTicks++;
        afk = inactiveTicks >= AFK_TICKS;

        Level level = Minecraft.getInstance().level;
        Player player = Minecraft.getInstance().player;
        if (level != null && player != null && isAfk() && inactiveTicks % (5 * 20) == 0) {
            Services.NETWORK.sendToServer(new ServerboundNotifyAfkPacket(inactiveTicks));
        }
    }

    public static boolean isAfk() {
        return afk;
    }
}