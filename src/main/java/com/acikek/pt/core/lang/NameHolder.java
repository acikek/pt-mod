package com.acikek.pt.core.lang;

import org.jetbrains.annotations.NotNull;

public interface NameHolder<T extends CompoundNaming> {

    @NotNull T naming();

    default String id() {
        return naming().id();
    }
}
