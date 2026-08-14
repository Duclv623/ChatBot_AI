package com.jurisai.util;

public class ConversationConstant {
    public static enum ConversationStatus {
        OPEN(0),
        IN_PROCESS(1),
        READ(2),
        UNREAD(3);

        private final int value;

        ConversationStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public static enum ParrentConvStatus {
        NEW_CONV(0);

        private final long value;

        ParrentConvStatus(long value) {
            this.value = value;
        }

        public long getValue() {
            return value;
        }
    }
}
