package dev.turtywurty.turtyissinking.network;

import dev.turtywurty.turtyissinking.Constants;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jspecify.annotations.NonNull;

public record ServerboundNotifyAfkPacket(int inactiveTicks) implements CustomPacketPayload {
    public static final Type<ServerboundNotifyAfkPacket> TYPE = new Type<>(Constants.id("notify_afk"));
    public static final StreamCodec<ByteBuf, ServerboundNotifyAfkPacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, ServerboundNotifyAfkPacket::inactiveTicks,
            ServerboundNotifyAfkPacket::new
    );

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
