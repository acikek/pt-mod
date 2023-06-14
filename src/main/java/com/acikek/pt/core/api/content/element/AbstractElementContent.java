package com.acikek.pt.core.api.content.element;

import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A base implementation of {@link ElementContentBase} that future implementors may inherit from.<br>
 *
 */
public abstract class AbstractElementContent<D, C extends ContentContext> implements ElementContentBase<D, C> {

    private final Identifier id;

    private C context;
    private List<ElementContentBase<?, C>> extensions;

    protected AbstractElementContent(Identifier id) {
        Objects.requireNonNull(id);
        this.id = id;
    }

    @Override
    public @NotNull Identifier id() {
        return id;
    }

    @Override
    public C context() {
        return context;
    }

    @Override
    public void setContext(C context) {
        this.context = context;
    }

    @Override
    public ElementContentBase<D, C> extend(ElementContentBase<?, C> child) {
        if (extensions == null) {
            extensions = new ArrayList<>();
        }
        extensions.add(child);
        return this;
    }

    @Override
    public List<ElementContentBase<?, C>> extensions() {
        return extensions;
    }
}
