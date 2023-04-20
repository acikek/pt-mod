package com.acikek.pt.core.signature;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.List;

public interface ElementSignature {

    List<ElementSignatureEntry> all();

    List<ElementSignatureEntry> get(World world);

    Text getDisplayText();

    default int sort(ElementSignature other) {
        return 0;
    }

    static Text createTooltip(List<ElementSignature> signatures) {
        var sorted = signatures.stream()
                .sorted(ElementSignature::sort)
                .toList();
        MutableText result = Text.empty();
        for (ElementSignature entry : sorted) {
            result.append(entry.getDisplayText());
        }
        return result;
    }
}
