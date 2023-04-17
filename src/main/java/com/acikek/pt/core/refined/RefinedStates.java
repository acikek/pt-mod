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

    public static ElementRefinedState chamber() {
        return fromType(RefinedStateTypes.CHAMBER);
    }

    public static ElementRefinedState basin() {
        return fromType(RefinedStateTypes.BASIN);
    }

    public static ElementRefinedState sack() {
        return fromType(RefinedStateTypes.SACK);
    }

    public static ElementRefinedState bloom() {
        return fromType(RefinedStateTypes.BLOOM);
    }

    public static ElementRefinedState trace() {
        return fromType(RefinedStateTypes.TRACE);
    }
}
