package com.acikek.pt.core.impl.source;

import com.acikek.pt.core.api.content.phase.PhasedContent;
import com.acikek.pt.core.api.element.ElementalObjects;
import com.acikek.pt.core.api.source.ElementSource;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class OreSourceBuilder {

    private Identifier id;
    private PhasedContent<Block> ore = PhasedContent.none();
    private PhasedContent<Block> deepslateOre = PhasedContent.none();
    private boolean hasOre;
    private PhasedContent<Item> rawItem = PhasedContent.none();
    private PhasedContent<Block> rawBlock = PhasedContent.none();
    private boolean hasRaw;
    private int miningLevel = -1;

    public OreSourceBuilder id(Identifier id) {
        this.id = id;
        return this;
    }

    public OreSourceBuilder ore(Object ore) {
        this.ore = PhasedContent.from(ore, Block.class);
        return this;
    }

    public OreSourceBuilder deepslateOre(Object deepslateOre) {
        this.deepslateOre = PhasedContent.from(deepslateOre, Block.class);
        return this;
    }

    public OreSourceBuilder addOres() {
        hasOre = true;
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

    public OreSourceBuilder addRawForms() {
        this.hasRaw = true;
        return this;
    }

    public OreSourceBuilder miningLevel(int miningLevel) {
        this.miningLevel = miningLevel;
        return this;
    }

    public ElementSource<?> build() {
        return new OreSource(
                id,
                hasOre && !ore.canExist() ? PhasedContent.of(ElementalObjects::createOreBlock) : ore,
                hasOre && !deepslateOre.canExist() ? PhasedContent.of(ElementalObjects::createDeepslateOreBlock) : deepslateOre,
                hasRaw && !rawItem.canExist() ? PhasedContent.of(ElementalObjects::createItem) : rawItem,
                hasRaw && !rawBlock.canExist() ? PhasedContent.of(ElementalObjects::createRawSourceBlock) : rawBlock,
                miningLevel != -1 ? miningLevel : 1
        );
    }
}
