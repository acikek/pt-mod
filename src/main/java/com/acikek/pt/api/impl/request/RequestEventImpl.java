package com.acikek.pt.api.impl.request;

import com.acikek.pt.api.request.FeatureRequests;
import com.acikek.pt.api.request.RequestEvent;
import com.acikek.pt.core.api.element.Element;
import com.acikek.pt.core.api.mineral.Mineral;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestEventImpl implements RequestEvent {

    private final Map<Mineral, FeatureRequests.Content> mineralRequests = new HashMap<>();
    private final Map<Element, FeatureRequests.Content> elementRequests = new HashMap<>();
    private final Map<Element, FeatureRequests.Sources> sourceRequests = new HashMap<>();

    private <T> void submitRequests(T key, Map<T, FeatureRequests.Content> map, List<Identifier> requests) {
        var features = map.computeIfAbsent(key, k -> FeatureRequests.Content.empty());
        features.requests().addAll(requests);
    }

    @Override
    public void submit(Mineral mineral, List<Identifier> requests) {
        submitRequests(mineral, mineralRequests, requests);
    }

    @Override
    public void all(Mineral mineral) {
        mineralRequests.put(mineral, FeatureRequests.Content.useAll());
    }

    @Override
    public void submit(Element element, List<Identifier> requests) {
        submitRequests(element, elementRequests, requests);
    }

    @Override
    public void all(Element element) {
        elementRequests.put(element, FeatureRequests.Content.useAll());
    }

    private FeatureRequests.Sources getSourcesMap(Element element) {
        return sourceRequests.computeIfAbsent(element, k -> FeatureRequests.Sources.empty());
    }

    @Override
    public void submit(Element element, Identifier sourceType, List<Identifier> requests) {
        var features = getSourcesMap(element)
                .requests()
                .computeIfAbsent(sourceType, k -> FeatureRequests.Content.empty());
        features.requests().addAll(requests);
    }

    @Override
    public void all(Element element, Identifier sourceType) {
        getSourcesMap(element).requests().put(sourceType, FeatureRequests.Content.useAll());
    }

    @Override
    public void allSourcesForElement(Element element) {
        sourceRequests.put(element, FeatureRequests.Sources.useAll());
    }

    @Override
    public Map<Mineral, FeatureRequests.Content> mineralRequests() {
        return mineralRequests;
    }

    @Override
    public Map<Element, FeatureRequests.Content> elementRequests() {
        return elementRequests;
    }

    @Override
    public Map<Element, FeatureRequests.Sources> elementSourceRequests() {
        return sourceRequests;
    }
}
