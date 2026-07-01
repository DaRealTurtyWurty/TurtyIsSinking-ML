package dev.turtywurty.turtyissinking.network;

import dev.turtywurty.turtyissinking.Constants;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jspecify.annotations.NonNull;

public record ClientboundReverseJukeboxPacket(BlockPos pos, boolean reversed) implements CustomPacketPayload {
    public static final Type<ClientboundReverseJukeboxPacket> TYPE = new Type<>(Constants.id("reverse_jukebox"));

    public static final StreamCodec<ByteBuf, ClientboundReverseJukeboxPacket> CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, ClientboundReverseJukeboxPacket::pos,
            ByteBufCodecs.BOOL, ClientboundReverseJukeboxPacket::reversed,
            ClientboundReverseJukeboxPacket::new
    );

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
