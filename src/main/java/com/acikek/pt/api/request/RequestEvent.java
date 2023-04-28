package com.acikek.pt.api.request;

import com.acikek.pt.core.api.AbstractPeriodicTable;
import com.acikek.pt.core.api.element.Element;
import com.acikek.pt.core.api.mineral.Mineral;
import net.minecraft.util.Identifier;

import java.util.*;

public interface RequestEvent {

    void submit(Mineral mineral, List<Identifier> requests);

    default void submit(Mineral mineral, Identifier... requests) {
        submit(mineral, Arrays.stream(requests).toList());
    }

    default void submit(Mineral mineral, Identifier request) {
        submit(mineral, Collections.singletonList(request));
    }

    void all(Mineral mineral);

    default void allMinerals(AbstractPeriodicTable table) {
        table.forEachMineral(this::all);
    }

    void submit(Element element, List<Identifier> requests);

    default void submit(Element element, Identifier... requests) {
        submit(element, Arrays.stream(requests).toList());
    }

    default void submit(Element element, Identifier request) {
        submit(element, Collections.singletonList(request));
    }

    void all(Element element);

    default void allElements(AbstractPeriodicTable table) {
        table.forEachElement(this::all);
    }

    void submit(Element element, Identifier sourceType, List<Identifier> requests);

    default void submit(Element element, Identifier sourceType, Identifier... requests) {
        submit(element, sourceType, Arrays.stream(requests).toList());
    }

    default void submit(Element element, Identifier sourceType, Identifier request) {
        submit(element, sourceType, Collections.singletonList(request));
    }

    void all(Element element, Identifier sourceType);

    void allSourcesForElement(Element element);

    default void allSources(AbstractPeriodicTable table, Identifier sourceType) {
        table.forEachElement(element -> all(element, sourceType));
    }

    default void allSources(AbstractPeriodicTable table) {
        table.forEachElement(this::allSourcesForElement);
    }

    default void all(AbstractPeriodicTable table) {
        allMinerals(table);
        allSources(table);
    }

    Map<Mineral, FeatureRequests.Content> mineralRequests();

    default FeatureRequests.Content getRequestsForMineral(Mineral mineral) {
        return mineralRequests().getOrDefault(mineral, FeatureRequests.Content.empty());
    }
    
    Map<Element, FeatureRequests.Content> elementRequests();
    
    default FeatureRequests.Content getRequestsForElement(Element element) {
        return elementRequests().getOrDefault(element, FeatureRequests.Content.empty());
    }

    Map<Element, FeatureRequests.Sources> elementSourceRequests();

    default FeatureRequests.Sources getSourceRequestsForElement(Element element) {
        return elementSourceRequests().getOrDefault(element, FeatureRequests.Sources.empty());
    }
}
