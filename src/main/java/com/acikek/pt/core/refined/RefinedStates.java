package com.acikek.pt.core.refined;

import com.acikek.pt.core.impl.refined.RefinedStateBuilder;

public class RefinedStates {

    public static RefinedStateBuilder builder() {
        return new RefinedStateBuilder();
    }

    public static ElementRefinedState fromType(RefinedStateType type, float strength) {
        return builder().block(type, strength).build();
    }

    public static ElementRefinedState metal(float strength) {
        return fromType(RefinedStateTypes.METAL, strength);
    }

    public static ElementRefinedState chamber() {
        return fromType(RefinedStateTypes.CHAMBER, 0.0f);
    }

    public static ElementRefinedState basin() {
        return fromType(RefinedStateTypes.BASIN, 0.0f);
    }
}
