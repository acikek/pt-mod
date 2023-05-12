package com.acikek.pt.api.impl.request;

import com.acikek.pt.api.request.FeatureRequests;
import com.acikek.pt.api.request.MineralRequestEvent;
import com.acikek.pt.core.api.AbstractPeriodicTable;
import com.acikek.pt.core.api.mineral.Mineral;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MineralRequestImpl implements MineralRequestEvent {

    private final Map<Mineral, FeatureRequests.Single> requests = new HashMap<>();

    @Override
    public void submit(Mineral mineral, List<Identifier> requests) {
        var features = this.requests.computeIfAbsent(mineral, k -> FeatureRequests.Single.empty());
        features.requests().addAll(requests);
    }

    @Override
    public void all(Mineral mineral) {
        requests.put(mineral, FeatureRequests.Single.useAll());
    }

    @Override
    public Map<Mineral, FeatureRequests.Single> requests() {
        return requests;
    }

    @Override
    public String toString() {
        return requests.toString();
    }
}
