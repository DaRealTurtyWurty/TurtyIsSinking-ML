package dev.turtywurty.turtyissinking.network;

import dev.turtywurty.turtyissinking.Constants;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jspecify.annotations.NonNull;

public record ServerboundAgeVerificationPacket(boolean verified) implements CustomPacketPayload {
    public static final Type<ServerboundAgeVerificationPacket> TYPE = new Type<>(Constants.id("age_verification"));
    public static final StreamCodec<ByteBuf, ServerboundAgeVerificationPacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, ServerboundAgeVerificationPacket::verified,
            ServerboundAgeVerificationPacket::new
    );

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
