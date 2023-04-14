package com.acikek.pt.core.impl.source;

import com.acikek.pt.core.source.ElementSource;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import org.apache.commons.lang3.Range;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.stream.Stream;

public class OreSource implements ElementSource {

    public Block ore;
    public Block deepslateOre;
    public Item rawItem;
    public Block rawBlock;

    public OreSource(Block ore, Block deepslateOre, Item rawItem, Block rawBlock) {
        Stream.of(ore, deepslateOre, rawItem, rawBlock).forEach(Objects::requireNonNull);
        this.ore = ore;
        this.deepslateOre = deepslateOre;
        this.rawItem = rawItem;
        this.rawBlock = rawBlock;
    }

    @Override
    public @NotNull Type getType() {
        return Type.ORE;
    }

    @Override
    public Block sourceBlock() {
        return ore;
    }

    @Override
    public Block deepslateSourceBlock() {
        return deepslateOre;
    }

    @Override
    public Block clusterSourceBlock() {
        return null;
    }

    @Override
    public Item rawSourceItem() {
        return rawItem;
    }

    @Override
    public Block rawSourceBlock() {
        return rawBlock;
    }

    @Override
    public Range<Integer> atmosphericRange() {
        return null;
    }
}
