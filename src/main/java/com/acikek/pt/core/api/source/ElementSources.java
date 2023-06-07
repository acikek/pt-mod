package com.acikek.pt.core.api.source;

import com.acikek.pt.core.api.content.element.ContentIdentifier;
import com.acikek.pt.core.api.mineral.Mineral;
import com.acikek.pt.core.impl.source.AtmosphericSource;
import com.acikek.pt.core.impl.source.MineralSource;
import com.acikek.pt.core.impl.source.OreSourceBuilder;
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

    public static ElementSource<?> ore(Identifier id, int miningLevel) {
        return oreBuilder().id(id).miningLevel(miningLevel).addOres().addRawForms().build();
    }

    public static ElementSource<?> ore(int miningLevel) {
        return ore(ElementSource.MAIN, miningLevel);
    }

    public static ElementSource<?> wrapOre(Identifier id, Block ore, Block deepslateOre, Item rawItem, Block rawBlock) {
        return oreBuilder().id(id).ore(ore).deepslateOre(deepslateOre).rawItem(rawItem).rawBlock(rawBlock).build();
    }

    public static ElementSource<?> wrapOre(Block ore, Block deepslateOre, Item rawItem, Block rawBlock) {
        return wrapOre(ElementSource.MAIN, ore, deepslateOre, rawItem, rawBlock);
    }

    public static ElementSource<?> mineral(Identifier id, Mineral<?> mineral) {
        return new MineralSource<>(id, mineral);
    }

    public static ElementSource<?> mineral(Mineral<?> mineral) {
        return new MineralSource<>(ElementSource.MAIN, mineral);
    }

    public static ElementSource<?> atmospheric(Identifier id, Range<Integer> range) {
        return new AtmosphericSource(id, range);
    }

    public static ElementSource<?> atmospheric(Range<Integer> range) {
        return new AtmosphericSource(ElementSource.MAIN, range);
    }

    public static ElementSource<?> atmospheric(Identifier id, int min, int max) {
        return atmospheric(id, Range.between(min, max));
    }

    public static ElementSource<?> atmospheric(int min, int max) {
        return atmospheric(ElementSource.MAIN, min, max);
    }
}
