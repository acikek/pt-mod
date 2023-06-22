package com.acikek.pt.core.impl.element;

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
        this.names = names;
        this.ids = ElementIds.create(id());
        /*forEachRefinedState(state -> state.onAdd(getStateContext(state)));
        forEachSource((source, state) -> source.onAdd(getSourceContext(source, state)));*/
    }

    public ElementIds<String> elementIds() {
        return ids;
    }

    @Override
    public @NotNull ElementDisplay display() {
        return names;
    }

    @Override
    public void onRegister() {
        Element.super.onRegister();
        getAllContent().forEach(Objects::requireNonNull);
        registered = true;
    }

    @Override
    public @NotNull Map<ElementRefinedState, List<ElementSource>> sourceStateMap() {
        return sourceStateMap;
    }

    private void checkRegistered() {
        if (registered) {
            throw new IllegalStateException("element already registered");
        }
    }

    @Override
    public void addRefinedState(ElementRefinedState state) {
        checkRegistered();
        sourceStateMap.put(state, new ArrayList<>());
        state.onAdd(getStateContext(state));
    }

    @Override
    public void addSource(ElementSource source, ElementRefinedState toState) {
        checkRegistered();
        var list = sourceStateMap.computeIfAbsent(toState, k -> new ArrayList<>());
        source.onAdd(getSourceContext(source, toState));
        list.add(source);
    }

    @Override
    public String toString() {
        return id();
    }
}
