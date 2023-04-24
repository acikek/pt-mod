package com.acikek.pt.core.impl.refined;

import com.acikek.pt.core.api.refined.ElementRefinedState;
import com.acikek.pt.core.api.refined.RefinedStateType;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.stream.Stream;

public class BaseRefinedState implements ElementRefinedState {

    private final Item item;
    private final Item miniItem;
    private final Block block;
    private final RefinedStateType type;

    public BaseRefinedState(Item item, Item miniItem, Block block, RefinedStateType type) {
        Stream.of(item, miniItem, block, type).forEach(Objects::requireNonNull);
        this.item = item;
        this.miniItem = miniItem;
        this.block = block;
        this.type = type;
    }

    @Override
    public @NotNull RefinedStateType getType() {
        return type;
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
