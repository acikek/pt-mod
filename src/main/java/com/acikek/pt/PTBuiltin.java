package com.acikek.pt;

import com.acikek.pt.api.plugin.PTPlugin;
import com.acikek.pt.core.api.AbstractPeriodicTable;
import com.acikek.pt.core.api.PeriodicTable;

import java.util.Collection;
import java.util.List;

public class PTBuiltin implements PTPlugin {

    @Override
    public Collection<AbstractPeriodicTable> createTables() {
        return List.of(PeriodicTable.INSTANCE);
    }
}
