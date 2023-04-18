package com.acikek.pt.core.refined;

import com.acikek.pt.core.impl.refined.RefinedStateBuilder;

public class RefinedStates {

    public static RefinedStateBuilder builder() {
        return new RefinedStateBuilder();
    }

    public static ElementRefinedState fromType(RefinedStateType type, Float strength) {
        return builder().block(type, strength).build();
    }

    public static ElementRefinedState fromType(RefinedStateType type) {
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
