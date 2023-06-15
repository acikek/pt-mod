package com.acikek.pt.core.api.element;

import com.acikek.pt.core.api.display.ElementDisplay;
import com.acikek.pt.core.api.refined.ElementRefinedState;
import com.acikek.pt.core.api.refined.RefinedStates;
import com.acikek.pt.core.api.source.ElementSource;
import com.acikek.pt.core.impl.element.ElementImpl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Elements {

    public static Element create(Object naming, Map<ElementRefinedState, List<ElementSource>> states) {
        return new ElementImpl(ElementDisplay.forObject(naming), states);
    }

    public static Element singleState(Object naming, ElementRefinedState state, List<ElementSource> sources) {
        return create(naming, Map.of(state, sources));
    }

    public static Element singleState(Object naming, ElementRefinedState state, ElementSource... sources) {
        return singleState(naming, state, Arrays.stream(sources).toList());
    }

    public static Element basic(Object naming, ElementRefinedState state, ElementSource source) {
        return singleState(naming, state, Collections.singletonList(source));
    }

    public static Element noSources(Object naming, ElementRefinedState state) {
        return singleState(naming, state, Collections.emptyList());
    }

    public static Element noSources(Object naming, List<ElementRefinedState> states) {
        return create(naming,
                states.stream().collect(
                        Collectors.toMap(state -> state, state -> Collections.emptyList())
                )
        );
    }

    public static Element noSources(Object naming, ElementRefinedState... states) {
        return noSources(naming, Arrays.stream(states).toList());
    }

    public static Element gas(Object naming) {
        return noSources(naming, RefinedStates.gas());
    }
}
