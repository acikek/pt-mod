package com.acikek.pt.core.impl.element;

import com.acikek.pt.core.api.content.ContentContext;
import com.acikek.pt.core.api.display.ElementDisplay;
import com.acikek.pt.core.api.element.Element;
import com.acikek.pt.core.api.refined.ElementRefinedState;
import com.acikek.pt.core.api.registry.ElementIds;
import com.acikek.pt.core.api.source.ElementSource;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class ElementImpl implements Element {

    private final ElementDisplay names;
    private final ElementIds<String> ids;
    private final Map<ElementRefinedState, List<ElementSource>> sourceStateMap;

    private boolean registered = false;

    public ElementImpl(ElementDisplay names, Map<ElementRefinedState, List<ElementSource>> sourceStateMap) {
        Objects.requireNonNull(names);
        Map<ElementRefinedState, List<ElementSource>> mutableMap = sourceStateMap.entrySet().stream()
                        .collect(Collectors.toMap(Map.Entry::getKey, entry -> new ArrayList<>(entry.getValue())));
        this.sourceStateMap = new HashMap<>(mutableMap);
        getAllContent().forEach(Objects::requireNonNull);
        this.names = names;
        this.ids = ElementIds.create(id());
    }

    public ElementIds<String> elementIds() {
        return ids;
    }

    @Override
    public @NotNull ElementDisplay display() {
        return names;
    }

    @Override
    public void afterRegister() {
        registered = true;
        forEachContent(content -> content.injectSignature(this));
    }

    @Override
    public @NotNull Map<ElementRefinedState, List<ElementSource>> sourceStateMap() {
        return sourceStateMap;
    }

    @Override
    public void addSource(ElementSource source, ElementRefinedState toState) {
        if (registered) {
            throw new IllegalStateException("element already registered");
        }
        var list = sourceStateMap.computeIfAbsent(toState, k -> new ArrayList<>());
        source.onAdd(new ContentContext.Source(this, toState));
        list.add(source);
    }

    @Override
    public String toString() {
        return id();
    }
}
