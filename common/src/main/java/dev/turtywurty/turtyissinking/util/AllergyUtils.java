package dev.turtywurty.turtyissinking.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import dev.turtywurty.turtyissinking.Constants;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class AllergyUtils {
    public static final Component ALLERGY_MESSAGE = Component.translatable(Constants.MOD_ID + ".allergy.message");

    public static final Map<Holder<MobEffect>, Float> ALLERGIC_REACTIONS = Map.of(
            MobEffects.NAUSEA, 0.95F,
            MobEffects.WEAKNESS, 0.85F,
            MobEffects.MINING_FATIGUE, 0.75F,
            MobEffects.HUNGER, 0.65F,
            MobEffects.WITHER, 0.20F,
            MobEffects.INSTANT_DAMAGE, 0.01F
    );
    public static final Codec<Identifier> IDENTIFIER_CODEC = Codec.STRING.comapFlatMap(
            value -> {
                Identifier id = Identifier.tryParse(value);
                return id != null ? DataResult.success(id) : DataResult.error(() -> "Invalid identifier: " + value);
            },
            Identifier::toString);

    private AllergyUtils() {
    }

    public static @NonNull Map<Identifier, Boolean> getDefaultAllergyFoods() {
        return new HashMap<>();
    }

    public static boolean isAllergicTo(ServerLevel level, Player player, Item item) {
        if (!item.components().has(DataComponents.CONSUMABLE))
            return false;

        Identifier itemId = BuiltInRegistries.ITEM.getKey(item);
        return hasDeterministicChance(level.getSeed(), player.getUUID(), itemId);
    }

    public static boolean hasDeterministicChance(long worldSeed, UUID playerUuid, Identifier itemId) {
        long mixed = worldSeed;
        mixed ^= playerUuid.getMostSignificantBits();
        mixed = mix64(mixed);
        mixed ^= playerUuid.getLeastSignificantBits();
        mixed = mix64(mixed);
        mixed = mixIdentifier(mixed, itemId);

        // 1 in 10 chance for each food item to be allergic.
        return Math.floorMod(mixed, 10) == 0;
    }

    private static long mixIdentifier(long seed, Identifier itemId) {
        String value = itemId.toString();
        long mixed = seed ^ value.length();
        for (int index = 0; index < value.length(); index++) {
            mixed ^= value.charAt(index);
            mixed *= 0x100000001b3L;
        }

        return mix64(mixed);
    }

    private static long mix64(long value) {
        value = (value ^ (value >>> 30)) * 0xbf58476d1ce4e5b9L;
        value = (value ^ (value >>> 27)) * 0x94d049bb133111ebL;
        return value ^ (value >>> 31);
    }

    public static @NonNull List<MobEffectInstance> getAllergyEffects(RandomSource randomSource) {
        return List.copyOf(ALLERGIC_REACTIONS.entrySet())
                .stream()
                .filter(entry -> randomSource.nextFloat() < entry.getValue())
                .map(Map.Entry::getKey)
                .map(effect -> {
                    if (effect == MobEffects.INSTANT_DAMAGE) {
                        return new MobEffectInstance(
                                effect,
                                5,
                                100
                        );
                    } else {
                        return new MobEffectInstance(
                                effect,
                                randomSource.nextInt(20 * 30, 20 * 60 * 10),
                                randomSource.nextInt(0, 5)
                        );
                    }
                })
                .toList();
    }

    public static boolean hasAllergicReaction(Level level) {
        return level.getRandom().nextFloat() < 0.95F;
    }
}
