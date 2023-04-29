package com.acikek.pt.core.api.element;

import com.acikek.pt.core.api.refined.ElementRefinedState;
import com.acikek.pt.core.impl.element.ElementImpl;
import com.acikek.pt.core.impl.signature.ElementSignatureImpls;
import com.acikek.pt.core.api.display.ElementDisplay;
import com.acikek.pt.core.api.refined.RefinedStates;
import com.acikek.pt.core.api.signature.ElementSignature;
import com.acikek.pt.core.api.signature.ElementSignatureEntry;
import com.acikek.pt.core.api.source.ElementSource;
import com.acikek.pt.core.api.source.ElementSources;

import java.util.*;

public class Elements {

    public static Map<ElementRefinedState, List<ElementSource>> states()

    public static Map<ElementRefinedState, List<ElementSource>> states(ElementRefinedState state, List<ElementSource> sources) {
        return new HashMap<>()
    }

    public static Element full(Object naming, Map<ElementRefinedState, List<ElementSource>>) {
        return new ElementImpl(ElementDisplay.forObject(naming), ElementSource.forObject(sources), state);
    }

    public static Element noSource(Object naming, ElementRefinedState state) {
        return full(naming, Collections.emptyList(), state);
    }

    public static Element gas(Object naming, int atmosphericMin, int atmosphericMax) {
        return full(naming, ElementSources.atmospheric(atmosphericMin, atmosphericMax), RefinedStates.gas());
    }
}
