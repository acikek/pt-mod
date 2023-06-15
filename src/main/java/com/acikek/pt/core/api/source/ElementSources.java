package com.acikek.pt.core.api.source;

import com.acikek.pt.core.api.content.element.ContentIdentifier;
import com.acikek.pt.core.api.data.DataKey;
import com.acikek.pt.core.api.mineral.Mineral;
import com.acikek.pt.core.impl.source.MineralSource;
import com.acikek.pt.core.impl.source.OreSourceBuilder;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class ElementSources {

    public static final ContentIdentifier ORE = ElementSource.id("ore");
    public static final ContentIdentifier MINERAL = ElementSource.id("mineral");

    public static final DataKey<Block> ORE_BLOCK = DataKey.of("ore_block", Block.class);
    public static final DataKey<Block> ORE_BLOCK_DEEPSLATE = DataKey.of("ore_block_deepslate", Block.class);
    public static final DataKey<Item> ORE_RAW_ITEM = DataKey.of("ore_raw_item", Item.class);
    public static final DataKey<Block> ORE_RAW_BLOCK = DataKey.of("ore_raw_block", Block.class);

    public static OreSourceBuilder oreBuilder() {
        return new OreSourceBuilder();
    }

    public static ElementSource ore(Identifier id, int miningLevel) {
        return oreBuilder().id(id).miningLevel(miningLevel).addOres().addRawForms().build();
    }

    public static ElementSource ore(int miningLevel) {
        return ore(ElementSource.MAIN, miningLevel);
    }

    public static ElementSource wrapOre(Identifier id, Block ore, Block deepslateOre, Item rawItem, Block rawBlock) {
        return oreBuilder().id(id).ore(ore).deepslateOre(deepslateOre).rawItem(rawItem).rawBlock(rawBlock).build();
    }

    public static ElementSource wrapOre(Block ore, Block deepslateOre, Item rawItem, Block rawBlock) {
        return wrapOre(ElementSource.MAIN, ore, deepslateOre, rawItem, rawBlock);
    }

    public static ElementSource mineral(Identifier id, Mineral mineral) {
        return new MineralSource(id, mineral);
    }

    public static ElementSource mineral(Mineral mineral) {
        return new MineralSource(ElementSource.MAIN, mineral);
    }
}
