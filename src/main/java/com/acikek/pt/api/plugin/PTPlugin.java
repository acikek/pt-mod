package com.acikek.pt.api.plugin;

import com.acikek.pt.core.api.AbstractPeriodicTable;

import java.util.Collection;
import java.util.Collections;

public interface PTPlugin {

    default Collection<AbstractPeriodicTable> createTables() {
        return Collections.emptyList();
    }

    default void beforeRegister() {
        // Empty
    }
}
