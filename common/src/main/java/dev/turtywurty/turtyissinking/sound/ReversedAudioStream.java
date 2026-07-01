package dev.turtywurty.turtyissinking.sound;

import net.minecraft.client.sounds.AudioStream;
import net.minecraft.client.sounds.FiniteAudioStream;
import org.jspecify.annotations.NonNull;

import javax.sound.sampled.AudioFormat;
import java.io.IOException;
import java.nio.ByteBuffer;

public class ReversedAudioStream implements AudioStream {
    private final ByteBuffer data;
    private final AudioFormat format;
    private final int frameSize;

    public ReversedAudioStream(FiniteAudioStream stream) throws IOException {
        try (stream) {
            this.format = stream.getFormat();
            this.frameSize = this.format.getFrameSize();
            this.data = reverseFrames(stream.readAll(), this.frameSize);
        }
    }

    private static ByteBuffer reverseFrames(ByteBuffer source, int frameSize) {
        if (frameSize <= 0)
            throw new IllegalArgumentException("Audio frame size must be positive");

        ByteBuffer input = source.slice();
        ByteBuffer output = ByteBuffer.allocateDirect(input.remaining());
        int frameCount = input.remaining() / frameSize;
        int trailingBytes = input.remaining() % frameSize;

        if (trailingBytes > 0) {
            int trailingStart = frameCount * frameSize;
            for (int index = trailingStart; index < input.limit(); index++) {
                output.put(input.get(index));
            }
        }

        for (int frame = frameCount - 1; frame >= 0; frame--) {
            int frameStart = frame * frameSize;
            for (int byteIndex = 0; byteIndex < frameSize; byteIndex++) {
                output.put(input.get(frameStart + byteIndex));
            }
        }

        output.flip();
        return output;
    }

    @Override
    public @NonNull AudioFormat getFormat() {
        return this.format;
    }

    @Override
    public @NonNull ByteBuffer read(int expectedSize) {
        if (!this.data.hasRemaining())
            return ByteBuffer.allocate(0);

        int size = Math.min(expectedSize, this.data.remaining());
        if (size < this.data.remaining()) {
            size -= size % this.frameSize;
            if (size == 0) {
                size = Math.min(this.frameSize, this.data.remaining());
            }
        }

        ByteBuffer chunk = this.data.slice();
        chunk.limit(size);
        this.data.position(this.data.position() + size);
        return chunk;
    }

    @Override
    public void close() {
    }
}
