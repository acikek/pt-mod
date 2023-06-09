package com.acikek.pt.core.api.registry;

import com.acikek.pt.PT;
import com.acikek.pt.core.impl.registry.ElementIdImpls;
import net.minecraft.util.Identifier;

public abstract class ElementIds<T> {

    private static final String MINI_ITEM_SUFFIX = "_mini";
    private static final String BLOCK_SUFFIX = "_block";

    protected T value;

    protected ElementIds(T value) {
        this.value = value;
    }

    public static ElementIds<String> create(String id) {
        return new ElementIdImpls.StringBacked(id);
    }

    public static ElementIds<Identifier> create(Identifier id) {
        return new ElementIdImpls.IdentifierBacked(id);
    }

    public abstract T get(String suffix);

    public abstract ElementIds<T> append(String suffix);

    public abstract ElementIds<String> useString();

    public abstract ElementIds<Identifier> useIdentifier(String key);

    public ElementIds<Identifier> useIdentifier() {
        return useIdentifier(PT.ID);
    }

    public T getItemId() {
        return value;
    }

    public T getMiniItemId() {
        return get(MINI_ITEM_SUFFIX);
    }

    public T getBlockId() {
        return get(BLOCK_SUFFIX);
    }

    public T getFluidId() {
        return value;
    }

    @Override
    public String toString() {
        return "ElementIds{" +
                "base=" + value +
                '}';
    }
}
