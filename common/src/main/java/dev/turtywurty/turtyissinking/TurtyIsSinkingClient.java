package dev.turtywurty.turtyissinking;

import dev.turtywurty.turtyissinking.init.client.ModEntityRenderers;
import dev.turtywurty.turtyissinking.network.ModClientNetworking;
import dev.turtywurty.turtyissinking.util.ClientAfkTracker;
import dev.turtywurty.turtymultiloader.event.client.ClientEvents;

public class TurtyIsSinkingClient {
    public static void init() {
        ModEntityRenderers.load();
        ModClientNetworking.register();
        ClientEvents.onEndClientTick(_ -> ClientAfkTracker.tick());
    }
}
