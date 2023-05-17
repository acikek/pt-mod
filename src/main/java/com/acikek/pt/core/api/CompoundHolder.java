package com.acikek.pt.core.api;

import com.acikek.pt.core.api.element.Element;
import com.acikek.pt.core.api.mineral.Mineral;

import java.util.List;
import java.util.function.Consumer;

public interface CompoundHolder {

    List<Mineral<?>> minerals();

    List<Element> elements();

    default void forEachMineral(Consumer<Mineral> fn) {
        for (Mineral<?> mineral : minerals()) {
            fn.accept(mineral);
        }
    }

    default void forEachElement(Consumer<Element> fn) {
        for (Element element : elements()) {
            fn.accept(element);
        }
    }
}
