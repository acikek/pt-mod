package com.acikek.pt.core.api.signature;

import com.acikek.pt.core.api.element.Element;
import com.acikek.pt.core.impl.signature.CompoundSignatureImpl;
import com.acikek.pt.core.impl.signature.ElementSignatureImpls;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Signatures {

    public static SignatureEntry amount(Element element, int amount) {
        return new SignatureEntry(element, amount);
    }

    public static SignatureEntry single(Element element) {
        return amount(element, 1);
    }

    public static SignatureComponent unit(Element element, int amount) {
        return new ElementSignatureImpls.Unit(amount(element, amount));
    }

    public static SignatureComponent unit(Element element) {
        return unit(element, 1);
    }

    public static SignatureComponent random(List<SignatureEntry> singleEntries, int amount) {
        return new ElementSignatureImpls.Random(singleEntries, amount);
    }

    public static SignatureComponent random(int amount, Object... singleEntries) {
        return random(Arrays.stream(singleEntries).map(SignatureEntry::forObject).toList(), amount);
    }

    public static SignatureComponent wrap(List<SignatureComponent> signatures, int multiplier) {
        return new ElementSignatureImpls.Wrapped(signatures, multiplier);
    }

    public static SignatureComponent wrap(int multiplier, SignatureComponent... signatures) {
        return wrap(Arrays.stream(signatures).toList(), multiplier);
    }

    public static SignatureComponent hydrate(int amount) {
        return new ElementSignatureImpls.Hydration(amount);
    }

    public static SignatureComponent hydrate() {
        return hydrate(1);
    }

    public static CompoundSignature of(Collection<SignatureComponent> components) {
        return new CompoundSignatureImpl(List.copyOf(components));
    }

    public static CompoundSignature of(SignatureComponent... components) {
        return of(Arrays.stream(components).toList());
    }

    public static CompoundSignature of(SignatureComponent component) {
        return of(Collections.singletonList(component));
    }

    public static boolean isElementIn(List<SignatureEntry> entries, Element element, boolean primary) {
        for (SignatureEntry entry : entries) {
            if (entry.element() == element && (!primary || entry.isPrimary())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isElementIn(List<SignatureEntry> entries, Element element) {
        return isElementIn(entries, element, false);
    }
}
