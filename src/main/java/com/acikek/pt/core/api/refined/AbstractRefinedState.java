package com.acikek.pt.core.api.refined;

import com.acikek.pt.core.api.content.element.AbstractElementContent;
import com.acikek.pt.core.api.content.element.ContentContext;
import net.minecraft.util.Identifier;

/**
 * A base implementation of {@link ElementRefinedState} that future implementors may inherit from.
 * @see AbstractElementContent
 */
public abstract class AbstractRefinedState extends AbstractElementContent<ContentContext.State> implements ElementRefinedState {

    private boolean primary = false;

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
}
