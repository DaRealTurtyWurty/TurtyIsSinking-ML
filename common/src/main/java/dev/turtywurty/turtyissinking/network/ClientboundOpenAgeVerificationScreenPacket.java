package dev.turtywurty.turtyissinking.network;

import dev.turtywurty.turtyissinking.Constants;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public class ClientboundOpenAgeVerificationScreenPacket implements CustomPacketPayload {
    public static final Type<ClientboundOpenAgeVerificationScreenPacket> TYPE = new Type<>(Constants.id("open_age_verification_screen"));
    public static final ClientboundOpenAgeVerificationScreenPacket INSTANCE = new ClientboundOpenAgeVerificationScreenPacket();

    public static final StreamCodec<ByteBuf, ClientboundOpenAgeVerificationScreenPacket> CODEC =
            StreamCodec.unit(INSTANCE);

    private ClientboundOpenAgeVerificationScreenPacket() {
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
