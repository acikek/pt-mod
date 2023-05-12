package com.acikek.pt.api.request.event;

import com.acikek.pt.core.api.AbstractPeriodicTable;
import com.acikek.pt.core.api.PeriodicTable;

public interface RequestEvent {

    MineralRequestEvent minerals();

    ContentRequestEvent states();

    ContentRequestEvent sources();

    default void all(AbstractPeriodicTable table) {
        minerals().all(table);
        states().all(table);
        sources().all(table);
    }

    default void all() {
        all(PeriodicTable.INSTANCE);
    }
}
