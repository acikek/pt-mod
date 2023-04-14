package com.acikek.pt.core.impl.source;

import com.acikek.pt.core.source.ElementSource;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import org.apache.commons.lang3.Range;
import org.jetbrains.annotations.NotNull;

public class AtmosphericSource implements ElementSource {

    private final Range<Integer> range;

    public AtmosphericSource(Range<Integer> range) {
        this.range = range;
    }

    @Override
    public @NotNull Type getType() {
        return Type.ATMOSPHERE;
    }

    @Override
    public Block sourceBlock() {
        return null;
    }

    @Override
    public Block deepslateSourceBlock() {
        return null;
    }

    @Override
    public Block clusterSourceBlock() {
        return null;
    }

    @Override
    public Item rawSourceItem() {
        return null;
    }

    @Override
    public Block rawSourceBlock() {
        return null;
    }

    @Override
    public Range<Integer> atmosphericRange() {
        return range;
    }
}
