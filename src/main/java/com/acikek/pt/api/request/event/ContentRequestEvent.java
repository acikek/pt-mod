package com.acikek.pt.api.request.event;

import com.acikek.pt.api.request.FeatureRequests;
import com.acikek.pt.api.request.RequestTypes;
import com.acikek.pt.core.api.AbstractPeriodicTable;
import com.acikek.pt.core.api.PeriodicTable;
import com.acikek.pt.core.api.element.Element;
import com.acikek.pt.core.api.refined.RefinedStates;
import com.acikek.pt.core.api.source.ElementSources;
import net.minecraft.util.Identifier;

import java.util.*;
import java.util.stream.Collectors;

public interface ContentRequestEvent {

    /**
     * Submits a set of requests to a specific content instance in the specified element.
     * @param contentType the "type" identifier of the relevant content
     * @see RefinedStates
     * @see ElementSources
     * @see RequestTypes
     */
    void submit(Element element, Identifier contentType, Set<Identifier> requests);

    /**
     * @see ContentRequestEvent#submit(Element, Identifier, Set)
     */
    default void submit(Element element, Identifier contentType, Identifier... requests) {
        submit(element, contentType, Arrays.stream(requests).collect(Collectors.toSet()));
    }

    /**
     * @see ContentRequestEvent#submit(Element, Identifier, Set)
     */
    default void submit(Element element, Identifier contentType, Identifier request) {
        submit(element, contentType, Collections.singleton(request));
    }

    /**
     * Submits a request for all features to the specified element.
     */
    void all(Element element);

    /**
     * Submits a request for all features to the specified content instance on the element.
     */
    void all(Element element, Identifier contentType);

    /**
     * Submits a request for all features to all elements created in the specified table.
     */
    default void all(AbstractPeriodicTable table) {
        table.forEachElement(this::all);
    }

    /**
     * Calls {@link ContentRequestEvent#all(AbstractPeriodicTable)} with {@link PeriodicTable#INSTANCE}.
     */
    default void all() {
        all(PeriodicTable.INSTANCE);
    }

    /**
     * Submits a request for all features to the specific content instances of all elements in the specified table.
     */
    default void all(AbstractPeriodicTable table, Identifier contentType) {
        table.forEachElement(element -> all(element, contentType));
    }

    /**
     * Calls {@link ContentRequestEvent#all(AbstractPeriodicTable, Identifier)} with {@link PeriodicTable#INSTANCE}.
     */
    default void all(Identifier contentType) {
        all(PeriodicTable.INSTANCE, contentType);
    }

    /**
     * @return a map of minerals to content instances to request sets
     */
    Map<Element, FeatureRequests.Content> requests();

    /**
     * @return submitted content requests for the specified element
     */
    default FeatureRequests.Content getRequests(Element element) {
        return requests().getOrDefault(element, FeatureRequests.Content.empty());
    }
}
