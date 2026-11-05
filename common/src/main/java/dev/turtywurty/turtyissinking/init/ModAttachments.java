package dev.turtywurty.turtyissinking.init;

import com.mojang.serialization.Codec;
import dev.turtywurty.turtyissinking.Constants;
import dev.turtywurty.turtyissinking.util.AllergyUtils;
import dev.turtywurty.turtymultiloader.attachment.AttachmentType;
import dev.turtywurty.turtymultiloader.attachment.Attachments;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.Identifier;

import java.util.HashMap;
import java.util.Map;

public final class ModAttachments {
    public static final AttachmentType<Boolean> ZOMBIE_67ING = Attachments.register(
            Constants.id("zombie_67ing"), builder -> builder.defaultFactory(() -> false)
                    .syncToTrackers(ByteBufCodecs.BOOL));

    public static final AttachmentType<Boolean> VILLAGER_NOSE_SNIPPED = Attachments.register(
            Constants.id("villager_nose_snipped"), builder -> builder.defaultFactory(() -> false)
                    .persistent(Codec.BOOL)
                    .syncToTrackers(ByteBufCodecs.BOOL));

    public static final AttachmentType<Map<Identifier, Boolean>> ALLERGIC_ITEMS = Attachments.register(
            Constants.id("allergic_items"), builder -> builder.defaultFactory(AllergyUtils::getDefaultAllergyFoods)
                    .persistent(Codec.unboundedMap(AllergyUtils.IDENTIFIER_CODEC, Codec.BOOL)
                            .xmap(HashMap::new, Map::copyOf))
                    .copyOnDeath());

    private ModAttachments() {
    }

    public static void load() {
    }
}
