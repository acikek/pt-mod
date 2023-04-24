package com.acikek.pt.core.api.source;

import java.util.List;

public interface SourceHolder {

    List<ElementSource> sources();

    void addSource(ElementSource source);

    default boolean hasSources() {
        return sources() != null;
    }
}
