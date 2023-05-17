package com.acikek.pt.core.api.signature;

import com.acikek.pt.core.api.element.Element;
import com.acikek.pt.core.impl.signature.ElementSignatureImpls;

import java.util.Arrays;
import java.util.List;

public class Signatures {

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

    public static ElementSignature wrap(List<ElementSignature> signatures, int multiplier) {
        return new ElementSignatureImpls.Wrapped(signatures, multiplier);
    }

    public static ElementSignature wrap(int multiplier, ElementSignature... signatures) {
        return wrap(Arrays.stream(signatures).toList(), multiplier);
    }

    public static ElementSignature hydrate(int amount) {
        return new ElementSignatureImpls.Hydration(amount);
    }

    public static ElementSignature hydrate() {
        return hydrate(1);
    }
}
