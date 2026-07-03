package dev.turtywurty.turtyissinking.screen;

import dev.turtywurty.turtyissinking.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;
import net.minecraft.util.random.Weighted;
import net.minecraft.util.random.WeightedList;
import org.jspecify.annotations.NonNull;

public class PhoneCallScreen extends Screen {
    public static final Component TITLE = Component.translatable("screen." + Constants.MOD_ID + ".phone_call");
    public static final Component IS_CALLING_TEXT = Component.translatable("screen." + Constants.MOD_ID + ".is_calling");
    private static final Identifier PHONE_TEXTURE = Constants.id("textures/gui/iphone.png");
    private static final Identifier ACCEPT_CALL_BUTTON_TEXTURE = Constants.id("textures/gui/accept_call_button.png");
    private static final int ACCEPT_CALL_BUTTON_TEXTURE_SIZE = 360;
    private static final int ACCEPT_CALL_BUTTON_SIZE = 200;
    private static final float ACCEPT_CALL_BUTTON_BOTTOM_OFFSET = 0.13F;

    private static final WeightedList<PhoneContact> CONTACTS = WeightedList.of(
            new Weighted<>(
                    new PhoneContact("Bob Bacon", Constants.id("textures/gui/calling_characters/bob_bacon.png"), 337, 593),
                    100
            ),
            new Weighted<>(
                    new PhoneContact("Daquavious Pork", Constants.id("textures/gui/calling_characters/daquavious_pork.png"), 272, 480),
                    5
            ),
            new Weighted<>(
                    new PhoneContact("John Pork", Constants.id("textures/gui/calling_characters/john_pork.png"), 387, 516),
                    100
            ),
            new Weighted<>(
                    new PhoneContact("Marvin Beak", Constants.id("textures/gui/calling_characters/marvin_beak.png"), 708, 993),
                    100
            ),
            new Weighted<>(
                    new PhoneContact("Quandale Dingle", Constants.id("textures/gui/calling_characters/quandale_dingle.png"), 300, 300),
                    100
            ),
            new Weighted<>(
                    new PhoneContact("Simon Claw", Constants.id("textures/gui/calling_characters/simon_claw.png"), 1186, 1701),
                    100
            ),
            new Weighted<>(
                    new PhoneContact("Skibidi Toilet", Constants.id("textures/gui/calling_characters/skibidi_toilet.png"), 237, 421),
                    100
            ),
            new Weighted<>(
                    new PhoneContact("Tim Cheese", Constants.id("textures/gui/calling_characters/tim_cheese.png"), 298, 360),
                    100
            )
    );

    private static final int PHONE_WIDTH = 787;
    private static final int PHONE_HEIGHT = 1577;
    private static final int PHONE_LEFT = 53;
    private static final int PHONE_TOP = 46;
    private static final int PHONE_RIGHT = 52;
    private static final int PHONE_BOTTOM = 52;

    private int leftPos;
    private int topPos;
    private int scaledPhoneWidth;
    private int scaledPhoneHeight;
    private int callerLeft;
    private int callerTop;
    private int scaledCallerWidth;
    private int scaledCallerHeight;

    private final PhoneContact currentCaller;

    public PhoneCallScreen() {
        super(TITLE);
        this.currentCaller = CONTACTS.getRandom(Minecraft.getInstance().level.getRandom())
                .orElse(CONTACTS.unwrap().getFirst().value());
    }

