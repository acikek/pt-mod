package com.acikek.pt.core.element;

import com.acikek.pt.core.impl.element.ElementImpl;
import com.acikek.pt.core.refined.ElementRefinedState;
import com.acikek.pt.core.refined.RefinedStates;
import com.acikek.pt.core.source.ElementSource;
import com.acikek.pt.core.source.ElementSources;

public class Elements {

    public static Element full(String id, ElementSource source, ElementRefinedState state) {
        return new ElementImpl(id, source, state);
    }

    public static Element noSource(String id, ElementRefinedState state) {
        return full(id, null, state);
    }

    public static Element gas(String id, int atmosphericMin, int atmosphericMax) {
        return full(id, ElementSources.atmospheric(atmosphericMin, atmosphericMax), RefinedStates.chamber());
    }
}
