package com.jurisai.util;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AccountConstant {
    public enum Role {
        ADMIN(0),
        USER(1);

        private final int value;

        Role(int value){
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum Gender {
        MALE(0),
        FEMALE(1),
        OTHER(2);

        private final int value;

        Gender(int value){
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum AuthProvier {
        LOCAL(1),
        GOOGLE(2);

        private final int value;

        AuthProvier(int value){
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum IsVerified {
        VERIFIED(0),
        UNVERIFIED(1);

        private final int value;

        IsVerified(int value){
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum TokenSubject {
        USER_ACCESS("User Access"),
        ADMIN_ACCESS("Admin Access"),
        USER_LOGOUT("User Logout"),
        OTP_RESET_PASSWORD("OTP Change Password"),
        RESET_PASSWORD("Reset Password"),
        CHANGE_PASSWORD("Change Password");

        private final String subject;

        TokenSubject(String subject) {
            this.subject = subject;
        }

        public String getSubject() {
            return subject;
        }

        public Duration getDuration() {
            switch (this) {
                case USER_ACCESS:
                    return Duration.ofHours(24);
                case ADMIN_ACCESS:
                    return Duration.ofHours(1);
                case OTP_RESET_PASSWORD:
                case CHANGE_PASSWORD:
                case RESET_PASSWORD:
                    return Duration.ofMinutes(5);
                case USER_LOGOUT:
                    return Duration.ofMinutes(1);
                default:
                    return Duration.ofMinutes(1);
            }
        }

        public List<String> getScopes() {
            switch (this) {
                case USER_ACCESS:
                case CHANGE_PASSWORD:
                case RESET_PASSWORD:
                    return Collections.unmodifiableList(Arrays.asList("read", "write"));
                case ADMIN_ACCESS:
                    return Collections.unmodifiableList(Arrays.asList("read", "write", "delete"));
                default:
                    return Collections.emptyList();
            }
        }
    }
}
