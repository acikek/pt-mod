package com.acikek.pt.core.mineral;

import com.acikek.pt.core.element.Element;
import com.acikek.pt.core.element.ElementalObjects;
import com.acikek.pt.core.impl.mineral.MineralResultImpls;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class Minerals {

    public static MineralResult.Pair amount(Element element, int amount) {
        return new MineralResult.Pair(element, amount);
    }

    public static MineralResult single(Element element, int amount) {
        return new MineralResultImpls.Single(amount(element, amount));
    }

    public static MineralResult random(List<MineralResult.Pair> pairs) {
        return new MineralResultImpls.Random(pairs);
    }

    public static MineralResult random(MineralResult.Pair... pairs) {
        return random(Arrays.stream(pairs).toList());
    }

    public static MineralBlock block(String name, Supplier<List<MineralResult>> supplier) {
        return new MineralBlock(ElementalObjects.MINERAL_SETTINGS, name, supplier);
    }
}
