package com.acikek.pt.api.impl.request.event;

import com.acikek.pt.api.request.event.ContentRequestEvent;
import com.acikek.pt.api.request.FeatureRequests;
import com.acikek.pt.core.api.element.Element;
import net.minecraft.util.Identifier;

import java.util.*;

public class ContentRequestImpl implements ContentRequestEvent {

    private final Map<Element, FeatureRequests.Content> requestsMap = new HashMap<>();

    private FeatureRequests.Content getContent(Element element) {
        return requestsMap.computeIfAbsent(element, k -> FeatureRequests.Content.empty());
    }

    @Override
    public void submit(Element element, Identifier contentType, Collection<Identifier> requests) {
        var features = getContent(element)
                .requests()
                .computeIfAbsent(contentType, k -> FeatureRequests.Single.empty());
        features.requests().addAll(requests);
    }

    @Override
    public void all(Element element) {
        requestsMap.put(element, FeatureRequests.Content.useAll());
    }

    @Override
    public void all(Element element, Identifier contentType) {
        getContent(element).requests().put(contentType, FeatureRequests.Single.useAll());
    }

    @Override
    public Map<Element, FeatureRequests.Content> requests() {
        return requestsMap;
    }

    @Override
    public String toString() {
        return requestsMap.toString();
    }
}
