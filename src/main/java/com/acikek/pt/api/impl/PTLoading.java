package com.acikek.pt.api.impl;

import com.acikek.pt.api.impl.request.event.RequestEventImpl;
import com.acikek.pt.api.plugin.PTPlugin;
import com.acikek.pt.api.request.event.RequestEvent;
import com.acikek.pt.core.api.AbstractPeriodicTable;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class PTLoading {

    private static final String ENTRYPOINT = "pt";

    public static List<AbstractPeriodicTable> tables = new ArrayList<>();
    private static List<PTPlugin> plugins;

    private static final Supplier<ImmutableList<PTPlugin>> PLUGINS = Suppliers.memoize(
            () -> ImmutableList.copyOf(
                    FabricLoader.getInstance().getEntrypoints(ENTRYPOINT, PTPlugin.class)
            )
    );

    public static void load() {
        plugins = PLUGINS.get();
        for (PTPlugin loader : plugins) {
            tables.addAll(loader.createTables());
        }
        for (PTPlugin loader : plugins) {
            loader.onRegister();
        }
        for (PTPlugin loader : plugins) {
            loader.addContentExtensions();
        }
        RequestEvent event = new RequestEventImpl();
        for (PTPlugin loader : plugins) {
            loader.request(event);
        }
        System.out.println(event);
        for (AbstractPeriodicTable table : tables) {
            table.register(event);
        }
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            for (AbstractPeriodicTable table : tables) {
                table.initClient();
            }
        }
    }
}
