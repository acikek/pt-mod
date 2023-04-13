package com.acikek.pt.core.impl.source;

import com.acikek.pt.core.element.ElementalObjects;
import com.acikek.pt.core.source.ElementSource;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class OreSourceBuilder {

    private Block ore;
    private Block deepslateOre;
    private Item rawItem;
    private Block rawBlock;

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

    public ElementSource build() {
        return new OreSource(
                ore != null ? ore : ElementalObjects.createOreBlock(),
                deepslateOre != null ? deepslateOre : ElementalObjects.createDeepslateOreBlock(),
                rawItem != null ? rawItem : ElementalObjects.createItem(),
                rawBlock != null ? rawBlock : ElementalObjects.createRawSourceBlock()
        );
    }
}
