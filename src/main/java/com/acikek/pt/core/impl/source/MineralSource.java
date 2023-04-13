package com.acikek.pt.core.impl.source;

import com.acikek.pt.core.source.ElementSource;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class MineralSource implements ElementSource {

    private final Block mineral;
    private final Block cluster;
    private final Item rawMineral;

    public MineralSource(Block mineral, Block cluster, Item rawMineral) {
        Objects.requireNonNull(mineral);
        this.mineral = mineral;
        this.cluster = cluster;
        this.rawMineral = rawMineral;
    }

    @Override
    public @NotNull Block sourceBlock() {
        return mineral;
    }

    @Override
    public Block deepslateSourceBlock() {
        return null;
    }

    @Override
    public Block clusterSourceBlock() {
        return cluster;
    }

    @Override
    public Item rawSourceItem() {
        return rawMineral;
    }

    @Override
    public Block rawSourceBlock() {
        return null;
    }
}
