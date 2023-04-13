package com.acikek.pt.core.source;

public interface SourceHolder {

    ElementSource source();

    default boolean hasSource() {
        return source() != null;
    }
}
