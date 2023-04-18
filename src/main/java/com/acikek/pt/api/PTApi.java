package com.acikek.pt.api;

import com.acikek.pt.api.impl.PTLoading;
import com.acikek.pt.core.AbstractPeriodicTable;
import com.acikek.pt.core.element.Element;

import java.util.List;
import java.util.function.Consumer;

public class PTApi {

    public static List<AbstractPeriodicTable> tables() {
        if (PTLoading.tables == null) {
            throw new IllegalStateException("tables haven't been created yet");
        }
        return PTLoading.tables;
    }

    public static void forEachElement(Consumer<Element> fn) {
        for (AbstractPeriodicTable table : tables()) {
            for (Element element : table.getElements()) {
                fn.accept(element);
            }
        }
    }
}
