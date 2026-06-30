package dev.turtywurty.turtyissinking.init;

import com.mojang.serialization.Codec;
import dev.turtywurty.turtyissinking.Constants;
import dev.turtywurty.turtyissinking.util.AllergyUtils;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.resources.Identifier;

import java.util.Map;

public final class FabricAttachments {
    public static final AttachmentType<Map<Identifier, Boolean>> ALLERGIC_ITEMS = AttachmentRegistry.create(
            Constants.id("allergic_items"),
            builder -> builder.persistent(Codec.unboundedMap(AllergyUtils.IDENTIFIER_CODEC, Codec.BOOL))
                    .copyOnDeath()
                    .initializer(AllergyUtils::getDefaultAllergyFoods)
                    .buildAndRegister(Constants.id("allergic_items")));

    private FabricAttachments() {
    }

    public static void load() {
    }
}
