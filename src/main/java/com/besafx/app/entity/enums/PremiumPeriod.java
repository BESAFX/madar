package com.besafx.app.entity.enums;

public enum PremiumPeriod {
    Daily("يومي"),
    Weekly("اسبوعي"),
    Midterm("نصف سنوي"),
    Annual("سنوي");

    private String name;

    PremiumPeriod(String name) {
        this.name = name;
    }

    public static PremiumPeriod findByName(String name) {
        for (PremiumPeriod v : values()) {
            if (v.getName().equals(name)) {
                return v;
            }
        }
        return null;
    }

    public static PremiumPeriod findByValue(String value) {
        for (PremiumPeriod v : values()) {
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
