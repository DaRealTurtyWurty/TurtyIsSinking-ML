package dev.turtywurty.turtyissinking.screen;

import dev.turtywurty.turtyissinking.network.ServerboundAgeVerificationPacket;
import dev.turtywurty.turtyissinking.platform.Services;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;

public class AgeVerificationScreen extends Screen {
    public static final Component TITLE = Component.translatable("screen.turtyissinking.age_verification.title");
    public static final Component I_AM_OVER_18_BUTTON = Component.translatable("screen.turtyissinking.age_verification.button.over_18");
    public static final Component I_AM_UNDER_18_BUTTON = Component.translatable("screen.turtyissinking.age_verification.button.under_18");

    public AgeVerificationScreen() {
        super(TITLE);
    }

    @Override
    protected void init() {
        super.init();

        addRenderableWidget(Button.builder(I_AM_OVER_18_BUTTON, _ -> {
            this.minecraft.gui.setScreen(null);
            LocalPlayer player = Minecraft.getInstance().player;
            if (player != null) {
                Services.NETWORK.sendToServer(new ServerboundAgeVerificationPacket(true));
            }
        }).bounds(this.width / 2 - 100, this.height / 2 - 20, 200, 20).build());

        addRenderableWidget(Button.builder(I_AM_UNDER_18_BUTTON, _ -> {
            this.minecraft.gui.setScreen(null);
            LocalPlayer player = Minecraft.getInstance().player;
            if (player != null) {
                Services.NETWORK.sendToServer(new ServerboundAgeVerificationPacket(false));
            }
        }).bounds(this.width / 2 - 100, this.height / 2 + 10, 200, 20).build());
    }

    @Override
    public boolean isPauseScreen() {
        return true;
    }
}
