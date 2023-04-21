package com.acikek.pt.core.source;

import com.acikek.pt.PT;
import com.acikek.pt.core.impl.source.AtmosphericSource;
import com.acikek.pt.core.impl.source.MineralSourceBuilder;
import com.acikek.pt.core.impl.source.OreSourceBuilder;
import com.acikek.pt.core.impl.mineral.MineralBlock;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.Range;

public class ElementSources {

    public static final Identifier ORE = PT.id("ore");
    public static final Identifier MINERAL = PT.id("mineral");
    public static final Identifier ATMOSPHERIC = PT.id("atmospheric");

    public static OreSourceBuilder oreBuilder() {
        return new OreSourceBuilder();
    }

    public static ElementSource ore() {
        return oreBuilder().build();
    }

    public static MineralSourceBuilder mineralBuilder() {
        return new MineralSourceBuilder();
    }

    public static ElementSource mineral(MineralBlock mineralBlock) {
        return mineralBuilder().mineral(mineralBlock).build();
    }

    public static ElementSource fullMineral(MineralBlock mineralBlock) {
        return mineralBuilder().mineral(mineralBlock).addCluster().addRawMineral().build();
    }

    public static ElementSource mineralWithRawForm(MineralBlock mineralBlock) {
        return mineralBuilder().mineral(mineralBlock).addRawMineral().build();
    }

    public static ElementSource atmospheric(Range<Integer> range) {
        return new AtmosphericSource(range);
    }

    public static ElementSource atmospheric(int min, int max) {
        return atmospheric(Range.between(min, max));
    }
}
