package com.acikek.pt.api.impl;

import com.acikek.pt.api.plugin.PTPlugin;
import com.acikek.pt.core.api.AbstractPeriodicTable;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import net.fabricmc.loader.api.FabricLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class PTLoading {

    private static final String ENTRYPOINT = "pt";

    public static List<AbstractPeriodicTable> tables = new ArrayList<>();

    private static final Supplier<ImmutableList<PTPlugin>> PLUGINS = Suppliers.memoize(
            () -> ImmutableList.copyOf(
                    FabricLoader.getInstance().getEntrypoints(ENTRYPOINT, PTPlugin.class)
            )
    );

    public static void load() {
        List<PTPlugin> plugins = PLUGINS.get();
        for (PTPlugin loader : plugins) {
            tables.addAll(loader.createTables());
        }
        for (PTPlugin loader : plugins) {
            loader.beforeRegister();
        }
        for (AbstractPeriodicTable table : tables) {
            table.register();
        }
    }
}
