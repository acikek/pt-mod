package com.acikek.pt.core.api.content;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * <p>
 *     A container for content that may be created at some point in time but of which there is no guarantee.
 * </p>
 *
 * <p>
 *     This is useful for {@link ElementContentBase} implementations, wherein due to the request system, content may not be
 *     registered; however, all created content must be registered, so this content must not be created either.
 * </p>
 *
 * <pre>
 * {@code
 *
 * PhasedContent<Block> block = PhasedContent.of(() -> new Block(...));
 * if (someCondition) {
 *     registerBlock(block.create());
 * }
 * Block result = block.get(); // the Block instance if created, null otherwise
 * Block required = block.require(); // not null, but errors if not created
 * }
 * </pre>
 */
public class PhasedContent<T> {

    protected Supplier<T> supplier;
    protected Optional<T> value = Optional.empty();

    private PhasedContent(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    /**
     * Creates a phased content instance.<br>
     * The specified supplier can return {@code null}, but the supplier itself must not be {@code null}.
     */
    public static <T> PhasedContent<T> of(@NotNull Supplier<T> supplier) {
        Objects.requireNonNull(supplier);
        return new PhasedContent<>(supplier);
    }

    /**
     * @return a phased content implementation wherein creation calls do nothing
     */
    public static <T> PhasedContent<T> none() {
        return new Null<>();
    }

    /**
     * Creates a nullable phased content instance.<br>
     * If the specified supplier is {@code null}, returns {@link PhasedContent#none()}
     */
    public static <T> PhasedContent<T> ofNullable(@Nullable Supplier<T> supplier) {
        return supplier != null
                ? of(supplier)
                : none();
    }

    /**
     * Creates a phased content instance where the value already exists.<br>
     * The existing value cannot be {@code null}.
     */
    public static <T> PhasedContent<T> existing(@NotNull T existing) {
        Objects.requireNonNull(existing);
        return new MayExist<>(existing, null);
    }

    /**
     * Creates a phased content instance from a generic {@link Object}.
     *
     * <ul>
     *     <li>Uses {@link PhasedContent#existing(Object)} if the object is of type {@code T}</li>
     *     <li>Uses {@link PhasedContent#of(Supplier)} if the object is a {@link Supplier}</li>
     * </ul>
     *
     * @throws IllegalStateException if no suitable constructor can be found
     */
    @SuppressWarnings("unchecked")
    public static <T> PhasedContent<T> from(Object content, Class<T> clazz) {
        if (clazz.isInstance(content)) {
            return existing(clazz.cast(content));
        }
        if (content instanceof Supplier<?> supplier) {
            return of((Supplier<T>) supplier);
        }
        throw new IllegalStateException("object must be either a content value or a supplier");
    }

    /**
     * Retrieves, caches, and returns the value from the original supplier.
     * <b>This can only be done once!</b>
     * @return the created value
     * @throws IllegalStateException if the value has already been created
     * @see PhasedContent#isCreated()
     */
    public T create() {
        if (value.isPresent()) {
            throw new IllegalStateException("phased content has already been created");
        }
        value = Optional.of(supplier.get());
        supplier = null;
        return value.get();
    }

    /**
     * @return the created value
     * @throws IllegalStateException if the value has not been created
     * @see PhasedContent#create()
     * @see PhasedContent#get()
     */
    public T require() {
        if (!isCreated()) {
            throw new IllegalStateException("phased content was never created");
        }
        return get();
    }

    /**
     * @return the value if created, otherwise {@code null}
     * @see PhasedContent#create()
     * @see PhasedContent#require()
     */
    public @Nullable T get() {
        return value.orElse(null);
    }

    /**
     * @return whether the value has been created
     * @see PhasedContent#create()
     */
    public boolean isCreated() {
        return value.isPresent();
    }

    /**
     * @return whether this content existed before it was created
     */
    public boolean isExternal() {
        return false;
    }

    /**
     * @return the opposite of {@link PhasedContent#isExternal()}
     */
    public boolean isInternal() {
        return !isExternal();
    }

    /**
     * @return whether the value is able to be created or already exists
     */
    public boolean canExist() {
        return true;
    }

    /**
     * Executes a runnable if {@link PhasedContent#isExternal()} is not {@code true};
     * that is, if this content was created and did not exist beforehand
     */
    public void ifInternal(Runnable fn) {
        if (isInternal()) {
            fn.run();
        }
    }

    /**
     * Creates the content and then, if this content is internal, executes the specified callback.
     * @see PhasedContent#create()
     * @see PhasedContent#ifInternal(Runnable)
     */
    public void create(Consumer<T> ifInternal) {
        var value = create();
        ifInternal(() -> ifInternal.accept(value));
    }

    /**
     * Requires and executes the specified callback if this content is internal.
     * @see PhasedContent#require()
     * @see PhasedContent#ifInternal(Runnable)
     */
    public void require(Consumer<T> ifInternal) {
        ifInternal(() -> ifInternal.accept(require()));
    }

    /**
     * Executes the callback if the content has been created,
     * regardless of whether the value is internal.
     */
    public void ifCreated(Consumer<T> callback) {
        if (isCreated()) {
            callback.accept(require());
        }
    }

    /**
     * @return the members of the specified collection that have been created
     */
    public static <T> List<PhasedContent<T>> filterByCreation(Collection<PhasedContent<T>> containers) {
        return containers.stream()
                .filter(PhasedContent::isCreated)
                .toList();
    }

    /**
     * @return the <b>values</b> of the specified content collection that have been created
     * @see PhasedContent#filterByCreation(Collection)
     */
    public static <T> List<T> getByCreation(Collection<PhasedContent<T>> containers) {
        return filterByCreation(containers).stream()
                .map(PhasedContent::get)
                .toList();
    }

    /**
     * @see PhasedContent#filterByCreation(Collection)
     */
    public static <T> List<PhasedContent<T>> filterByCreation(PhasedContent<T>... containers) {
        return filterByCreation(Arrays.stream(containers).toList());
    }

    /**
     * @see PhasedContent#getByCreation(Collection)
     */
    public static <T> List<T> getByCreation(PhasedContent<T>... containers) {
        return getByCreation(Arrays.stream(containers).toList());
    }

    /**
     * An extension of {@link PhasedContent} that can accept an existing value as the initial value.
     */
    private static class MayExist<T> extends PhasedContent<T> {

        private final T created;

        private MayExist(T created, Supplier<T> supplier) {
            super(supplier);
            this.created = created;
        }

        @Override
        public T create() {
            if (supplier != null) {
                return super.create();
            }
            value = Optional.of(created);
            return created;
        }

        @Override
        public boolean isExternal() {
            return true;
        }
    }

    /**
     * An extension of {@link PhasedContent} where the content explicitly never exists
     */
    private static class Null<T> extends PhasedContent<T> {

        private Null() {
            super(() -> null);
        }

        @Override
        public T create() {
            // Empty
            return null;
        }

        @Override
        public boolean isExternal() {
            return true;
        }

        @Override
        public boolean canExist() {
            return false;
        }
    }
}
