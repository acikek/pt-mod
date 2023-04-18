package com.acikek.pt.core.impl.refined;

import com.acikek.pt.core.refined.RefinedStateTypes;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;

import java.util.Objects;

public class FluidRefinedState extends BaseRefinedState {

    private final Fluid fluid;

    public FluidRefinedState(Item item, Item miniItem, Block block, Fluid fluid) {
        super(item, miniItem, block, RefinedStateTypes.FLUID);
        Objects.requireNonNull(fluid);
        this.fluid = fluid;
    }

    @Override
    public Fluid refinedFluid() {
        return fluid;
    }
}
