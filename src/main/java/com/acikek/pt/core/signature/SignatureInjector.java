package com.acikek.pt.core.signature;

import java.util.List;

public interface SignatureInjector extends SignatureHolder {

    void setSignature(List<ElementSignature> signature);
}
