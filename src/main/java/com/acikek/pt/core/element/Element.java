package com.acikek.pt.core.element;

import com.acikek.pt.core.id.ElementIds;
import com.acikek.pt.core.refined.RefinedStateHolder;
import com.acikek.pt.core.source.SourceHolder;

public interface Element extends SourceHolder, RefinedStateHolder {

    String id();

    ElementIds<String> elementIds();

    default void register() {
        if (hasSource()) {
            source().register(elementIds());
        }
        state().register(elementIds());
    }
}
