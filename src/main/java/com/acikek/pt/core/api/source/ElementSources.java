package com.acikek.pt.core.api.source;

import com.acikek.pt.PT;
import com.acikek.pt.core.impl.source.AtmosphericSource;
import com.acikek.pt.core.impl.source.MineralSource;
import com.acikek.pt.core.impl.source.OreSourceBuilder;
import com.acikek.pt.core.api.mineral.Mineral;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.Range;

public class ElementSources {

    public static final Identifier ORE = PT.id("ore");
    public static final Identifier MINERAL = PT.id("mineral");
    public static final Identifier ATMOSPHERIC = PT.id("atmospheric");

    public static OreSourceBuilder oreBuilder() {
        return new OreSourceBuilder();
    }

    public static ElementSource ore(int miningLevel) {
        return oreBuilder().miningLevel(miningLevel).build();
    }

    public static ElementSource mineral(Mineral mineral) {
        return new MineralSource(mineral);
    }

    public static ElementSource atmospheric(Range<Integer> range) {
        return new AtmosphericSource(range);
    }

    public static ElementSource atmospheric(int min, int max) {
        return atmospheric(Range.between(min, max));
    }
}