    @Override
    protected void init() {
        super.init();

        int guiWidth = Minecraft.getInstance().getWindow().getGuiScaledWidth();
        int guiHeight = Minecraft.getInstance().getWindow().getGuiScaledHeight();
        float scale = Math.min(1.0F, Math.min((float) guiWidth / PHONE_WIDTH, (float) guiHeight / PHONE_HEIGHT));

        this.scaledPhoneWidth = Math.round(PHONE_WIDTH * scale);
        this.scaledPhoneHeight = Math.round(PHONE_HEIGHT * scale);
        this.leftPos = (guiWidth - this.scaledPhoneWidth) / 2;
        this.topPos = (guiHeight - this.scaledPhoneHeight) / 2;

        this.callerLeft = this.leftPos + Math.round(PHONE_LEFT * scale);
        this.callerTop = this.topPos + Math.round(PHONE_TOP * scale);
        this.scaledCallerWidth = this.scaledPhoneWidth - Math.round((PHONE_LEFT + PHONE_RIGHT) * scale);
        this.scaledCallerHeight = this.scaledPhoneHeight - Math.round((PHONE_TOP + PHONE_BOTTOM) * scale);

        int acceptButtonSize = Math.round(ACCEPT_CALL_BUTTON_SIZE * scale);
        addRenderableWidget(new TexturedButton(
                this.leftPos + this.scaledPhoneWidth / 2 - acceptButtonSize / 2,
                this.topPos + this.scaledPhoneHeight - Math.round(ACCEPT_CALL_BUTTON_BOTTOM_OFFSET * this.scaledPhoneHeight) - acceptButtonSize / 2,
                acceptButtonSize,
                acceptButtonSize,
                ACCEPT_CALL_BUTTON_TEXTURE,
                ACCEPT_CALL_BUTTON_TEXTURE_SIZE,
                ACCEPT_CALL_BUTTON_TEXTURE_SIZE,
                _ -> onClose()));
    }

    @Override
    public void extractBackground(@NonNull GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks) {
        super.extractBackground(graphics, mouseX, mouseY, partialTicks);

        graphics.blit(RenderPipelines.GUI_TEXTURED, this.currentCaller.texture(), this.callerLeft, this.callerTop, 0, 0, this.scaledCallerWidth, this.scaledCallerHeight, this.currentCaller.imageWidth(), this.currentCaller.imageHeight(), this.currentCaller.imageWidth(), this.currentCaller.imageHeight());
        graphics.blit(RenderPipelines.GUI_TEXTURED, PHONE_TEXTURE, this.leftPos, this.topPos, 0, 0, this.scaledPhoneWidth, this.scaledPhoneHeight, PHONE_WIDTH, PHONE_HEIGHT, PHONE_WIDTH, PHONE_HEIGHT);
    }

    @Override
    public void extractRenderState(@NonNull GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks) {
        super.extractRenderState(graphics, mouseX, mouseY, partialTicks);

        graphics.pose().pushMatrix();
        graphics.pose().translate(this.leftPos + this.scaledPhoneWidth / 2f - (this.font.width(this.currentCaller.name()) / 2f) * 1.5f, this.topPos + (Math.round(0.1F * this.scaledPhoneHeight) * 1.25f));
        graphics.pose().scale(1.5F, 1.5F);
        graphics.text(this.font, Component.literal(this.currentCaller.name()).withStyle(Style.EMPTY), 0, 0, 0xFFFFFFFF, true);
        graphics.pose().popMatrix();
        graphics.text(this.font, IS_CALLING_TEXT, this.leftPos + this.scaledPhoneWidth / 2 - this.font.width(IS_CALLING_TEXT) / 2, this.topPos + Math.round(0.2F * this.scaledPhoneHeight), 0xFFFFFFFF, true);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    public record PhoneContact(String name, Identifier texture, int imageWidth, int imageHeight) {
        public PhoneContact {
            if (name == null || name.isEmpty())
                throw new IllegalArgumentException("Name cannot be null or empty");

            if (texture == null)
                throw new IllegalArgumentException("Texture cannot be null");

            if (imageWidth <= 0 || imageHeight <= 0)
                throw new IllegalArgumentException("Image width and height must be positive");
        }
    }

    private static class TexturedButton extends Button {
        private final Identifier texture;
        private final int textureWidth;
        private final int textureHeight;

        protected TexturedButton(int x, int y, int width, int height, Identifier texture, int textureWidth, int textureHeight, OnPress onPress) {
            super(x, y, width, height, CommonComponents.EMPTY, onPress, DEFAULT_NARRATION);
            this.texture = texture;
            this.textureWidth = textureWidth;
            this.textureHeight = textureHeight;
        }

        @Override
        protected void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks) {
            graphics.blit(RenderPipelines.GUI_TEXTURED, this.texture, this.getX(), this.getY(), 0, 0, this.width, this.height, this.textureWidth, this.textureHeight, this.textureWidth, this.textureHeight);
        }
    }
}
