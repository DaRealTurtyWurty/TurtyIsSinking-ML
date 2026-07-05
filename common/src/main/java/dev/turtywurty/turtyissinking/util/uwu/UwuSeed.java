package dev.turtywurty.turtyissinking.util.uwu;

import java.util.Objects;
import java.util.function.IntSupplier;

public final class UwuSeed {
    private String seed;
    private IntSupplier seeder;

    public UwuSeed() {
        this("");
    }

    public UwuSeed(String seed) {
        this.seed = seed == null ? "" : seed;
        this.seeder = generateSeeder(this.seed);
    }

    public String getSeed() {
        return seed;
    }

    public void setSeed(String seed) {
        String nextSeed = seed == null ? "" : seed;
        if (Objects.equals(this.seed, nextSeed))
            return;

        this.seed = nextSeed;
        this.seeder = generateSeeder(nextSeed);
    }

    public double random() {
        return random(0, 1);
    }

    public double random(double min, double max) {
        return generateRange(sfc32(), min, max);
    }

    public int randomInt(int min, int max) {
        return (int) Math.round(random(min, max));
    }

    public double sfc32() {
        return sfc32(0, 1);
    }

    public double sfc32(double min, double max) {
        int a = seeder.getAsInt();
        int b = seeder.getAsInt();
        int c = seeder.getAsInt();
        int d = seeder.getAsInt();

        int t = a + b;
        a = b ^ (b >>> 9);
        b = c + (c << 3);
        c = (c << 21) | (c >>> 11);
        d = d + 1;
        t = t + d;
        c = c + t;

        return generateRange(unsignedRatio(t), min, max);
    }

    public double mulberry32() {
        return mulberry32(0, 1);
    }

    public double mulberry32(double min, double max) {
        int a = seeder.getAsInt();

        int t = a + 0x6d2b79f5;
        t = (t ^ (t >>> 15)) * (t | 1);
        t ^= t + (t ^ (t >>> 7)) * (t | 61);

        return generateRange(unsignedRatio(t ^ (t >>> 14)), min, max);
    }

    public double jsf32() {
        return jsf32(0, 1);
    }

    public double jsf32(double min, double max) {
        int a = seeder.getAsInt();
        int b = seeder.getAsInt();
        int c = seeder.getAsInt();
        int d = seeder.getAsInt();

        int t = a - ((b << 27) | (b >>> 5));
        a = b ^ ((c << 17) | (c >>> 15));
        b = c + d;
        c = d + t;
        d = a + t;

        return generateRange(unsignedRatio(d), min, max);
    }

    public double xoshiro128() {
        return xoshiro128(0, 1);
    }

    public double xoshiro128(double min, double max) {
        int a = seeder.getAsInt();
        int b = seeder.getAsInt();
        int c = seeder.getAsInt();
        int d = seeder.getAsInt();

        int t = b << 9;
        int r = a * 5;
        r = ((r << 7) | (r >>> 25)) * 9;
        c ^= a;
        d ^= b;
        b ^= c;
        a ^= d;
        c ^= t;
        d = (d << 11) | (d >>> 21);

        return generateRange(unsignedRatio(r), min, max);
    }

    private static double generateRange(double value, double min, double max) {
        if (min > max)
            throw new IllegalArgumentException("The minimum value must be below the maximum value");

        if (min == max)
            throw new IllegalArgumentException("The minimum value cannot equal the maximum value");

        if (min == 0 && max == 1)
            return value;

        return value * (max - min) + min;
    }

    private static IntSupplier generateSeeder(String seed) {
        int h = 1779033703 ^ seed.length();
        for (int i = 0; i < seed.length(); i++) {
            h = (h ^ seed.charAt(i)) * (int) 3432918353L;
            h = (h << 13) | (h >>> 19);
        }

        final int[] state = {h};
        return () -> {
            int next = state[0];
            next = (next ^ (next >>> 16)) * (int) 2246822507L;
            next = (next ^ (next >>> 13)) * (int) 3266489909L;
            next ^= next >>> 16;
            state[0] = next;
            return next;
        };
    }

    private static double unsignedRatio(int value) {
        return Integer.toUnsignedLong(value) / 4294967296.0;
    }
}