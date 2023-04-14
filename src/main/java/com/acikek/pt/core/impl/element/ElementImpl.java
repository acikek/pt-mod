package com.acikek.pt.core.impl.element;

import com.acikek.pt.core.element.Element;
import com.acikek.pt.core.lang.ElementNaming;
import com.acikek.pt.core.registry.ElementIds;
import com.acikek.pt.core.refined.ElementRefinedState;
import com.acikek.pt.core.source.ElementSource;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.stream.Stream;

public class ElementImpl implements Element {

    private final ElementNaming names;
    private final ElementIds<String> ids;
    private final ElementSource source;
    private final ElementRefinedState state;

    public ElementImpl(ElementNaming names, ElementSource source, ElementRefinedState state) {
        Stream.of(names, state).forEach(Objects::requireNonNull);
        this.names = names;
        this.ids = ElementIds.get(id());
        this.source = source;
        this.state = state;
    }

    public ElementIds<String> elementIds() {
        return ids;
    }

    @Override
    public @NotNull ElementNaming names() {
        return names;
    }

    @Override
    public ElementSource source() {
        return source;
    }

    @Override
    public @NotNull ElementRefinedState state() {
        return state;
    }
}
