package com.acikek.pt.core.api.mineral;

import com.acikek.pt.core.impl.mineral.MineralBlock;
import com.acikek.pt.core.impl.mineral.MineralBlockBuilder;
import com.acikek.pt.core.api.signature.ElementSignature;

import java.util.List;
import java.util.function.Supplier;

public class Minerals {

    public static MineralBlockBuilder builder() {
        return new MineralBlockBuilder();
    }

    private static Mineral build(MineralBlockBuilder builder, Object naming, Supplier<List<ElementSignature>> supplier) {
        return builder.naming(naming).signature(supplier).build();
    }

    public static Mineral block(Object naming, Supplier<List<ElementSignature>> supplier) {
        return build(builder(), naming, supplier);
    }

    public static Mineral fullBlock(Object naming, Supplier<List<ElementSignature>> supplier) {
        return build(builder().addRawMineral().addCluster(), naming, supplier);
    }

    public static Mineral blockWithRawForm(Object naming, Supplier<List<ElementSignature>> supplier) {
        return build(builder().addRawMineral(), naming, supplier);
    }
}