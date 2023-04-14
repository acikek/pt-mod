package com.acikek.pt.core.lang;

import com.acikek.pt.core.impl.lang.ElementNamingImpl;
import org.apache.commons.lang3.StringUtils;

public class ElementNames {

    public static ElementNaming full(String id, String name, String symbol) {
        return new ElementNamingImpl(id, name, symbol);
    }

    private static String getNameFromId(String id) {
        return StringUtils.capitalize(id);
    }

    private static String getIdFromName(String name) {
        return name.toLowerCase();
    }

    private static String getSymbolFromName(String name) {
        return StringUtils.substring(name, 0, 2);
    }

    public static ElementNaming byName(String name, String symbol) {
        return full(getIdFromName(name), name, symbol);
    }

    public static ElementNaming byName(String name) {
        return byName(name, getSymbolFromName(name));
    }

    public static ElementNaming byId(String id, String symbol) {
        return full(id, getNameFromId(id), symbol);
    }

    public static ElementNaming byId(String id) {
        String name = getNameFromId(id);
        return full(id, name, getSymbolFromName(name));
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
