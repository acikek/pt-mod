package com.acikek.pt.core.impl.refined;

import com.acikek.pt.core.api.element.ElementalObjects;
import com.acikek.pt.core.api.refined.ElementRefinedState;
import com.acikek.pt.core.api.refined.RefinedStateType;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;

public class RefinedStateBuilder {

    private Item item;
    private Item miniItem;
    private Block block;
    private Fluid fluid;
    private RefinedStateType type;

    public RefinedStateBuilder item(Item item) {
        this.item = item;
        return this;
    }

    public RefinedStateBuilder miniItem(Item miniItem) {
        this.miniItem = miniItem;
        return this;
    }

    public RefinedStateBuilder type(RefinedStateType type) {
        this.type = type;
        return this;
    }

    public RefinedStateBuilder block(Block block) {
        this.block = block;
        return this;
    }

    public RefinedStateBuilder block(RefinedStateType type, Float strength) {
        this.type(type);
        return block(type.createBlock(strength));
    }

    public RefinedStateBuilder block(RefinedStateType type) {
        return block(type, null);
    }

    public RefinedStateBuilder addFluid(Fluid fluid) {
        this.fluid = fluid;
        return this;
    }

    public ElementRefinedState build() {
        Item item = this.item != null ? this.item : ElementalObjects.createItem();
        Item miniItem = this.miniItem != null ? this.miniItem : ElementalObjects.createItem();
        return fluid != null
                ? new FluidRefinedState(item, miniItem, block, fluid)
                : new BaseRefinedState(item, miniItem, block, type);
    }
}
