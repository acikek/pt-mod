package com.acikek.pt.core.impl.source;

import com.acikek.pt.api.request.FeatureRequests;
import com.acikek.pt.core.api.registry.PTRegistry;
import net.minecraft.util.Identifier;

public abstract class UndergroundSource<D> extends BaseSource<D> {

    public UndergroundSource(Identifier id) {
        super(id);
    }

    @Override
    public void register(PTRegistry registry, FeatureRequests.Single features) {

    }
}
