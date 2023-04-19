package com.acikek.pt.core.element;

import com.acikek.pt.core.impl.element.ElementImpl;
import com.acikek.pt.core.impl.element.ElementSignatureImpls;
import com.acikek.pt.core.lang.ElementNames;
import com.acikek.pt.core.refined.ElementRefinedState;
import com.acikek.pt.core.refined.RefinedStates;
import com.acikek.pt.core.source.ElementSource;
import com.acikek.pt.core.source.ElementSources;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Elements {

    public static Element full(Object naming, Object sources, ElementRefinedState state) {
        return new ElementImpl(ElementNames.forObject(naming), ElementSource.forObject(sources), state);
    }

    public static Element noSource(Object naming, ElementRefinedState state) {
        return full(naming, Collections.emptyList(), state);
    }

    public static Element gas(Object naming, int atmosphericMin, int atmosphericMax) {
        return full(naming, ElementSources.atmospheric(atmosphericMin, atmosphericMax), RefinedStates.gas());
    }

    public static ElementSignature.Entry amount(Element element, int amount) {
        return new ElementSignature.Entry(element, amount);
    }

    public static ElementSignature.Entry amount(Element element) {
        return amount(element, 1);
    }

    public static ElementSignature single(Element element, int amount) {
        return new ElementSignatureImpls.Single(amount(element, amount));
    }

    public static ElementSignature single(Element element) {
        return single(element, 1);
    }

    public static ElementSignature random(List<Element> elements, int amount) {
        return new ElementSignatureImpls.Random(elements, amount);
    }

    public static ElementSignature random(int amount, Element... elements) {
        return random(Arrays.stream(elements).toList(), amount);
    }

    public static ElementSignature wrap(List<ElementSignature.Entry> entries, int multiplier) {
        return new ElementSignatureImpls.Wrapped(entries, multiplier);
    }

    public static ElementSignature wrap(int multiplier, ElementSignature.Entry... entries) {
        return wrap(Arrays.stream(entries).toList(), multiplier);
    }

    public static ElementSignature hydrate(int amount) {
        return new ElementSignatureImpls.Hydration(amount);
    }

    public static ElementSignature hydrate() {
        return hydrate(1);
    }
}
