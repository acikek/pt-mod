package com.acikek.pt.core.lang;

import java.util.Objects;
import java.util.stream.Stream;

public record MineralNaming(String id, String englishName, String rawFormName) implements CompoundNaming {

    public static MineralNaming full(String id, String englishName, String rawFormName) {
        Stream.of(id, englishName).forEach(Objects::requireNonNull);
        return new MineralNaming(id, englishName, rawFormName);
    }

    public static MineralNaming byName(String englishName, String rawFormName) {
        return full(CompoundNames.getIdFromName(englishName), englishName, rawFormName);
    }

    public static MineralNaming byName(String englishName) {
        return byName(englishName, null);
    }

    public static MineralNaming byId(String id, String rawFormName) {
        return full(id, CompoundNames.getNameFromId(id), rawFormName);
    }

    public static MineralNaming byId(String id) {
        return byId(id, null);
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
