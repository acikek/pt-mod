package com.acikek.pt.core.api;

import com.acikek.pt.PT;
import com.acikek.pt.api.request.event.RequestEvent;
import com.acikek.pt.core.api.element.Element;
import com.acikek.pt.core.api.mineral.Mineral;
import com.acikek.pt.core.api.registry.PTRegistry;
import com.google.common.collect.ImmutableMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public abstract class AbstractPeriodicTable implements CompoundHolder {

    private final Map<String, Mineral<?>> minerals;
    private final Map<String, Element> elements;
    private final PTRegistry registry;

    protected AbstractPeriodicTable(PTRegistry registry) {
        ImmutableMap.Builder<String, Element> elementBuilder = ImmutableMap.builder();
        for (Element element : createElements()) {
            elementBuilder.put(element.id(), element);
        }
        elements = elementBuilder.build();
        ImmutableMap.Builder<String, Mineral<?>> mineralBuilder = ImmutableMap.builder();
        for (Mineral<?> mineral : createMinerals()) {
            mineral.init();
            mineralBuilder.put(mineral.id(), mineral);
        }
        minerals = mineralBuilder.build();
        for (Element element : elements()) {
            element.forEachRefinedState(state -> state.onAdd(element.getStateContext(state)));
            element.forEachSource((source, state) -> source.onAdd(element.getSourceContext(state)));
        }
        this.registry = registry;
    }

    protected AbstractPeriodicTable() {
        this(PT.REGISTRY);
    }

    protected List<Mineral<?>> createMinerals() {
        return Collections.emptyList();
    }

    protected abstract List<Element> createElements();

    public Map<String, Mineral<?>> mineralMap() {
        return minerals;
    }

    @Override
    public List<Mineral<?>> minerals() {
        return mineralMap().values().stream().toList();
    }

    public Map<String, Element> elementMap() {
        return elements;
    }

    @Override
    public List<Element> elements() {
        return elementMap().values().stream().toList();
    }

    private <T> List<T> getValues(Function<Element, List<T>> list) {
        return elements().stream()
                .flatMap(element -> list.apply(element).stream())
                .toList();
    }

    public List<Block> getBlocks() {
        return getValues(Element::getBlocks);
    }

    public List<Item> getItems() {
        return getValues(Element::getItems);
    }

    public void register(RequestEvent event) {
        forEachMineral(mineral -> mineral.register(registry, event.minerals().getRequests(mineral)));
        forEachElement(element -> element.register(registry, event.states().getRequests(element), event.sources().getRequests(element)));
    }

    @Environment(EnvType.CLIENT)
    public void initClient() {
        forEachMineral(Mineral::initClient);
        forEachElement(Element::initClient);
    }
}
