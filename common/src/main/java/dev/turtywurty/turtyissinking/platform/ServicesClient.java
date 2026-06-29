package dev.turtywurty.turtyissinking.platform;

import dev.turtywurty.turtyissinking.platform.services.client.IClientRegistryHelper;

public final class ServicesClient {
    public static final IClientRegistryHelper REGISTRY = Services.load(IClientRegistryHelper.class);

    private ServicesClient() {
    }
}