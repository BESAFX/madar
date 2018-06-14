package com.besafx.app.entity.enums;

public enum PremiumCalendar {
    H("هجري"),
    G("ميلادي");

    private String name;

    PremiumCalendar(String name) {
        this.name = name;
    }

    public static PremiumCalendar findByName(String name) {
        for (PremiumCalendar v : values()) {
            if (v.getName().equals(name)) {
                return v;
            }
        }
        return null;
    }

    public static PremiumCalendar findByValue(String value) {
        for (PremiumCalendar v : values()) {
            if (v.name().equals(value)) {
                return v;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }
}
