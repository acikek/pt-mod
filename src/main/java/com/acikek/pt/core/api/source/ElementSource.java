package com.acikek.pt.core.api.source;

import com.acikek.pt.api.datagen.DatagenDelegator;
import com.acikek.pt.core.api.element.Element;
import com.acikek.pt.core.api.mineral.MineralResultHolder;
import com.acikek.pt.core.api.registry.ElementIds;
import com.acikek.pt.core.api.registry.PTRegistry;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ElementSource extends DatagenDelegator, MineralResultHolder {

    /**
     * @return an identifier specifying the type of this source.
     * For example, ore sources return {@link ElementSources#ORE}.
     */
    @NotNull Identifier getId();

    /**
     * @return whether this source is an instance of the specified type.
     * @see ElementSources
     */
    default boolean isType(Identifier id) {
        return getId().equals(id);
    }

    /**
     * @return whether this source can only be added to a single element.
     */
    default boolean isExclusive() {
        return false;
    }

    /**
     * @return whether this source has already been added to an element.
     * Can be {@code false} if this information is not relevant.
     */
    default boolean isAdded(Element element) {
        return false;
    }

    /**
     * Called after this source has been added to an element but before registry.
     * @throws IllegalStateException if {@link ElementSource#isExclusive()} and {@link ElementSource#isAdded(Element)} are both {@code true}.
     */
    default void onAdd(Element parent) {
        if (isExclusive() && isAdded(parent)) {
            throw new IllegalStateException("exclusive source '" + this + "' is already attached; cannot be added to element '" + parent + "'");
        }
    }

    void register(PTRegistry registry, ElementIds<String> ids, List<T> requests);

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
