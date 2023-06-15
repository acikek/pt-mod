package com.acikek.pt.core.api.data;

import com.acikek.pt.PT;
import net.minecraft.util.Identifier;

public record DataKey<T>(Identifier id, Class<T> type) {

    public static <T> DataKey<T> of(Identifier id, Class<T> type) {
        return new DataKey<>(id, type);
    }

    public static <T> DataKey<T> of(String id, Class<T> type) {
        return of(PT.id(id), type);
    }

    public T from(Object obj) {
        return type.cast(obj);
    }
}
