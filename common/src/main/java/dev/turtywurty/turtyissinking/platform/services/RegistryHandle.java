package dev.turtywurty.turtyissinking.platform.services;

import net.minecraft.resources.Identifier;

import java.util.function.Supplier;

public interface RegistryHandle<T> extends Supplier<T> {
    Identifier id();
}
