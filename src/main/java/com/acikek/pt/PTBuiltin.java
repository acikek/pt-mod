package com.acikek.pt;

import com.acikek.pt.api.plugin.PTPlugin;
import com.acikek.pt.api.request.event.RequestEvent;
import com.acikek.pt.api.request.RequestTypes;
import com.acikek.pt.core.api.AbstractPeriodicTable;
import com.acikek.pt.core.api.PeriodicTable;
import com.acikek.pt.core.api.refined.RefinedStates;
import net.minecraft.util.Identifier;

import java.util.Collection;
import java.util.List;

public class PTBuiltin implements PTPlugin {

    public static final Identifier ID = PT.id("builtin");

    @Override
    public Identifier id() {
        return ID;
    }

    @Override
    public Collection<AbstractPeriodicTable> createTables() {
        return List.of(PeriodicTable.INSTANCE);
    }

    @Override
    public void request(RequestEvent event) {
        event.all();
        /*event.minerals().submit(PeriodicTable.STIBNITE, RequestTypes.CONTENT);
        event.states().submit(PeriodicTable.ANTIMONY, RefinedStates.BASE, RequestTypes.CONTENT);*/
    }

    @Override
    public void addContentExtensions() {
        for (var state : PeriodicTable.ANTIMONY.getRefinedStatesByType(RefinedStates.BASE)) {
            //state.extend(RefinedStates.fluid(new Identifier("joe")));
        }
    }
}
