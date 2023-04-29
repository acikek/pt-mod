package com.acikek.pt.core.api.refined;

import com.acikek.pt.PT;
import com.acikek.pt.core.impl.refined.RefinedStateBuilder;
import net.minecraft.util.Identifier;

public class RefinedStates {

    public static final Identifier BASE = PT.id("base");
    public static final Identifier FLUID = PT.id("fluid");

    public static RefinedStateBuilder builder() {
        return new RefinedStateBuilder();
    }

    public static ElementRefinedState fromType(RefinedStateTypes type, Float strength) {
        return builder().block(type, strength).build();
    }

    public static ElementRefinedState fromType(RefinedStateTypes type) {
        return fromType(type, null);
    }

    public static ElementRefinedState metal(float strength) {
        return fromType(RefinedStateTypes.METAL, strength);
    }

    public static ElementRefinedState gas() {
        return fromType(RefinedStateTypes.GAS);
    }

    public static ElementRefinedState fluid() {
        return fromType(RefinedStateTypes.FLUID);
    }

    public static ElementRefinedState sack() {
        return fromType(RefinedStateTypes.POWDER);
    }

    public static ElementRefinedState synthesized() {
        return fromType(RefinedStateTypes.SYNTHESIZED);
    }

    public static ElementRefinedState trace() {
        return fromType(RefinedStateTypes.TRACE);
    }
}
