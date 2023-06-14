package com.acikek.pt.api.plugin;

import com.acikek.pt.api.request.event.RequestEvent;
import com.acikek.pt.core.api.AbstractPeriodicTable;
import net.minecraft.util.Identifier;

import java.util.Collection;
import java.util.Collections;

public interface PTPlugin {

    Identifier id();

    default Collection<AbstractPeriodicTable> createTables() {
        return Collections.emptyList();
    }

    default void request(RequestEvent event) {
        // Empty
    }

    default void addContent() {
        // Empty
    }

    default void extendContent() {
        // Empty
    }

    default void onRegister() {
        // Empty
    }
}
