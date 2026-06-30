package dev.turtywurty.turtyissinking.init;

import com.mojang.serialization.Codec;
import dev.turtywurty.turtyissinking.Constants;
import dev.turtywurty.turtyissinking.util.AllergyUtils;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Map;

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

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Map<Identifier, Boolean>>> ALLERGIC_ITEMS = ATTACHMENT_TYPES.register(
            "allergic_items",
            () -> AttachmentType.builder(AllergyUtils::getDefaultAllergyFoods)
                    .serialize(Codec.unboundedMap(AllergyUtils.IDENTIFIER_CODEC, Codec.BOOL).fieldOf("values"))
                    .copyOnDeath()
                    .build());

    private NeoForgeAttachments() {
    }

    public static void load(IEventBus eventBus) {
        ATTACHMENT_TYPES.register(eventBus);
    }
}
