package com.acikek.pt.core.api.data;

/**
 * @param <D> the public data type exposed from implementation details
 */
public interface DataHolder {

    /**
     * @return public data specific to the object implementation type.
     * The return type is guaranteed to be accessible in the public API.
     */
    ContentData getData();
}
