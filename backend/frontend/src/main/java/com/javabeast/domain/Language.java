package com.javabeast.domain;

public enum Language {
    EN("en"),
    ES("es"),
    DE("de");

    private final String displayName;

    Language(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }


}
