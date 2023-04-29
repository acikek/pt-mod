package com.acikek.pt.core.impl.refined;

import com.acikek.pt.core.api.element.ElementalObjects;
import com.acikek.pt.core.api.refined.ElementRefinedState;
import com.acikek.pt.core.api.refined.RefinedStateTypes;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public class RefinedStateBuilder {

    private Identifier id;
    private Supplier<Item> item;
    private Supplier<Item> miniItem;
    private Supplier<Block> block;
    private Supplier<Fluid> fluid;
    private RefinedStateTypes type;

    public RefinedStateBuilder id(Identifier id) {
        this.id = id;
        return this;
    }

    public RefinedStateBuilder item(Supplier<Item> item) {
        this.item = item;
        return this;
    }

    public RefinedStateBuilder miniItem(Supplier<Item> miniItem) {
        this.miniItem = miniItem;
        return this;
    }

    public RefinedStateBuilder type(RefinedStateTypes type) {
        this.type = type;
        return this;
    }

    public RefinedStateBuilder block(Supplier<Block> block) {
        this.block = block;
        return this;
    }

    public RefinedStateBuilder block(RefinedStateTypes type, Float strength) {
        this.type(type);
        return block(() -> type.createBlock(strength));
    }

    public RefinedStateBuilder block(RefinedStateTypes type) {
        return block(type, null);
    }

    public RefinedStateBuilder addFluid(Supplier<Fluid> fluid) {
        this.fluid = fluid;
        return this;
    }

    public ElementRefinedState build() {
        Supplier<Item> item = this.item != null ? this.item : ElementalObjects::createItem;
        Supplier<Item> miniItem = this.miniItem != null ? this.miniItem : ElementalObjects::createItem;
        return fluid != null
                ? new FluidRefinedState(id, item, miniItem, block, fluid)
                : new BaseRefinedState(id, item, miniItem, block, type);
    }
}
