package com.acikek.pt.core.api.element;

import com.acikek.pt.core.api.refined.ElementRefinedState;
import com.acikek.pt.core.api.source.ElementSource;

import java.util.List;
import java.util.Map;

public interface SourceStateMapper {

    Map<ElementRefinedState, List<ElementSource>> sourceStateMap();

    void addSource(ElementSource source, ElementRefinedState toState);

    void addSource(ElementSource source);
}
