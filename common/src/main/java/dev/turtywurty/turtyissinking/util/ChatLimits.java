package dev.turtywurty.turtyissinking.util;

public final class ChatLimits {
    public static final int MAX_CHAT_MESSAGE_LENGTH = 4096;

    private ChatLimits() {
    }

    public static String truncateChatMessage(String message) {
        return message.length() > MAX_CHAT_MESSAGE_LENGTH ? message.substring(0, MAX_CHAT_MESSAGE_LENGTH) : message;
    }
}
