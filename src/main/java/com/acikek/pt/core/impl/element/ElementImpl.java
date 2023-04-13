package com.acikek.pt.core.impl.element;

import com.acikek.pt.core.element.Element;
import com.acikek.pt.core.id.ElementIds;
import com.acikek.pt.core.refined.ElementRefinedState;
import com.acikek.pt.core.source.ElementSource;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ElementImpl implements Element {

    private final String id;
    private final ElementIds<String> ids;
    private final ElementSource source;
    private final ElementRefinedState state;

    public ElementImpl(String id, ElementSource source, ElementRefinedState state) {
        Objects.requireNonNull(id);
        this.id = id;
        this.ids = ElementIds.get(id);
        this.source = source;
        this.state = state;
    }

    public String id() {
        return id;
    }

    public ElementIds<String> elementIds() {
        return ids;
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
