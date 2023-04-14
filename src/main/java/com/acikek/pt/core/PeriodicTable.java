package com.acikek.pt.core;

import com.acikek.pt.core.element.Element;
import com.acikek.pt.core.element.Elements;
import com.acikek.pt.core.refined.RefinedStates;
import com.acikek.pt.core.source.ElementSources;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class PeriodicTable {

    public static final Map<String, Element> ELEMENTS;

    public static final Element HYDROGEN = Elements.gas("hydrogen", 280, 319);
    public static final Element HELIUM = Elements.gas("helium", 255, 300);
    public static final Element LITHIUM = Elements.full("lithium", ElementSources.mineral(), RefinedStates.metal(1.6f));
    public static final Element BERYLLIUM = Elements.full("beryllium", ElementSources.fullMineral(), RefinedStates.metal(6.5f));
    public static final Element OXYGEN = Elements.gas("oxygen", 0, 235);
    public static final Element ANTIMONY = Elements.full("antimony", ElementSources.fullMineral(), RefinedStates.metal(4.0f));

    static {
        ImmutableMap.Builder<String, Element> builder = ImmutableMap.builder();
        List<Element> elements = List.of(HYDROGEN, HELIUM, LITHIUM, BERYLLIUM, OXYGEN, ANTIMONY);
        for (Element element : elements) {
            builder.put(element.id(), element);
        }
        ELEMENTS = builder.build();
    }

    public static List<Element> getElements() {
        return ELEMENTS.values().stream().toList();
    }

    private static <T> List<T> getValues(Function<Element, List<T>> list) {
        return getElements().stream()
                .flatMap(element -> list.apply(element).stream())
                .toList();
    }

    public static List<Block> getBlocks() {
        return getValues(Element::getBlocks);
    }

    public static List<Item> getItems() {
        return getValues(Element::getItems);
    }

    public static void register() {
        for (Element element : getElements()) {
            element.register();
        }
    }
}
