package com.acikek.pt.core.id;

import com.acikek.pt.core.impl.id.ElementIdImpls;
import net.minecraft.util.Identifier;

public abstract class ElementIds<T> {

    private static final String MINI_ITEM_SUFFIX = "_mini";
    private static final String BLOCK_SUFFIX = "_block";
    private static final String SOURCE_BLOCK_SUFFIX = "_source_block";
    private static final String DEEPSLATE_SOURCE_BLOCK_SUFFIX = "_source_deepslate";
    private static final String CLUSTER_SOURCE_BLOCK_SUFFIX = "_source_cluster";
    private static final String RAW_SOURCE_ITEM_SUFFIX = "_source";
    private static final String RAW_SOURCE_BLOCK_SUFFIX = "_raw_block";

    protected T value;

    protected ElementIds(T value) {
        this.value = value;
    }

    public static ElementIds<String> get(String id) {
        return new ElementIdImpls.StringBacked(id);
    }

    public static ElementIds<Identifier> get(Identifier id) {
        return new ElementIdImpls.IdentifierBacked(id);
    }

    protected abstract T addSuffix(String suffix);

    public abstract ElementIds<String> useString();

    public abstract ElementIds<Identifier> useIdentifier();

    public T getItemId() {
        return value;
    }

    public T getMiniItemId() {
        return addSuffix(MINI_ITEM_SUFFIX);
    }

    public T getBlockId() {
        return addSuffix(BLOCK_SUFFIX);
    }

    public T getFluidId() {
        return value;
    }

    public T getSourceBlockId() {
        return addSuffix(SOURCE_BLOCK_SUFFIX);
    }

    public T getDeepslateSourceBlockId() {
        return addSuffix(DEEPSLATE_SOURCE_BLOCK_SUFFIX);
    }

    public T getClusterSourceBlockId() {
        return addSuffix(CLUSTER_SOURCE_BLOCK_SUFFIX);
    }

    public T getRawSourceItemId() {
        return addSuffix(RAW_SOURCE_ITEM_SUFFIX);
    }

    public T getRawSourceBlockId() {
        return addSuffix(RAW_SOURCE_BLOCK_SUFFIX);
    }
}
