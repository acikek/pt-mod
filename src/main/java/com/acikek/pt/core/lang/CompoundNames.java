package com.acikek.pt.core.lang;

import org.apache.commons.lang3.StringUtils;

public class CompoundNames {

    public static String getNameFromId(String id) {
        return StringUtils.capitalize(id.replace("_", " "));
    }

    public static String getIdFromName(String name) {
        return name.toLowerCase().replace(" ", "_");
    }

    public static String getSymbolFromName(String name) {
        return StringUtils.substring(name, 0, 2);
    }
}
