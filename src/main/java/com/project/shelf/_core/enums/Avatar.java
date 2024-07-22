package com.project.shelf._core.enums;

public enum Avatar {

    AVATAR01("AVATAR01"),
    AVATAR02("AVATAR02"),
    AVATAR03("AVATAR03"),
    AVATAR04("AVATAR04"),
    AVATAR05("AVATAR05"),
    AVATAR06("AVATAR06"),
    AVATAR07("AVATAR07"),
    AVATAR08("AVATAR08");

    private final String value;

    Avatar(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
