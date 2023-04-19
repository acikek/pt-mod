package com.acikek.pt.core.mineral;

import com.acikek.pt.core.element.Element;
import com.acikek.pt.core.element.ElementalObjects;
import com.acikek.pt.core.impl.mineral.MineralResultImpls;
import com.acikek.pt.core.lang.MineralNaming;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class Minerals {

    public static MineralResult.Entry amount(Element element, int amount) {
        return new MineralResult.Entry(element, amount);
    }

    public static MineralResult.Entry amount(Element element) {
        return amount(element, 1);
    }

    public static MineralResult single(Element element, int amount) {
        return new MineralResultImpls.Single(amount(element, amount));
    }

    public static MineralResult single(Element element) {
        return single(element, 1);
    }

    public static MineralResult random(List<Element> elements, int amount) {
        return new MineralResultImpls.Random(elements, amount);
    }

    public static MineralResult random(int amount, Element... elements) {
        return random(Arrays.stream(elements).toList(), amount);
    }

    public static MineralResult wrap(List<MineralResult.Entry> entries, int multiplier) {
        return new MineralResultImpls.Wrapped(entries, multiplier);
    }

    public static MineralResult wrap(int multiplier, MineralResult.Entry... entries) {
        return wrap(Arrays.stream(entries).toList(), multiplier);
    }

    public static MineralResult hydrate(int amount) {
        return new MineralResultImpls.Hydration(amount);
    }

    public static MineralResult hydrate() {
        return hydrate(1);
    }

    public static MineralBlock block(Object naming, Supplier<List<MineralResult>> supplier) {
        return new MineralBlock(ElementalObjects.MINERAL_SETTINGS, MineralNaming.forObject(naming), supplier);
    }
}
