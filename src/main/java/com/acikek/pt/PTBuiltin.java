package com.acikek.pt;

import com.acikek.pt.api.plugin.PTPlugin;
import com.acikek.pt.api.request.RequestEvent;
import com.acikek.pt.api.request.RequestTypes;
import com.acikek.pt.core.api.AbstractPeriodicTable;
import com.acikek.pt.core.api.PeriodicTable;
import com.acikek.pt.core.api.refined.RefinedStates;
import com.acikek.pt.core.api.source.ElementSources;

import java.util.Collection;
import java.util.List;

public class PTBuiltin implements PTPlugin {

    @Override
    public Collection<AbstractPeriodicTable> createTables() {
        return List.of(PeriodicTable.INSTANCE);
    }

    @Override
    public void requestFeatures(RequestEvent event) {
        event.minerals().submit(PeriodicTable.STIBNITE, RequestTypes.CONTENT);
        event.states().submit(PeriodicTable.ANTIMONY, RefinedStates.BASE, RequestTypes.CONTENT);
        event.sources().submit(PeriodicTable.ANTIMONY, ElementSources.MINERAL, RequestTypes.CONTENT);
    }
}
