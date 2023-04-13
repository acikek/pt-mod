package com.acikek.pt.core.impl.refined;

import com.acikek.pt.core.refined.ElementRefinedState;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.stream.Stream;

public class SolidRefinedState implements ElementRefinedState {

    private final Item item;
    private final Item miniItem;
    private final Block block;

    public SolidRefinedState(Item item, Item miniItem, Block block) {
        Stream.of(item, miniItem, block).forEach(Objects::requireNonNull);
        this.item = item;
        this.miniItem = miniItem;
        this.block = block;
    }

    @Override
    public @NotNull Item refinedItem() {
        return item;
    }

    @Override
    public @NotNull Item miniRefinedItem() {
        return miniItem;
    }

    @Override
    public @NotNull Block refinedBlock() {
        return block;
    }

    @Override
    public Fluid refinedFluid() {
        return null;
    }
}
