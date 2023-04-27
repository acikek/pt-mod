package com.acikek.pt.core.api.signature;

import net.minecraft.text.Text;

import java.util.List;

public interface SignatureInjector extends SignatureHolder {

    void setSignature(List<ElementSignature> signature);

    void setSignatureTooltip(Text tooltip);
}