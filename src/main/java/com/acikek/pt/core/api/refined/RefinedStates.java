package com.acikek.pt.core.api.refined;

import com.acikek.pt.core.api.content.ContentIdentifier;
import com.acikek.pt.core.impl.refined.RefinedStateBuilder;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class RefinedStates {

    public static final ContentIdentifier BASE = ElementRefinedState.id("base");
    public static final ContentIdentifier FLUID = ElementRefinedState.id("fluid");

    public static RefinedStateBuilder builder() {
        return new RefinedStateBuilder();
    }

    public static ElementRefinedState<?> wrap(Identifier id, Item item, Item miniItem, Block block) {
        return builder().id(id).item(item).miniItem(miniItem).block(block).build();
    }

    public static ElementRefinedState<?> wrap(Item item, Item miniItem, Block block) {
        return wrap(ElementRefinedState.MAIN, item, miniItem, block);
    }

    public static ElementRefinedState<?> fromType(Identifier id, RefinedStateType type, Float strength) {
        return builder().id(id).block(type, strength).addItem().addMiniItem().build();
    }

    public static ElementRefinedState<?> fromType(RefinedStateType type, Float strength) {
        return fromType(ElementRefinedState.MAIN, type, strength);
    }

    public static ElementRefinedState<?> fromType(Identifier id, RefinedStateType type) {
        return fromType(id, type, null);
    }

    public static ElementRefinedState<?> fromType(RefinedStateType type) {
        return fromType(type, null);
    }

    public static ElementRefinedState<?> metal(Identifier id, float strength) {
        return fromType(id, RefinedStateType.METAL, strength);
    }

    public static ElementRefinedState<?> metal(float strength) {
        return metal(ElementRefinedState.MAIN, strength);
    }

    public static ElementRefinedState<?> gas(Identifier id) {
        return fromType(id, RefinedStateType.GAS);
    }

    public static ElementRefinedState<?> gas() {
        return gas(ElementRefinedState.MAIN);
    }

    public static ElementRefinedState<?> fluid(Identifier id) {
        return fromType(id, RefinedStateType.FLUID);
    }

    public static ElementRefinedState<?> fluid() {
        return fluid(ElementRefinedState.MAIN);
    }

    public static ElementRefinedState<?> sack(Identifier id) {
        return fromType(id, RefinedStateType.POWDER);
    }

    public static ElementRefinedState<?> sack() {
        return sack(ElementRefinedState.MAIN);
    }

    public static ElementRefinedState<?> synthesized(Identifier id) {
        return fromType(id, RefinedStateType.SYNTHESIZED);
    }

    public static ElementRefinedState<?> synthesized() {
        return synthesized(ElementRefinedState.MAIN);
    }

    public static ElementRefinedState<?> trace(Identifier id) {
        return fromType(id, RefinedStateType.TRACE);
    }

    public static ElementRefinedState<?> trace() {
        return trace(ElementRefinedState.MAIN);
    }
}
