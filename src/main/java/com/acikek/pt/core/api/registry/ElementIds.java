package com.acikek.pt.core.api.registry;

import com.acikek.pt.core.impl.registry.ElementIdImpls;
import net.minecraft.util.Identifier;

public abstract class ElementIds<T> {

    private static final String MINI_ITEM_SUFFIX = "_mini";
    private static final String BLOCK_SUFFIX = "_block";
    private static final String SOURCE_BLOCK_SUFFIX = "_source_block";
    private static final String DEEPSLATE_SOURCE_BLOCK_SUFFIX = "_source_deepslate";
    private static final String RAW_SOURCE_ITEM_SUFFIX = "_source";
    private static final String RAW_SOURCE_BLOCK_SUFFIX = "_raw_block";

    private static final String MINERAL_CLUSTER_SUFFIX = "_cluster";

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

    public abstract ElementIds<String> useString();

    public abstract ElementIds<Identifier> useIdentifier();

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

    public T getSourceBlockId() {
        return get(SOURCE_BLOCK_SUFFIX);
    }

    public T getDeepslateSourceBlockId() {
        return get(DEEPSLATE_SOURCE_BLOCK_SUFFIX);
    }

    public T getRawSourceItemId() {
        return get(RAW_SOURCE_ITEM_SUFFIX);
    }

    public T getRawSourceBlockId() {
        return get(RAW_SOURCE_BLOCK_SUFFIX);
    }

    public T getMineralClusterId() {
        return get(MINERAL_CLUSTER_SUFFIX);
    }
}
