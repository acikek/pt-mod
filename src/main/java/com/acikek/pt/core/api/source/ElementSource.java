package com.acikek.pt.core.api.source;

import com.acikek.pt.core.api.content.ElementContentBase;
import com.acikek.pt.core.api.content.ContentContext;
import com.acikek.pt.core.api.refined.ElementRefinedState;

public interface ElementSource<D> extends ElementContentBase<D, ContentContext.Source> {

    /**
     * @see ContentContext.Source#state()
     */
    default ElementRefinedState<?> state() {
        return context().state();
    }
}
