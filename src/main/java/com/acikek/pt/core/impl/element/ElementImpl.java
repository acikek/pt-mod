package com.acikek.pt.core.impl.element;

import com.acikek.pt.core.element.Element;
import com.acikek.pt.core.lang.ElementNaming;
import com.acikek.pt.core.registry.ElementIds;
import com.acikek.pt.core.refined.ElementRefinedState;
import com.acikek.pt.core.source.ElementSource;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class ElementImpl implements Element {

    private final ElementNaming names;
    private final ElementIds<String> ids;
    private final List<ElementSource> sources;
    private final ElementRefinedState state;

    private boolean registered = false;

    public ElementImpl(ElementNaming names, List<ElementSource> sources, ElementRefinedState state) {
        Stream.of(names, state).forEach(Objects::requireNonNull);
        this.names = names;
        this.ids = ElementIds.create(id());
        this.sources = new ArrayList<>(sources);
        this.state = state;
    }

    public ElementIds<String> elementIds() {
        return ids;
    }

    @Override
    public @NotNull ElementNaming naming() {
        return names;
    }

    @Override
    public List<ElementSource> sources() {
        return sources.stream().toList();
    }

    @Override
    public void afterRegister() {
        registered = true;
    }

    @Override
    public void addSource(ElementSource source) {
        if (registered) {
            throw new IllegalStateException("element already registered");
        }
        sources.add(source);
    }

    @Override
    public @NotNull ElementRefinedState state() {
        return state;
    }
}
