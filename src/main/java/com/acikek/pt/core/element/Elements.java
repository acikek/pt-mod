package com.acikek.pt.core.element;

import com.acikek.pt.core.impl.element.ElementImpl;
import com.acikek.pt.core.lang.ElementNames;
import com.acikek.pt.core.refined.ElementRefinedState;
import com.acikek.pt.core.refined.RefinedStates;
import com.acikek.pt.core.source.ElementSource;
import com.acikek.pt.core.source.ElementSources;

public class Elements {

    public static Element full(Object naming, ElementSource source, ElementRefinedState state) {
        return new ElementImpl(ElementNames.forObject(naming), source, state);
    }

    public static Element noSource(Object naming, ElementRefinedState state) {
        return full(naming, null, state);
    }

    public static Element gas(Object naming, int atmosphericMin, int atmosphericMax) {
        return full(naming, ElementSources.atmospheric(atmosphericMin, atmosphericMax), RefinedStates.chamber());
    }
}
