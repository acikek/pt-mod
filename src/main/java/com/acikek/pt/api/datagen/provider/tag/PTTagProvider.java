package com.acikek.pt.api.datagen.provider.tag;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.TagKey;

import java.util.stream.Stream;

/**
 * Implemented as an extension of {@link FabricTagProvider} with capabilities specific to PT.
 */
public interface PTTagProvider<T> {

    /**
     * Adds the specified objects as pure optionals to the provided tag.<br>
     * Equivalent to calling {@link FabricTagProvider.FabricTagBuilder#addOptional(RegistryKey)} for each object.
     */
    void add(TagKey<T> tag, Stream<T> objects);

    /**
     * @see PTTagProvider#add(TagKey, Stream)
     */
    default void add(TagKey<T> tag, T... objects) {
        add(tag, Stream.of(objects));
    }
}
