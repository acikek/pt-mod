package com.acikek.pt.core.api.content;

import com.acikek.pt.api.datagen.DatagenDelegator;
import com.acikek.pt.api.request.FeatureRequests;
import com.acikek.pt.api.request.RequestEvent;
import com.acikek.pt.core.api.AbstractPeriodicTable;
import com.acikek.pt.core.api.element.Element;
import com.acikek.pt.core.api.mineral.MineralResultHolder;
import com.acikek.pt.core.api.registry.ElementIds;
import com.acikek.pt.core.api.registry.PTRegistry;
import com.acikek.pt.core.api.source.ElementSource;
import com.acikek.pt.core.api.source.ElementSources;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public interface ContentBase<T extends ContentContext> extends DatagenDelegator, MineralResultHolder {

    /**
     * @return an identifier specifying the type of this source.
     * For example, ore sources return {@link ElementSources#ORE}.
     */
    @NotNull Identifier getTypeId();

    /**
     * @return whether this content is an instance of the specified type.
     * @see ElementSources
     */
    default boolean isType(Identifier id) {
        return getTypeId().equals(id);
    }

    /**
     * @return whether this content can only be added to a single element.
     */
    default boolean isExclusive() {
        return false;
    }

    /**
     * @return whether this content has already been added to <b>any</b> element.
     * Can be {@code false} if this information is not relevant.
     */
    default boolean isAdded(T context) {
        return false;
    }

    /**
     * Called after this content has been added to an element but before registry.
     * @throws IllegalStateException if {@link ElementSource#isExclusive()} and {@link ElementSource#isAdded(Element)} are both {@code true}.
     */
    default void onAdd(T context) {
        if (isExclusive() && isAdded(context)) {
            throw new IllegalStateException("exclusive source '" + this + "' is already attached; cannot be added to element '" + context.parent() + "'");
        }
    }

    /**
     * Registers all content associated with this base.
     * @param registry the registry associated with the element's {@link AbstractPeriodicTable}
     * @param ids the ID util from the parent {@link Element}
     * @param features features requested from this content type by the {@link RequestEvent}
     */
    void register(PTRegistry registry, ElementIds<String> ids, T context, FeatureRequests.Content features);

    /*static List<ElementSource> forObject(Object obj) {
        if (obj instanceof ElementSource source) {
            return List.of(source);
        }
        if (obj instanceof List<?> list) {
            return (List<ElementSource>) list;
        }
        throw new IllegalStateException("ElementSource list can only be derived from an actual list or a single instance");
    }*/
}
