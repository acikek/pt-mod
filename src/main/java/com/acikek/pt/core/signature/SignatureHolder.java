package com.acikek.pt.core.signature;

import net.minecraft.text.Text;

import java.util.List;

public interface SignatureHolder {

    List<ElementSignature> signature();

    default Text createTooltip() {
        return ElementSignature.createTooltip(signature());
    }
}
