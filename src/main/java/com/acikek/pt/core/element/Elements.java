package com.acikek.pt.core.element;

import com.acikek.pt.core.impl.element.ElementImpl;
import com.acikek.pt.core.impl.signature.ElementSignatureImpls;
import com.acikek.pt.core.display.ElementDisplay;
import com.acikek.pt.core.refined.ElementRefinedState;
import com.acikek.pt.core.refined.RefinedStates;
import com.acikek.pt.core.signature.ElementSignature;
import com.acikek.pt.core.signature.ElementSignatureEntry;
import com.acikek.pt.core.source.ElementSource;
import com.acikek.pt.core.source.ElementSources;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Elements {

    public static Element full(Object naming, Object sources, ElementRefinedState state) {
        return new ElementImpl(ElementDisplay.forObject(naming), ElementSource.forObject(sources), state);
    }

    public static Element noSource(Object naming, ElementRefinedState state) {
        return full(naming, Collections.emptyList(), state);
    }

    public static Element gas(Object naming, int atmosphericMin, int atmosphericMax) {
        return full(naming, ElementSources.atmospheric(atmosphericMin, atmosphericMax), RefinedStates.gas());
    }

    public static ElementSignatureEntry amount(Element element, int amount) {
        return new ElementSignatureEntry(element, amount);
    }

    public static ElementSignatureEntry single(Element element) {
        return amount(element, 1);
    }

    public static ElementSignature unit(Element element, int amount) {
        return new ElementSignatureImpls.Unit(amount(element, amount));
    }

    public static ElementSignature unit(Element element) {
        return unit(element, 1);
    }

    public static ElementSignature random(List<ElementSignatureEntry> singleEntries, int amount) {
        return new ElementSignatureImpls.Random(singleEntries, amount);
    }

    public static ElementSignature random(int amount, Object... singleEntries) {
        return random(Arrays.stream(singleEntries).map(ElementSignatureEntry::forObject).toList(), amount);
    }

    public static ElementSignature wrap(List<ElementSignatureEntry> entries, int multiplier) {
        return new ElementSignatureImpls.Wrapped(entries, multiplier);
    }

    public static ElementSignature wrap(int multiplier, ElementSignatureEntry... entries) {
        return wrap(Arrays.stream(entries).toList(), multiplier);
    }

    public static ElementSignature hydrate(int amount) {
        return new ElementSignatureImpls.Hydration(amount);
    }

    public static ElementSignature hydrate() {
        return hydrate(1);
    }
}
