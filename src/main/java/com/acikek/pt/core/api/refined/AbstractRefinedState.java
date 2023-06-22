package com.acikek.pt.core.api.refined;

import com.acikek.pt.core.api.content.element.AbstractElementContent;
import com.acikek.pt.core.api.content.element.ContentContext;
import com.acikek.pt.core.api.content.element.ElementContentBase;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * A base implementation of {@link ElementRefinedState} that future implementors may inherit from.
 * @see AbstractElementContent
 */
public abstract class AbstractRefinedState extends AbstractElementContent<ContentContext.State> implements ElementRefinedState {

    private boolean primary = false;

    private List<ElementRefinedState> extensions;
    private ElementRefinedState root = null;

    protected AbstractRefinedState(Identifier id) {
        super(id);
    }

    @Override
    public boolean isPrimary() {
        return primary;
    }

    @Override
    public ElementRefinedState primary() {
        primary = true;
        return this;
    }

    @Override
    public ElementRefinedState extend(ElementContentBase<ContentContext.State> extension) {
        super.extend(extension);
        if (extensions == null) {
            extensions = new ArrayList<>();
        }
        extensions.add((ElementRefinedState) extension);
        return this;
    }

    @Override
    public @Nullable ElementRefinedState root() {
        return root;
    }

    @Override
    public void setRoot(ElementContentBase<ContentContext.State> root) {
        this.root = (ElementRefinedState) root;
    }

    @Override
    public List<ElementRefinedState> extensions() {
        return extensions;
    }
}
