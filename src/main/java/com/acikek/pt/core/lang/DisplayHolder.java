package com.acikek.pt.core.lang;

import org.jetbrains.annotations.NotNull;

public interface DisplayHolder<T extends CompoundDisplay> {

    @NotNull T display();

    default String id() {
        return display().id();
    }
}
