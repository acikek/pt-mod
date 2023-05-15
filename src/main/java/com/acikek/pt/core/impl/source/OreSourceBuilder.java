package com.acikek.pt.core.impl.source;

import com.acikek.pt.core.api.content.PhasedContent;
import com.acikek.pt.core.api.element.ElementalObjects;
import com.acikek.pt.core.api.source.ElementSource;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class OreSourceBuilder {

    private PhasedContent<Block> ore;
    private PhasedContent<Block> deepslateOre;
    private PhasedContent<Item> rawItem;
    private PhasedContent<Block> rawBlock;
    private int miningLevel = -1;

    public OreSourceBuilder ore(Object ore) {
        this.ore = PhasedContent.from(ore, Block.class);
        return this;
    }

    public OreSourceBuilder deepslateOre(Object deepslateOre) {
        this.deepslateOre = PhasedContent.from(deepslateOre, Block.class);
        return this;
    }

    public OreSourceBuilder rawItem(Object rawItem) {
        this.rawItem = PhasedContent.from(rawItem, Item.class);
        return this;
    }

    public OreSourceBuilder rawBlock(Object rawBlock) {
        this.rawBlock = PhasedContent.from(rawBlock, Block.class);
        return this;
    }

    public OreSourceBuilder miningLevel(int miningLevel) {
        this.miningLevel = miningLevel;
        return this;
    }

    public ElementSource build() {
        return new OreSource(
                ore != null ? ore : PhasedContent.of(ElementalObjects::createOreBlock),
                deepslateOre != null ? deepslateOre : PhasedContent.of(ElementalObjects::createDeepslateOreBlock),
                rawItem != null ? rawItem : PhasedContent.of(ElementalObjects::createItem),
                rawBlock != null ? rawBlock : PhasedContent.of(ElementalObjects::createRawSourceBlock),
                miningLevel != -1 ? miningLevel : 1
        );
    }
}
