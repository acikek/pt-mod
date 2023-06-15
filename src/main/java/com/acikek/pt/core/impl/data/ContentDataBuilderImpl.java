package com.acikek.pt.core.impl.data;

import com.acikek.pt.core.api.data.ContentData;
import com.acikek.pt.core.api.data.ContentDataBuilder;
import com.acikek.pt.core.api.data.DataKey;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class ContentDataBuilderImpl implements ContentDataBuilder {

    private final Map<Identifier, Object> map;

    public ContentDataBuilderImpl(Map<Identifier, Object> map) {
        this.map = map == null
                ? new HashMap<>()
                : map;
    }

    @Override
    public <T> ContentDataBuilder add(DataKey<T> key, T value) {
        map.put(key.id(), value);
        return this;
    }

    @Override
    public ContentData build() {
        return new ContentData(map);
    }
}
