package com.acikek.pt.core.display;

import java.util.Objects;
import java.util.stream.Stream;

public record ElementDisplay(String id, String englishName, String symbol, int signatureAmount) implements CompoundDisplay {

    public static ElementDisplay full(String id, String name, String symbol, int signatureAmount) {
        Stream.of(id, name, symbol).forEach(Objects::requireNonNull);
        return new ElementDisplay(id, name, symbol, signatureAmount);
    }

    public static ElementDisplay full(String id, String name, String symbol) {
        return full(id, name, symbol, 1);
    }

    public static ElementDisplay byName(String name, String symbol, int signatureAmount) {
        return full(CompoundNames.getIdFromName(name), name, symbol, signatureAmount);
    }

    public static ElementDisplay byName(String name, String symbol) {
        return byName(name, symbol, 1);
    }

    public static ElementDisplay byName(String name, int signatureAmount) {
        return byName(name, CompoundNames.getSymbolFromName(name), signatureAmount);
    }

    public static ElementDisplay byName(String name) {
        return byName(name, 1);
    }

    public static ElementDisplay byId(String id, String symbol, int signatureAmount) {
        return full(id, CompoundNames.getNameFromId(id), symbol, signatureAmount);
    }

    public static ElementDisplay byId(String id, String symbol) {
        return byId(id, symbol, 1);
    }

    public static ElementDisplay byId(String id, int signatureAmount) {
        String name = CompoundNames.getNameFromId(id);
        return full(id, name, CompoundNames.getSymbolFromName(name), signatureAmount);
    }

    public static ElementDisplay byId(String id) {
        return byId(id, 1);
    }

    public static ElementDisplay forObject(Object obj) {
        if (obj instanceof String id) {
            return byId(id);
        }
        if (obj instanceof ElementDisplay naming) {
            return naming;
        }
        throw new IllegalStateException("object must be either a String id or an ElementNaming instance");
    }
}
