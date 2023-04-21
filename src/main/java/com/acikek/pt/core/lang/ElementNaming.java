package com.acikek.pt.core.lang;

import java.util.Objects;
import java.util.stream.Stream;

public record ElementNaming(String id, String englishName, String symbol) implements CompoundNaming {

    public static ElementNaming full(String id, String name, String symbol) {
        Stream.of(id, name, symbol).forEach(Objects::requireNonNull);
        return new ElementNaming(id, name, symbol);
    }
    public static ElementNaming byName(String name, String symbol) {
        return full(CompoundNames.getIdFromName(name), name, symbol);
    }

    public static ElementNaming byName(String name) {
        return byName(name, CompoundNames.getSymbolFromName(name));
    }

    public static ElementNaming byId(String id, String symbol) {
        return full(id, CompoundNames.getNameFromId(id), symbol);
    }

    public static ElementNaming byId(String id) {
        String name = CompoundNames.getNameFromId(id);
        return full(id, name, CompoundNames.getSymbolFromName(name));
    }

    public static ElementNaming forObject(Object obj) {
        if (obj instanceof String id) {
            return byId(id);
        }
        if (obj instanceof ElementNaming naming) {
            return naming;
        }
        throw new IllegalStateException("object must be either a String id or an ElementNaming instance");
    }
}
