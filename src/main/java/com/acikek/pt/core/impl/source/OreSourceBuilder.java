package com.acikek.pt.core.impl.source;

import com.acikek.pt.core.api.element.ElementalObjects;
import com.acikek.pt.core.api.source.ElementSource;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.function.Supplier;

public class OreSourceBuilder {

    private Supplier<Block> ore;
    private Supplier<Block> deepslateOre;
    private Supplier<Item> rawItem;
    private Supplier<Block> rawBlock;
    private int miningLevel = -1;

    public OreSourceBuilder ore(Supplier<Block> ore) {
        this.ore = ore;
        return this;
    }

    public OreSourceBuilder deepslateOre(Supplier<Block> deepslateOre) {
        this.deepslateOre = deepslateOre;
        return this;
    }

    public OreSourceBuilder rawItem(Supplier<Item> rawItem) {
        this.rawItem = rawItem;
        return this;
    }

    public OreSourceBuilder rawBlock(Supplier<Block> rawBlock) {
        this.rawBlock = rawBlock;
        return this;
    }

    public OreSourceBuilder miningLevel(int miningLevel) {
        this.miningLevel = miningLevel;
        return this;
    }

    public ElementSource build() {
        return new OreSource(
                ore != null ? ore : ElementalObjects::createOreBlock,
                deepslateOre != null ? deepslateOre : ElementalObjects::createDeepslateOreBlock,
                rawItem != null ? rawItem : ElementalObjects::createItem,
                rawBlock != null ? rawBlock : ElementalObjects::createRawSourceBlock,
                miningLevel != -1 ? miningLevel : 1
        );
    }
}
