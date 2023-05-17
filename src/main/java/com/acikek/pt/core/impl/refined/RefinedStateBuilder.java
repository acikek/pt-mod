package com.acikek.pt.core.impl.refined;

import com.acikek.pt.core.api.content.PhasedContent;
import com.acikek.pt.core.api.element.ElementalObjects;
import com.acikek.pt.core.api.refined.ElementRefinedState;
import com.acikek.pt.core.api.refined.RefinedStateType;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public class RefinedStateBuilder {

    private Identifier id;
    private PhasedContent<Item> item = PhasedContent.none();
    private boolean hasItem;
    private PhasedContent<Item> miniItem = PhasedContent.none();
    private boolean hasMiniItem;
    private PhasedContent<Block> block;
    private PhasedContent<Fluid> fluid;
    private RefinedStateType type;

    public RefinedStateBuilder id(Identifier id) {
        this.id = id;
        return this;
    }

    public RefinedStateBuilder item(Object item) {
        this.item = PhasedContent.from(item, Item.class);
        return this;
    }

    public RefinedStateBuilder addItem() {
        hasItem = true;
        return this;
    }

    public RefinedStateBuilder miniItem(Object miniItem) {
        this.miniItem = PhasedContent.from(miniItem, Item.class);
        return this;
    }

    public RefinedStateBuilder addMiniItem() {
        hasMiniItem = true;
        return this;
    }

    public RefinedStateBuilder type(RefinedStateType type) {
        this.type = type;
        return this;
    }

    public RefinedStateBuilder block(Object block) {
        this.block = PhasedContent.from(block, Block.class);
        return this;
    }

    public RefinedStateBuilder block(RefinedStateType type, Float strength) {
        this.type(type);
        return block((Supplier<Block>) () -> type.createBlock(strength));
    }

    public RefinedStateBuilder block(RefinedStateType type) {
        return block(type, null);
    }

    public RefinedStateBuilder addFluid(Object fluid) {
        this.fluid = PhasedContent.from(fluid, Fluid.class);
        return this;
    }

    public ElementRefinedState<?> build() {
        var item = hasItem && !this.item.canExist() ? PhasedContent.of(ElementalObjects::createItem) : this.item;
        var miniItem = hasMiniItem && !this.miniItem.canExist() ? PhasedContent.of(ElementalObjects::createItem) : this.miniItem;
        return fluid != null
                ? new FluidRefinedState(id, item, miniItem, block, fluid)
                : new BaseRefinedState.Type(id, item, miniItem, block, type);
    }
}
