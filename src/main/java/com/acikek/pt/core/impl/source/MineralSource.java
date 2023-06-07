package com.acikek.pt.core.impl.source;

import com.acikek.pt.api.request.FeatureRequests;
import com.acikek.pt.core.api.content.element.ContentContext;
import com.acikek.pt.core.api.content.element.ContentIdentifier;
import com.acikek.pt.core.api.mineral.Mineral;
import com.acikek.pt.core.api.registry.PTRegistry;
import com.acikek.pt.core.api.signature.SignatureHolder;
import com.acikek.pt.core.api.signature.Signatures;
import com.acikek.pt.core.api.source.ElementSources;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MineralSource<D> extends UndergroundSource<D> {

    private static final List<Mineral<?>> ADDED = new ArrayList<>();

    private final Mineral<D> mineral;
    private ContentContext.Source context;

    public MineralSource(Identifier id, Mineral<D> mineral) {
        super(id);
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
        if (Signatures.isElementIn(mineral.signature().all(), context.parent(), true)) {
            ADDED.add(mineral);
        }
        else {
            throw new IllegalStateException("element '" + context.parent() + "' is not a primary component of source '" + this + "'");
        }
    }

    @Override
    public void register(PTRegistry registry, FeatureRequests.Single features) {
        // TODO
    }

    @Override
    public void addSignatures(SignatureHolder holder) {
        mineral.addSignatures(mineral);
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
