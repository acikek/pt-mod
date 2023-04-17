package com.acikek.pt.core.source;

import java.util.List;

public interface SourceHolder {

    List<ElementSource> sources();

    default boolean hasSources() {
        return sources() != null;
    }
}
