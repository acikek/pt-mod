package com.acikek.pt.core.impl.signature;

import com.acikek.pt.core.api.signature.CompoundSignature;
import com.acikek.pt.core.api.signature.SignatureComponent;
import com.acikek.pt.core.api.signature.SignatureEntry;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.Collections;
import java.util.List;

/**
 * A compound signature implementation that makes heavy use of caching.
 */
public class CompoundSignatureImpl implements CompoundSignature {

    private final List<SignatureComponent> components;
    private final List<SignatureEntry> all;
    private final List<SignatureEntry> primaries;
    private final List<SignatureEntry> units;
    private final Text text;

    public CompoundSignatureImpl(List<SignatureComponent> components) {
        this.components = components;
        all = components.stream()
                .flatMap(result -> result.all().stream())
                .toList();
        primaries = all.stream()
                .filter(SignatureEntry::isPrimary)
                .toList();
        units = getUnits();
        text = getText();
    }

    private List<SignatureEntry> getUnits() {
        if (components.size() == 1) {
            var unit = components.get(0).getUnit();
            if (unit != null) {
                return Collections.singletonList(unit);
            }
        }
        return primaries.size() != 0
                ? primaries
                : Collections.emptyList();
    }

    private Text getText() {
        var sorted = components.stream()
                .sorted(SignatureComponent::sort)
                .toList();
        MutableText result = Text.empty();
        for (SignatureComponent entry : sorted) {
            result.append(entry.getDisplayText());
        }
        return result;
    }

    @Override
    public List<SignatureComponent> components() {
        return components;
    }

    @Override
    public List<SignatureEntry> all() {
        return all;
    }

    @Override
    public List<SignatureEntry> primaries() {
        return primaries;
    }

    @Override
    public List<SignatureEntry> units() {
        return units;
    }

    @Override
    public MutableText text() {
        return text.copy();
    }
}
