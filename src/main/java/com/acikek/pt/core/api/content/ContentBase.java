package com.acikek.pt.core.api.content;

import com.acikek.pt.api.datagen.DatagenDelegator;
import com.acikek.pt.api.request.FeatureRequests;
import com.acikek.pt.api.request.event.RequestEvent;
import com.acikek.pt.core.api.AbstractPeriodicTable;
import com.acikek.pt.core.api.data.DataHolder;
import com.acikek.pt.core.api.element.Element;
import com.acikek.pt.core.api.mineral.MineralResultHolder;
import com.acikek.pt.core.api.registry.ElementIds;
import com.acikek.pt.core.api.registry.PTRegistry;
import com.acikek.pt.core.api.source.ElementSource;
import com.acikek.pt.core.api.source.ElementSources;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

/**
 * @param <C> the context type passed to this content in registry requests and callbacks
 * @param <D> the public data type exposed from implementation details
 */
public interface ContentBase<D, C extends ContentContext> extends DataHolder<D>, DatagenDelegator, MineralResultHolder {

    /**
     * @return an identifier specifying the type of this content.
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
    default boolean isAdded(C context) {
        return false;
    }

    /**
     * Called after this content has been added to an element but before registry.
     * @throws IllegalStateException if {@link ContentBase#isExclusive()} and {@link ContentBase#isAdded(ContentContext)} are both {@code true}.
     */
    default void onAdd(C context) {
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
    void register(PTRegistry registry, ElementIds<String> ids, C context, FeatureRequests.Single features);

    /**
     * Initializes content on the client <b>after</b>
     * {@link ContentBase#register(PTRegistry, ElementIds, ContentContext, FeatureRequests.Single)} has been called
     * on the common side.
     */
    @Environment(EnvType.CLIENT)
    default void initClient(C context) {
        // Empty
    }
}
