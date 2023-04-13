package com.acikek.pt.core.impl.source;

import com.acikek.pt.core.element.ElementalObjects;
import com.acikek.pt.core.source.ElementSource;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class MineralSourceBuilder {

    private Block mineral;
    private Block cluster;
    private boolean hasCluster = false;
    private Item rawMineral;
    private boolean hasRawMineral = false;

    public MineralSourceBuilder mineral(Block mineral) {
        this.mineral = mineral;
        return this;
    }

    public MineralSourceBuilder addCluster(Block cluster) {
        this.cluster = cluster;
        return this;
    }

    public MineralSourceBuilder addCluster() {
        hasCluster = true;
        return this;
    }

    public MineralSourceBuilder addRawMineral(Item rawMineral) {
        this.rawMineral = rawMineral;
        return this;
    }

    public MineralSourceBuilder addRawMineral() {
        hasRawMineral = true;
        return this;
    }

    public ElementSource build() {
        return new MineralSource(
                mineral != null ? mineral : ElementalObjects.createMineralBlock(),
                cluster != null ? cluster : hasCluster ? ElementalObjects.createClusterBlock() : null,
                rawMineral != null ? rawMineral : hasRawMineral ? ElementalObjects.createItem() : null
        );
    }
}
