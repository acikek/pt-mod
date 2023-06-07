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
    @NotNull Map<ElementRefinedState<?>, List<ElementSource<?>>> sourceStateMap();

    /**
     * @return a list of the holder's refined states
     * @throws IllegalStateException if there are no refined states
     */
    default List<ElementRefinedState<?>> getRefinedStates() {
        if (sourceStateMap().isEmpty()) {
            throw new IllegalStateException("holder has no refined states");
        }
        return sourceStateMap().keySet().stream().toList();
    }

    /**
     * @return a list of each element source pointing to the refined states
     */
    default List<ElementSource<?>> getSources() {
        return sourceStateMap().values()
                .stream()
                .flatMap(List::stream)
                .toList();
    }

    private <T extends ElementContentBase<?, ?>> List<T> getByType(List<T> list, Identifier type) {
        return list.stream()
                .filter(content -> content.isType(type))
                .toList();
    }

    /**
     * @param stateType the state type to check against
     * @return a list of refined states that match the specified type
     * @see RefinedStates
     */
    default List<ElementRefinedState<?>> getRefinedStatesByType(Identifier stateType) {
        return getByType(getRefinedStates(), stateType);
    }

    /**
     * @param id the unique ID specific to the state to check against
     * @return the specific refined state, if found
     * @throws IllegalStateException if more than one refined state with the specified id is found
     */
    default @Nullable ElementRefinedState<?> getRefinedStateById(Identifier id) {
        var list = getRefinedStates().stream()
                .filter(state -> state.id().equals(id))
                .toList();
        if (list.size() > 1) {
            throw new IllegalStateException("there exists more than one refined state with the id '" + id + "'");
        }
        return !list.isEmpty()
                ? list.get(0)
                : null;
    }

    /**
     * @param sourceType the source type to check against
     * @return a list of element sources that match the specified type
     * @see ElementSources
     */
    default List<ElementSource<?>> getSourcesByType(Identifier sourceType) {
        return getByType(getSources(), sourceType);
    }

    /**
     * @return a list of all the refined states and element sources, in order of sources before states,
     * unified in a single interface
     */
    default List<ElementContentBase<?, ?>> getAllContent() {
        List<ElementContentBase<?, ?>> result = new ArrayList<>();
        result.addAll(getRefinedStates());
        result.addAll(getSources());
        return result;
    }

    /**
     * Maps over all refined states using the specified callback.
     * @see SourceStateMapper#getRefinedStates() 
     */
    default void forEachRefinedState(Consumer<ElementRefinedState<?>> fn) {
        for (var state : getRefinedStates()) {
            fn.accept(state);
        }
    }

    /**
     * Maps over all element sources using the specified callback.
     * @see SourceStateMapper#getSources() 
     */
    default void forEachSource(Consumer<ElementSource<?>> fn) {
        for (var source : getSources()) {
            fn.accept(source);
        }
    }

    /**
     * Maps over all content bases, including all refined states and element sources, using the specified callback.
     * @see SourceStateMapper#getAllContent() 
     */
    default void forEachContent(Consumer<ElementContentBase<?, ?>> fn) {
        for (var content : getAllContent()) {
            fn.accept(content);
        }
    }

    /**
     * Maps over all element sources with the added context of its connected refined state.
     */
    default void forEachSource(BiConsumer<ElementSource<?>, ElementRefinedState<?>> fn) {
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
     * @return the only refined state, also known as the "main" state, used by this holder
     * @throws IllegalStateException if this holder does not have exactly one refined state
     * @see SourceStateMapper#hasSingleState()
     *
     */
    default ElementRefinedState<?> getSingleState() {
        if (!hasSingleState()) {
            throw new IllegalStateException("state holder has multiple refined states");
        }
        return getRefinedStates().get(0);
    }

    /**
     * Adds a source to a specific refined state in this holder.
     */
    void addSource(ElementSource<?> source, ElementRefinedState<?> toState);

    /**
     * Adds a source to the "main" refined state, if any.
     * @see SourceStateMapper#getSingleState()
     */
    default void addSource(ElementSource<?> source) {
        addSource(source, getSingleState());
    }

    /**
     * Adds a source to the refined state with the specified ID.
     * @throws IllegalStateException if no refined state is found
     * @see SourceStateMapper#getRefinedStateById(Identifier) 
     */
    default void addSource(ElementSource<?> source, Identifier stateId) {
        var state = getRefinedStateById(stateId);
        if (state == null) {
            throw new IllegalStateException("refined state '" + stateId + "' was not found");
        }
        addSource(source, state);
    }

    /*
        private void throwDuplicate(Identifier type, Identifier id) {
        throw new IllegalStateException("duplicate '" + type + "' content: '" + id + "'");
    }

    private boolean checkForMain(Collection<Identifier> ids) {
        if ()
    }
     */

    private boolean validateStates() {
        List<Identifier> checked = new ArrayList<>();
        for (var state : getRefinedStates()) {
            if (checked.contains(state.id())) {
                throw new IllegalStateException("duplicate '" + state.typeId().type() + "' content: '" + state.id() + "'");
            }
            checked.add(state.id());
        }
        if (!checked.contains(ElementRefinedState.MAIN)) {
            throw new IllegalStateException("must contain one 'main' refined state");
        }
        return true;
    }

    private boolean validateSources() {
        Map<ContentIdentifier, List<Identifier>> checked = new HashMap<>();
        for (var source : getSources()) {
            var list = checked.computeIfAbsent(source.typeId(), k -> new ArrayList<>());
            if (list.contains(source.id())) {
                throw new IllegalStateException("duplicate '" + source.typeId() + "' content: '" + source.id() + "'");
            }
            list.add(source.id());
        }
        return true;
    }

    /**
     * Checks all content {@link ElementContentBase#id()}s to make sure they are unique for
     * their content types and, for {@link ElementSource}s, their {@link ElementContentBase#typeId()}s.
     * @return whether the validation was successful
     * @throws IllegalStateException if the validation fails
     */
    default boolean validateContent() {
        return validateStates() && validateSources();
    }
}
