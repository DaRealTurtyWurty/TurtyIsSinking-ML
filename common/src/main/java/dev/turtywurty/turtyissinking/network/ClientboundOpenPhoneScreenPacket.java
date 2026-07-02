package dev.turtywurty.turtyissinking.network;

import dev.turtywurty.turtyissinking.Constants;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jspecify.annotations.NonNull;

public final class ClientboundOpenPhoneScreenPacket implements CustomPacketPayload {
    public static final Type<ClientboundOpenPhoneScreenPacket> TYPE = new Type<>(Constants.id("open_phone_screen"));
    public static final ClientboundOpenPhoneScreenPacket INSTANCE = new ClientboundOpenPhoneScreenPacket();
    public static final StreamCodec<ByteBuf, ClientboundOpenPhoneScreenPacket> CODEC =
            StreamCodec.unit(INSTANCE);

    private ClientboundOpenPhoneScreenPacket() {
    }

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
