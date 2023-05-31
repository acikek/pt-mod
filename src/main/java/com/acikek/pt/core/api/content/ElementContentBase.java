package com.acikek.pt.core.api.content;

import com.acikek.pt.api.datagen.DatagenDelegator;
import com.acikek.pt.core.api.data.DataHolder;
import com.acikek.pt.core.api.element.Element;
import com.acikek.pt.core.api.mineral.MineralResultHolder;
import com.acikek.pt.core.api.refined.RefinedStates;
import com.acikek.pt.core.api.registry.ElementIds;
import com.acikek.pt.core.api.source.ElementSources;
import com.acikek.pt.core.api.source.MaterialHolder;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * @param <C> the context type passed to this content in registry requests and callbacks
 * @param <D> the public data type exposed from implementation details
 */
public interface ElementContentBase<D, C extends ContentContext> extends ContentBase, DataHolder<D>, DatagenDelegator, MineralResultHolder, MaterialHolder {

    /**
     * @return an identifier specifying the type of this content.
     * For example, ore sources return {@link ElementSources#ORE}.
     */
    @NotNull ContentIdentifier typeId();

    /**
     * @return whether this content is an instance of the specified type.
     * @see ElementContentBase#typeId()
     * @see ElementSources
     * @see RefinedStates
     */
    default boolean isType(Identifier id) {
        return typeId().equals(id);
    }

    /**
     * @return instance-specific content context to be used in most implementation methods
     */
    C context();

    /**
     * @see ContentContext#parent();
     */
    default Element parent() {
        return context().parent();
    }

    /**
     * @see ContentContext#contentIds()
     */
    default ElementIds<String> contentIds() {
        return context().contentIds();
    }

    /**
     * Sets an instance-specific content context value.
     * @throws IllegalStateException if the context has already been set
     */
    @ApiStatus.OverrideOnly
    default void setContext(C context) {
        if (context() != null) {
            throw new IllegalStateException("context is already set");
        }
    }

    /**
     * @return whether, at this pass of data generation, this content type
     * ({@link ElementContentBase#typeId()}) has built for the {@link ElementContentBase#parent()} element already
     */
    default boolean hasBuiltPass() {
        return parent().hasBuiltContentPass(typeId());
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
    default boolean isAdded() {
        return false;
    }

    /**
     * Called after this content has been added to an element but before registry.<br>
     * <b>{@link ElementContentBase#setContext(ContentContext)} is called here!</b>
     * @throws IllegalStateException if {@link ElementContentBase#isExclusive()} and {@link ElementContentBase#isAdded()} are both {@code true}.
     */
    default void onAdd(C context) {
        setContext(context);
        if (isExclusive() && isAdded()) {
            throw new IllegalStateException("exclusive source '" + this + "' is already attached; cannot be added to element '" + context.parent() + "'");
        }
    }
}
