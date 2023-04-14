package com.acikek.pt.core.lang;

import org.jetbrains.annotations.NotNull;

public interface NameHolder {

    @NotNull ElementNaming names();

    default String id() {
        return names().id();
    }
}
