package dev.turtywurty.turtyissinking.init;

import dev.turtywurty.turtyissinking.Constants;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public final class NeoForgeAttachments {
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, Constants.MOD_ID);

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Boolean>> ZOMBIE_67ING =
            ATTACHMENT_TYPES.register("zombie_67ing", () -> AttachmentType.builder(() -> false)
                    .sync(ByteBufCodecs.BOOL)
                    .build());

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Boolean>> VILLAGER_NOSE_SNIPPED =
            ATTACHMENT_TYPES.register("villager_nose_snipped", () -> AttachmentType.builder(() -> false)
                    .sync(ByteBufCodecs.BOOL)
                    .build());

    private NeoForgeAttachments() {
    }

    public static void load(IEventBus eventBus) {
        ATTACHMENT_TYPES.register(eventBus);
    }
}
