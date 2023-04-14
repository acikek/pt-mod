package com.acikek.pt.core;

import com.acikek.pt.core.element.Element;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public abstract class AbstractPeriodicTable {

    private final Map<String, Element> elements;

    protected AbstractPeriodicTable() {
        ImmutableMap.Builder<String, Element> builder = ImmutableMap.builder();
        for (Element element : createElements()) {
            builder.put(element.id(), element);
        }
        elements = builder.build();
    }

    protected abstract List<Element> createElements();

    public Map<String, Element> elementMap() {
        return elements;
    }

    public List<Element> getElements() {
        return elementMap().values().stream().toList();
    }

    private <T> List<T> getValues(Function<Element, List<T>> list) {
        return getElements().stream()
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
        for (Element element : getElements()) {
            element.register();
        }
    }
}
