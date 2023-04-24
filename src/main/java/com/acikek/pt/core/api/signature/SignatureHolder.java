package com.acikek.pt.core.api.signature;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public interface SignatureHolder {

    List<ElementSignature> signature();

    default Text stylizeSignature(MutableText tooltip) {
        return tooltip.formatted(Formatting.GRAY);
    }

    default Text createTooltip() {
        return stylizeSignature(ElementSignature.createTooltip(signature()).copy());
    }
}
