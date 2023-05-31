package com.acikek.pt.core.impl.source;

import com.acikek.pt.api.request.FeatureRequests;
import com.acikek.pt.core.api.content.ContentContext;
import com.acikek.pt.core.api.content.ContentIdentifier;
import com.acikek.pt.core.api.mineral.Mineral;
import com.acikek.pt.core.api.registry.PTRegistry;
import com.acikek.pt.core.api.signature.ElementSignatureEntry;
import com.acikek.pt.core.api.signature.SignatureHolder;
import com.acikek.pt.core.api.source.ElementSources;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MineralSource<D> extends UndergroundSource<D> {

    private static final List<Mineral<?>> ADDED = new ArrayList<>();

    private final Mineral<D> mineral;
    private ContentContext.Source context;

    public MineralSource(Mineral<D> mineral) {
        Objects.requireNonNull(mineral);
        this.mineral = mineral;
    }

    @Override
    public @NotNull ContentIdentifier typeId() {
        return ElementSources.MINERAL;
    }

    @Override
    public ContentContext.Source context() {
        return context;
    }

    @Override
    public void setContext(ContentContext.Source context) {
        super.setContext(context);
        this.context = context;
    }

    @Override
    public Item mineralResultItem() {
        return mineral.mineralResultItem();
    }

    @Override
    public boolean isExclusive() {
        return true;
    }

    @Override
    public boolean isAdded() {
        return ADDED.contains(mineral);
    }

    @Override
    public void onAdd(ContentContext.Source context) {
        for (ElementSignatureEntry entry : mineral.getAllResultEntries()) {
            if (entry.element() == context.parent() && entry.isPrimary()) {
                ADDED.add(mineral);
                return;
            }
        }
        throw new IllegalStateException("element '" + context.parent() + "' is not a primary component of source '" + this + "'");
    }

    @Override
    public void register(PTRegistry registry, FeatureRequests.Single features) {
        // TODO
    }

    @Override
    public void injectSignature(SignatureHolder holder) {
        mineral.injectSignature(holder);
    }

    @Override
    public List<Block> getBlocks() {
        return mineral.getBlocks();
    }

    @Override
    public List<Item> getItems() {
        return mineral.getItems();
    }

    @Override
    public String toString() {
        return "MineralSource{" + mineral + "}";
    }

    @Override
    public D getData() {
        return mineral.getData();
    }
}
