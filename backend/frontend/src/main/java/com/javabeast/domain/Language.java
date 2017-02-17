package com.javabeast.domain;

public enum Language {
    EN("en"),
    ES("es"),
    DE("de"),
    IT("it"),
    NL("nl"),
    PL("pl"),
    PT("pt"),
    DA("da"),
    UK("uk"),
    RU("ru"),
    NO("no"),
    SV("sv");

    private final String displayName;

    Language(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }


}
