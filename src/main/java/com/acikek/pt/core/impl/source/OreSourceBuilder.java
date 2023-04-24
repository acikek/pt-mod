package com.acikek.pt.core.impl.source;

import com.acikek.pt.core.api.element.ElementalObjects;
import com.acikek.pt.core.api.source.ElementSource;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class OreSourceBuilder {

    private Block ore;
    private Block deepslateOre;
    private Item rawItem;
    private Block rawBlock;
    private int miningLevel = -1;

    public OreSourceBuilder ore(Block ore) {
        this.ore = ore;
        return this;
    }

    public OreSourceBuilder deepslateOre(Block deepslateOre) {
        this.deepslateOre = deepslateOre;
        return this;
    }

    public OreSourceBuilder rawItem(Item rawItem) {
        this.rawItem = rawItem;
        return this;
    }

    public OreSourceBuilder rawBlock(Block rawBlock) {
        this.rawBlock = rawBlock;
        return this;
    }

    public OreSourceBuilder miningLevel(int miningLevel) {
        this.miningLevel = miningLevel;
        return this;
    }

    public ElementSource build() {
        return new OreSource(
                ore != null ? ore : ElementalObjects.createOreBlock(),
                deepslateOre != null ? deepslateOre : ElementalObjects.createDeepslateOreBlock(),
                rawItem != null ? rawItem : ElementalObjects.createItem(),
                rawBlock != null ? rawBlock : ElementalObjects.createRawSourceBlock(),
                miningLevel != -1 ? miningLevel : 1
        );
    }
}
