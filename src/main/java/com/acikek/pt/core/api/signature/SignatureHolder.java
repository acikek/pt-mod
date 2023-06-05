package com.acikek.pt.core.api.signature;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public interface SignatureHolder {

    CompoundSignature signature();

    default boolean hasSignature() {
        return signature() != null;
    }

    default Text stylizeSignature(MutableText tooltip) {
        return tooltip.formatted(Formatting.GRAY);
    }

    default Text getSignatureText() {
        return stylizeSignature(signature().text());
    }
}
