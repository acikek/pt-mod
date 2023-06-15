package com.acikek.pt.api.request.event;

import com.acikek.pt.api.request.FeatureRequests;
import com.acikek.pt.api.request.RequestTypes;
import com.acikek.pt.core.api.AbstractPeriodicTable;
import com.acikek.pt.core.api.PeriodicTable;
import com.acikek.pt.core.api.mineral.Mineral;
import net.minecraft.util.Identifier;

import java.util.*;
import java.util.stream.Collectors;

public interface MineralRequestEvent {

    /**
     * Submits a list of requests to the specified mineral.
     * @see RequestTypes
     */
    void submit(Mineral mineral, Set<Identifier> requests);

    /**
     * @see MineralRequestEvent#submit(Mineral, Set)
     */
    default void submit(Mineral mineral, Identifier... requests) {
        submit(mineral, Arrays.stream(requests).collect(Collectors.toSet()));
    }

    /**
     * @see MineralRequestEvent#submit(Mineral, Set)
     */
    default void submit(Mineral mineral, Identifier request) {
        submit(mineral, Collections.singleton(request));
    }

    /**
     * Submits a request for all features to the specified mineral.
     */
    void all(Mineral mineral);

    /**
     * Submits a request for all features to all minerals created in the specified table.
     */
    default void all(AbstractPeriodicTable table) {
        table.forEachMineral(this::all);
    }

    /**
     * Calls {@link MineralRequestEvent#all(AbstractPeriodicTable)} with {@link PeriodicTable#INSTANCE}.
     */
    default void all() {
        all(PeriodicTable.INSTANCE);
    }

    /**
     * @return a map of minerals to request lists
     */
    Map<Mineral, FeatureRequests.Single> requests();

    /**
     * @return submitted requests for the specified mineral
     */
    default FeatureRequests.Single getRequests(Mineral mineral) {
        return requests().getOrDefault(mineral, FeatureRequests.Single.empty());
    }
}
