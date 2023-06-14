package com.acikek.pt.core.api.source;

import com.acikek.pt.core.api.content.element.AbstractElementContent;
import com.acikek.pt.core.api.content.element.ContentContext;
import net.minecraft.util.Identifier;

/**
 * A base implementation of {@link ElementSource} that future implementors may inherit from.
 * @see AbstractElementContent
 */
public abstract class AbstractElementSource<D> extends AbstractElementContent<D, ContentContext.Source> implements ElementSource<D> {

    protected AbstractElementSource(Identifier id) {
        super(id);
    }
}
