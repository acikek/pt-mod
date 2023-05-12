package com.acikek.pt.api.request;

import com.acikek.pt.core.api.AbstractPeriodicTable;
import com.acikek.pt.core.api.PeriodicTable;
import com.acikek.pt.core.api.mineral.Mineral;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public interface MineralRequestEvent {

    void submit(Mineral mineral, List<Identifier> requests);

    default void submit(Mineral mineral, Identifier... requests) {
        submit(mineral, Arrays.stream(requests).toList());
    }

    default void submit(Mineral mineral, Identifier request) {
        submit(mineral, Collections.singletonList(request));
    }

    void all(Mineral mineral);

    default void all(AbstractPeriodicTable table) {
        table.forEachMineral(this::all);
    }

    default void all() {
        all(PeriodicTable.INSTANCE);
    }

    Map<Mineral, FeatureRequests.Single> requests();

    default FeatureRequests.Single getRequests(Mineral mineral) {
        return requests().getOrDefault(mineral, FeatureRequests.Single.empty());
    }
}
