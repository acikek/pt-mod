package com.acikek.pt.core.lang;

import org.jetbrains.annotations.NotNull;

public interface NameHolder {

    @NotNull ElementNaming naming();

    default String id() {
        return naming().id();
    }
}
