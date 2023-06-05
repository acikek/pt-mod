package com.acikek.pt.core.api.signature;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.List;

public interface SignatureComponent {

    List<SignatureEntry> all();

    List<SignatureEntry> get(World world);

    SignatureEntry getUnit();

    default boolean isUnit() {
        return getUnit() != null;
    }

    Text getDisplayText();

    default int sort(SignatureComponent other) {
        return 0;
    }

    default CompoundSignature build() {
        return Signatures.of(this);
    }
}
