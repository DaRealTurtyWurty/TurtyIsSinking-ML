package dev.turtywurty.turtyissinking.sound;

import net.minecraft.core.BlockPos;

import java.util.HashSet;
import java.util.Set;

public final class ReversedJukeboxTracker {
    private static final Set<BlockPos> REVERSED_JUKEBOXES = new HashSet<>();

    private ReversedJukeboxTracker() {
    }

    public static void setReversed(BlockPos pos, boolean reversed) {
        if (reversed) {
            REVERSED_JUKEBOXES.add(pos.immutable());
        } else {
            REVERSED_JUKEBOXES.remove(pos);
        }
    }

    public static boolean consumeReversed(BlockPos pos) {
        return REVERSED_JUKEBOXES.remove(pos);
    }
}
