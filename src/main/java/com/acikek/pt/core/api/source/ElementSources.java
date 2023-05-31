package com.acikek.pt.core.api.source;

import com.acikek.pt.PT;
import com.acikek.pt.core.api.content.ContentIdentifier;
import com.acikek.pt.core.impl.source.AtmosphericSource;
import com.acikek.pt.core.impl.source.MineralSource;
import com.acikek.pt.core.impl.source.OreSourceBuilder;
import com.acikek.pt.core.api.mineral.Mineral;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.Range;

public class ElementSources {

    public static final ContentIdentifier ORE = ElementSource.id("ore");
    public static final ContentIdentifier MINERAL = ElementSource.id("mineral");
    public static final ContentIdentifier ATMOSPHERIC = ElementSource.id("atmospheric");

    public static OreSourceBuilder oreBuilder() {
        return new OreSourceBuilder();
    }

    public static ElementSource<?> ore(int miningLevel) {
        return oreBuilder().miningLevel(miningLevel).addOres().addRawForms().build();
    }

    public static ElementSource<?> wrapOre(Block ore, Block deepslateOre, Item rawItem, Block rawBlock) {
        return oreBuilder().ore(ore).deepslateOre(deepslateOre).rawItem(rawItem).rawBlock(rawBlock).build();
    }

    public static ElementSource<?> mineral(Mineral<?> mineral) {
        return new MineralSource<>(mineral);
    }

    public static ElementSource<?> atmospheric(Range<Integer> range) {
        return new AtmosphericSource(range);
    }

    public static ElementSource<?> atmospheric(int min, int max) {
        return atmospheric(Range.between(min, max));
    }
}
