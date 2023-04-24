package com.acikek.pt.core.api.display;

import java.util.Objects;
import java.util.stream.Stream;

public record MineralDisplay(String id, String englishName, String rawFormName) implements CompoundDisplay {

    public static MineralDisplay full(String id, String englishName, String rawFormName) {
        Stream.of(id, englishName).forEach(Objects::requireNonNull);
        return new MineralDisplay(id, englishName, rawFormName);
    }

    public static MineralDisplay byName(String englishName, String rawFormName) {
        return full(CompoundNames.getIdFromName(englishName), englishName, rawFormName);
    }

    public static MineralDisplay byName(String englishName) {
        return byName(englishName, null);
    }

    public static MineralDisplay byId(String id, String rawFormName) {
        return full(id, CompoundNames.getNameFromId(id), rawFormName);
    }

    public static MineralDisplay byId(String id) {
        return byId(id, null);
    }

    public static MineralDisplay forObject(Object object) {
        if (object instanceof String name) {
            return byName(name);
        }
        if (object instanceof MineralDisplay naming) {
            return naming;
        }
        throw new IllegalStateException("object must be either a String name or a MineralNaming instance");
    }

    public String getRawFormId() {
        return id + "_" + CompoundNames.getIdFromName(rawFormName);
    }
}
