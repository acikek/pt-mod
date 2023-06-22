package com.acikek.pt.core.api.content.element;

import com.acikek.pt.core.api.refined.ElementRefinedState;
import com.acikek.pt.core.api.refined.RefinedStates;
import com.acikek.pt.core.api.source.ElementSource;
import com.acikek.pt.core.api.source.ElementSources;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface SourceStateMapper {

    /**
     * @return refined states with at least one element source pointing to them.
     * Holders should always have at least one refined state in this map even if they do not map to any sources.
     */
    @NotNull Map<ElementRefinedState, List<ElementSource>> sourceStateMap();

    /**
     * @return a list of the holder's refined states
     * @throws IllegalStateException if there are no refined states
     */
    default List<ElementRefinedState> getRefinedStates() {
        if (sourceStateMap().isEmpty()) {
            throw new IllegalStateException("holder has no refined states");
        }
        return sourceStateMap().keySet()
                .stream()
                .flatMap(state -> state.allContent().stream())
                .toList();
    }

    /**
     * @return a list of each element source pointing to the refined states
     */
    default List<ElementSource> getSources() {
        return sourceStateMap().values()
                .stream()
                .flatMap(List::stream)
                .flatMap(source -> source.allContent().stream())
                .toList();
    }

    private <T extends ElementContentBase<?>> List<T> getByType(List<T> list, ContentIdentifier type) {
        return list.stream()
                .filter(content -> content.isType(type))
                .toList();
    }

    private <T extends ElementContentBase<?>> List<T> getById(List<T> list, Identifier id) {
        return list.stream()
                .filter(content -> content.isInstance(id))
                .toList();
    }

    /**
     * @param stateType the state type to check against
     * @return a list of refined states that match the specified type
     * @see RefinedStates
     */
    default List<ElementRefinedState> getRefinedStatesByType(ContentIdentifier stateType) {
        return getByType(getRefinedStates(), stateType);
    }

    /**
     * @param id the state instance ID to check against
     * @return the specific refined state, if found
     */
    default @Nullable ElementRefinedState getRefinedStateById(Identifier id) {
        return getById(getRefinedStates(), id).get(0);
    }

    /**
     * @param sourceType the source type to check against
     * @return a list of element sources that match the specified type
     * @see ElementSources
     */
    default List<ElementSource> getSourcesByType(ContentIdentifier sourceType) {
        return getByType(getSources(), sourceType);
    }

    /**
     * @param id the source instance ID to check against
     * @return the specific source, if found
     */
    default @Nullable ElementSource getSourceById(Identifier id) {
        return getById(getSources(), id).get(0);
    }

    /**
     * @return a list of sources mapping to the specified refined state, if any
     */
    default List<ElementSource> getSourcesForState(ElementRefinedState state) {
        return sourceStateMap().get(state);
    }

    /**
     * @return a list of all the refined states and element sources, in order of sources before states,
     * unified in a single interface
     */
    default List<ElementContentBase<?>> getAllContent() {
        List<ElementContentBase<?>> result = new ArrayList<>();
        result.addAll(getRefinedStates());
        result.addAll(getSources());
        return result;
    }

    /**
     * Maps over all refined states using the specified callback.
     * @see SourceStateMapper#getRefinedStates() 
     */
    default void forEachRefinedState(Consumer<ElementRefinedState> fn) {
        for (var state : getRefinedStates()) {
            fn.accept(state);
        }
    }

    /**
     * Maps over all element sources using the specified callback.
     * @see SourceStateMapper#getSources() 
     */
    default void forEachSource(Consumer<ElementSource> fn) {
        for (var source : getSources()) {
            fn.accept(source);
        }
    }

    /**
     * Maps over all content bases, including all refined states and element sources, using the specified callback.
     * @see SourceStateMapper#getAllContent() 
     */
    default void forEachContent(Consumer<ElementContentBase<?>> fn) {
        for (var content : getAllContent()) {
            fn.accept(content);
        }
    }

    /**
     * Maps over all element sources with the added context of its connected refined state.
     */
    default void forEachSource(BiConsumer<ElementSource, ElementRefinedState> fn) {
        for (var entry : sourceStateMap().entrySet()) {
            for (var source : entry.getValue()) {
                fn.accept(source, entry.getKey());
            }
        }
    }

    /**
     * @return whether this holder has any refined states, and therefore, any element sources
     */
    default boolean hasSources() {
        return !getSources().isEmpty();
    }

    /**
     * @return whether this holder has only one refined state
     */
    default boolean hasSingleState() {
        return sourceStateMap().size() == 1;
    }

    /**
     * @return the primary refined state used by this holder
     * @throws IllegalStateException if a single primary state is not found
     */
    default ElementRefinedState getPrimaryState() {
        if (hasSingleState()) {
            return getRefinedStates().get(0);
        }
        var primaries = getRefinedStates().stream()
                .filter(ElementRefinedState::isPrimary)
                .toList();
        if (primaries.size() != 1) {
            throw new IllegalStateException("must have exactly one primary refined state (actual: " + primaries.size() + ")");
        }
        return primaries.get(0);
    }

    /**
     * Adds a source to a specific refined state in this holder.
     */
    void addSource(ElementSource source, ElementRefinedState toState);

    /**
     * Adds a source to the primary refined state, if any.
     * @see SourceStateMapper#getPrimaryState()
     */
    default void addSource(ElementSource source) {
        addSource(source, getPrimaryState());
    }

    /**
     * Adds a source to the refined state with the specified ID.
     * @throws IllegalStateException if no refined state is found
     * @see SourceStateMapper#getRefinedStateById(Identifier) 
     */
    default void addSource(ElementSource source, Identifier stateId) {
        var state = getRefinedStateById(stateId);
        if (state == null) {
            throw new IllegalStateException("refined state '" + stateId + "' was not found");
        }
        addSource(source, state);
    }

    /**
     * Checks all content {@link ElementContentBase#id()}s to make sure they are unique for
     * their content {@link ElementContentBase#typeId()}s and that they have at least one {@link ElementContentBase#MAIN} instance per type.<br>
     * Additionally checks that this mapper's {@link ElementRefinedState}s have a single primary state.
     * @return whether the validation was successful
     * @throws IllegalStateException if the validation fails
     */
    default boolean validateContent() {
        Map<ContentIdentifier, List<Identifier>> checked = new HashMap<>();
        for (var content : getAllContent()) {
            var list = checked.computeIfAbsent(content.typeId(), k -> new ArrayList<>());
            if (list.contains(content.id())) {
                throw new IllegalStateException("duplicate '" + content.typeId() + "' content: '" + content.id() + "'");
            }
            list.add(content.id());
        }
        for (var entry : checked.entrySet()) {
            if (!entry.getValue().contains(ElementContentBase.MAIN)) {
                throw new IllegalStateException("content type '" + entry.getKey() + "' does not have a " + ElementContentBase.MAIN + " instance");
            }
        }
        return getPrimaryState() != null;
    }
}
