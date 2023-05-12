package com.acikek.pt.api.request.event;

import com.acikek.pt.core.api.AbstractPeriodicTable;
import com.acikek.pt.core.api.PeriodicTable;
import com.acikek.pt.core.api.mineral.Mineral;
import com.acikek.pt.core.api.refined.ElementRefinedState;
import com.acikek.pt.core.api.source.ElementSource;

public interface RequestEvent {

    /**
     * @return the request portal for {@link Mineral}s
     */
    MineralRequestEvent minerals();

    /**
     * @return the request portal for {@link ElementRefinedState}s
     */
    ContentRequestEvent states();

    /**
     * @return the request portal for {@link ElementSource}s
     */
    ContentRequestEvent sources();

    /**
     * Submits requests for all features to each request portal with the specified table.
     */
    default void all(AbstractPeriodicTable table) {
        minerals().all(table);
        states().all(table);
        sources().all(table);
    }

    /**
     * Calls {@link RequestEvent#all(AbstractPeriodicTable)} with {@link PeriodicTable#INSTANCE}.
     */
    default void all() {
        all(PeriodicTable.INSTANCE);
    }
}
