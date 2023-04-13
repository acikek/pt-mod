package com.acikek.pt.core.impl.refined;

import com.acikek.pt.core.element.ElementalObjects;
import com.acikek.pt.core.refined.ElementRefinedState;
import com.acikek.pt.core.refined.RefinedBlockType;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;

public class RefinedStateBuilder {

    private Item item;
    private Item miniItem;
    private Block block;
    private Fluid fluid;

    public RefinedStateBuilder item(Item item) {
        this.item = item;
        return this;
    }

    public RefinedStateBuilder miniItem(Item miniItem) {
        this.miniItem = miniItem;
        return this;
    }

    public RefinedStateBuilder block(Block block) {
        this.block = block;
        return this;
    }

    public RefinedStateBuilder block(RefinedBlockType type, float strength) {
        return block(new Block(type.getBlockSettings(strength)));
    }

    public RefinedStateBuilder block(RefinedBlockType type) {
        return block(type, 0.0f);
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
                : new SolidRefinedState(item, miniItem, block);
    }
}
