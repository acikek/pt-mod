package com.acikek.pt.core.source;

import com.acikek.pt.api.datagen.DatagenDelegator;
import com.acikek.pt.core.element.Element;
import com.acikek.pt.core.registry.ElementIds;
import com.acikek.pt.core.registry.PTRegistry;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ElementSource extends DatagenDelegator {

    @NotNull Identifier getId();

    default boolean isType(Identifier id) {
        return getId().equals(id);
    }

    default boolean hasMineralResult() {
        return mineralResultItem() != null;
    }

    void register(PTRegistry registry, ElementIds<String> ids);

    default boolean isExclusive() {
        return false;
    }

    default boolean isAlreadyAdded(Element element) {
        return false;
    }

    default void onAdd(Element parent) {
        if (isExclusive() && isAlreadyAdded(parent)) {
            throw new IllegalStateException("exclusive source '" + this + "' is already attached; cannot be added to element '" + parent + "'");
        }
    }

    @SuppressWarnings("unchecked")
    static List<ElementSource> forObject(Object obj) {
        if (obj instanceof ElementSource source) {
            return List.of(source);
        }
        if (obj instanceof List<?> list) {
            return (List<ElementSource>) list;
        }
        throw new IllegalStateException("ElementSource list can only be derived from an actual list or a single instance");
    }
}
