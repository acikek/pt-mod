package com.acikek.pt.core.element;

import java.util.List;
import java.util.function.Consumer;

public interface ElementHolder {

    List<Element> elements();

    default void forEachElement(Consumer<Element> fn) {
        for (Element element : elements()) {
            fn.accept(element);
        }
    }
}
