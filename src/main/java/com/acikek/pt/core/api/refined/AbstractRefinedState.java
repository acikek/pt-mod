package com.acikek.pt.core.api.refined;

import com.acikek.pt.core.api.content.element.AbstractElementContent;
import com.acikek.pt.core.api.content.element.ContentContext;
import net.minecraft.util.Identifier;

/**
 * A base implementation of {@link ElementRefinedState} that future implementors may inherit from.
 * @see AbstractElementContent
 */
public abstract class AbstractRefinedState<D> extends AbstractElementContent<D, ContentContext.State> implements ElementRefinedState<D> {

    private boolean primary = false;

    protected AbstractRefinedState(Identifier id) {
        super(id);
    }

    @Override
    public boolean isPrimary() {
        return primary;
    }

    @Override
    public ElementRefinedState<D> primary() {
        primary = true;
        return this;
    }
}
