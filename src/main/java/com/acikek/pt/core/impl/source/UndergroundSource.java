package com.acikek.pt.core.impl.source;

import com.acikek.pt.api.request.FeatureRequests;
import com.acikek.pt.core.api.registry.ElementIds;
import com.acikek.pt.core.api.registry.PTRegistry;
import com.acikek.pt.core.api.source.ElementSource;

public abstract class UndergroundSource implements ElementSource {

    @Override
    public void register(PTRegistry registry, ElementIds<String> ids, FeatureRequests.Content features) {

    }
}
