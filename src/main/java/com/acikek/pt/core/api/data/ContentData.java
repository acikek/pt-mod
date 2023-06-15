package com.acikek.pt.core.api.data;

import com.acikek.pt.core.impl.data.ContentDataBuilderImpl;
import net.minecraft.util.Identifier;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ContentData {

    // could use contentId but i feel like that's for a different purpose.
    // at least between different namespaces there's no collision
    // however in the same mod you could potentially combine two of the same key for a single content + extensions?
    // i'll make that error
    private final Map<Identifier, Object> data;

    public ContentData(Map<Identifier, Object> data) {
        this.data = data;
    }

    public static ContentDataBuilder builder() {
        return new ContentDataBuilderImpl(null);
    }

    public <T> T get(DataKey<T> key) {
        var obj = data.get(key.id());
        return key.from(obj);
    }

    public <T> boolean contains(DataKey<T> key) {
        return data.containsKey(key.id());
    }

    public <T> Optional<T> getOptional(DataKey<T> key) {
        return Optional.ofNullable(get(key));
    }

    private Map<Identifier, Object> copyData() {
        return new HashMap<>(data);
    }

    public ContentData copy() {
        return new ContentData(copyData());
    }

    public ContentDataBuilder edit() {
        return new ContentDataBuilderImpl(copyData());
    }

    public ContentData combine(Collection<ContentData> others) {
        var copied = copy();
        for (var other : others) {
            for (var entry : other.data.entrySet()) {
                copied.data.merge(entry.getKey(), entry.getValue(), (o1, o2) -> {
                    throw new IllegalStateException("duplicate data key '" + entry.getKey() + "'");
                });
            }
        }
        return copied;
    }
}
