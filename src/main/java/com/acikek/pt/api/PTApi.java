package com.acikek.pt.api;

import com.acikek.pt.api.impl.PTApiImpl;
import com.acikek.pt.api.impl.PTLoading;
import com.acikek.pt.core.api.AbstractPeriodicTable;
import com.acikek.pt.core.api.element.Element;
import com.acikek.pt.core.api.signature.CompoundSignature;
import com.acikek.pt.core.api.signature.SignatureAcceptor;
import net.minecraft.item.ItemConvertible;

import java.util.List;
import java.util.function.Consumer;

public class PTApi {

    public static List<AbstractPeriodicTable> tables() {
        if (PTLoading.tables == null) {
            throw new IllegalStateException("tables haven't been created yet");
        }
        return PTLoading.tables;
    }

    public static String subscript(String str) {
        return PTApiImpl.subscript(str);
    }

    public static String subscript(int amount) {
        return subscript(String.valueOf(amount));
    }

    public static String subscriptChecked(int amount) {
        return amount > 1
                ? subscript(amount)
                : "";
    }

    public static void forEachElement(Consumer<Element> fn) {
        for (AbstractPeriodicTable table : tables()) {
            table.forEachElement(fn);
        }
    }

    public static void setSignature(ItemConvertible item, CompoundSignature signature) {
        ((SignatureAcceptor) item.asItem()).setSignature(signature);
    }
}
