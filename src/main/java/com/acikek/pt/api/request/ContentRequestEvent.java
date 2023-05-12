package com.acikek.pt.api.request;

import com.acikek.pt.core.api.AbstractPeriodicTable;
import com.acikek.pt.core.api.PeriodicTable;
import com.acikek.pt.core.api.element.Element;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public interface ContentRequestEvent {

    void submit(Element element, Identifier contentType, List<Identifier> requests);

    default void submit(Element element, Identifier contentType, Identifier... requests) {
        submit(element, contentType, Arrays.stream(requests).toList());
    }

    default void submit(Element element, Identifier contentType, Identifier request) {
        submit(element, contentType, Collections.singletonList(request));
    }

    void all(Element element);

    void all(Element element, Identifier contentType);

    default void all(AbstractPeriodicTable table) {
        table.forEachElement(this::all);
    }

    default void all() {
        all(PeriodicTable.INSTANCE);
    }

    default void all(AbstractPeriodicTable table, Identifier contentType) {
        table.forEachElement(element -> all(element, contentType));
    }

    default void all(Identifier contentType) {
        all(PeriodicTable.INSTANCE, contentType);
    }

    Map<Element, FeatureRequests.Content> requests();

    default FeatureRequests.Content getRequests(Element element) {
        return requests().getOrDefault(element, FeatureRequests.Content.empty());
    }
}
