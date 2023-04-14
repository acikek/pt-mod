package com.acikek.pt.core.lang;

public record MineralNaming(String englishName, String rawFormName) {

    public static MineralNaming full(String englishName, String rawFormName) {
        return new MineralNaming(englishName, rawFormName);
    }

    public static MineralNaming byName(String englishName) {
        return full(englishName, null);
    }

    public static MineralNaming forObject(Object object) {
        if (object instanceof String name) {
            return byName(name);
        }
        if (object instanceof MineralNaming naming) {
            return naming;
        }
        throw new IllegalStateException("object must be either a String name or a MineralNaming instance");
    }
}
