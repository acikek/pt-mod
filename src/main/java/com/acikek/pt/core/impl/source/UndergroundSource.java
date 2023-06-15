package com.acikek.pt.core.impl.source;

import com.acikek.pt.api.request.FeatureRequests;
import com.acikek.pt.core.api.registry.PTRegistry;
import com.acikek.pt.core.api.source.AbstractElementSource;
import net.minecraft.util.Identifier;

public abstract class UndergroundSource extends AbstractElementSource {

    public UndergroundSource(Identifier id) {
        super(id);
    }

    @Override
    public void register(PTRegistry registry, FeatureRequests.Single features) {

    }
}
