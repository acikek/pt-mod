package com.acikek.pt.core.api.registry;

import com.acikek.pt.core.api.source.ElementSource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * <p>
 *     A container for content that may be created at some point in time but of which there is no guarantee.
 * </p>
 *
 * <p>
 *     This is useful for {@link ElementSource} implementations, wherein due to the request system, content may not be
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

    private Supplier<T> supplier;
    private Optional<T> value = Optional.empty();

    private PhasedContent(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    /**
     * Creates a phased content instance.<br>
     * The specified supplier's result can return {@code null}, but the supplier itself must not be {@code null}.
     */
    public static <T> PhasedContent<T> of(@NotNull Supplier<T> supplier) {
        Objects.requireNonNull(supplier);
        return new PhasedContent<>(supplier);
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
     * @return the created value
     * @throws IllegalStateException if the value has not been created
     * @see PhasedContent#create()
     * @see PhasedContent#get()
     */
    public T require() {
        if (value.isEmpty()) {
            throw new IllegalStateException("phased content was never created");
        }
        return get();
    }
}
