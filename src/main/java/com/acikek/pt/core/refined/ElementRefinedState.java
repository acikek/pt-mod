package com.acikek.pt.core.refined;

import com.acikek.pt.PT;
import com.acikek.pt.core.id.ElementIds;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public interface ElementRefinedState {

    @NotNull Item refinedItem();

    @NotNull Item miniRefinedItem();

    @NotNull Block refinedBlock();

    Fluid refinedFluid();

    default boolean hasRefinedFluid() {
        return refinedFluid() != null;
    }

    default void register(ElementIds<String> ids) {
        PT.registerItem(ids.getItemId(), refinedItem());
        PT.registerItem(ids.getMiniItemId(), miniRefinedItem());
        PT.registerBlock(ids.getBlockId(), refinedBlock());
        if (hasRefinedFluid()) {
            PT.registerFluid(ids.getFluidId(), refinedFluid());
        }
    }

    default List<Block> getBlocks() {
        return Collections.singletonList(refinedBlock());
    }

    default List<Item> getItems() {
        return List.of(refinedItem(), miniRefinedItem());
    }
}
