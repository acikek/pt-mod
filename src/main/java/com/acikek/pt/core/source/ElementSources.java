package com.acikek.pt.core.source;

import com.acikek.pt.core.impl.source.MineralSourceBuilder;
import com.acikek.pt.core.impl.source.OreSourceBuilder;

public class ElementSources {

    public static OreSourceBuilder oreBuilder() {
        return new OreSourceBuilder();
    }

    public static ElementSource ore() {
        return oreBuilder().build();
    }

    public static MineralSourceBuilder mineralBuilder() {
        return new MineralSourceBuilder();
    }

    public static ElementSource mineral() {
        return mineralBuilder().build();
    }
}
