package com.acikek.pt.core.api.mineral;

import com.acikek.pt.core.api.signature.CompoundSignature;
import com.acikek.pt.core.impl.mineral.MineralBuilder;
import com.acikek.pt.core.api.signature.SignatureComponent;

import java.util.List;
import java.util.function.Supplier;

public class Minerals {

    public static MineralBuilder builder() {
        return new MineralBuilder();
    }

    private static Mineral<?> build(MineralBuilder builder, Object naming, Supplier<CompoundSignature> supplier) {
        return builder.naming(naming).signature(supplier).build();
    }

    public static Mineral<?> block(Object naming, Supplier<CompoundSignature> supplier) {
        return build(builder().addBlock(), naming, supplier);
    }

    public static Mineral<?> fullBlock(Object naming, Supplier<CompoundSignature> supplier) {
        return build(builder().addBlock().addRawMineral().addCluster(), naming, supplier);
    }

    public static Mineral<?> blockWithRawForm(Object naming, Supplier<CompoundSignature> supplier) {
        return build(builder().addBlock().addRawMineral(), naming, supplier);
    }
}
