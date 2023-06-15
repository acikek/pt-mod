package com.acikek.pt.core.api.data;

public interface ContentDataBuilder {

    <T> ContentDataBuilder add(DataKey<T> key, T value);

    ContentData build();
}
