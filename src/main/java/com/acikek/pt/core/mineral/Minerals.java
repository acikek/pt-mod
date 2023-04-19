package com.acikek.pt.core.mineral;

import com.acikek.pt.core.element.ElementSignature;
import com.acikek.pt.core.element.ElementalObjects;
import com.acikek.pt.core.lang.MineralNaming;

import java.util.List;
import java.util.function.Supplier;

public class Minerals {

    public static MineralBlock block(Object naming, Supplier<List<ElementSignature>> supplier) {
        return new MineralBlock(ElementalObjects.MINERAL_SETTINGS, MineralNaming.forObject(naming), supplier);
    }
}
