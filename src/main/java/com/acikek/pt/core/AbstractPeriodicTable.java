package com.acikek.pt.core;

import com.acikek.pt.PT;
import com.acikek.pt.core.element.Element;
import com.acikek.pt.core.element.ElementHolder;
import com.acikek.pt.core.mineral.Mineral;
import com.acikek.pt.core.registry.PTRegistry;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public abstract class AbstractPeriodicTable implements ElementHolder {

    private final Map<String, Element> elements;
    private final PTRegistry registry;

    protected AbstractPeriodicTable(PTRegistry registry) {
        ImmutableMap.Builder<String, Element> builder = ImmutableMap.builder();
        for (Element element : createElements()) {
            builder.put(element.id(), element);
        }
        elements = builder.build();
        for (Mineral mineral : createMinerals()) {
            mineral.init();
        }
        this.registry = registry;
    }

    protected AbstractPeriodicTable() {
        this(PT.REGISTRY);
    }

    protected List<Mineral> createMinerals() {
        return Collections.emptyList();
    }

    protected abstract List<Element> createElements();

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

    public void register() {
        forEachElement(element -> element.register(registry));
    }
}
