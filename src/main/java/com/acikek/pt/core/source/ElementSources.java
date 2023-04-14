package com.acikek.pt.core.source;

import com.acikek.pt.core.impl.source.AtmosphericSource;
import com.acikek.pt.core.impl.source.MineralSourceBuilder;
import com.acikek.pt.core.impl.source.OreSourceBuilder;
import org.apache.commons.lang3.Range;

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

    public static ElementSource fullMineral() {
        return mineralBuilder().addCluster().addRawMineral().build();
    }

    public static ElementSource mineralWithRawForm() {
        return mineralBuilder().addRawMineral().build();
    }

    public static ElementSource atmospheric(Range<Integer> range) {
        return new AtmosphericSource(range);
    }

    public static ElementSource atmospheric(int min, int max) {
        return atmospheric(Range.between(min, max));
    }
}
