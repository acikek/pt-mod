package com.acikek.pt.core.impl.source;

import com.acikek.pt.core.element.Element;
import com.acikek.pt.core.mineral.Mineral;
import com.acikek.pt.core.registry.ElementIds;
import com.acikek.pt.core.registry.PTRegistry;
import com.acikek.pt.core.signature.ElementSignatureEntry;
import com.acikek.pt.core.source.ElementSource;
import com.acikek.pt.core.source.ElementSources;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MineralSource implements ElementSource {

    private static final Map<Mineral, Element> ATTACHMENTS = new HashMap<>();

    private final Mineral mineral;

    public MineralSource(Mineral mineral) {
        Objects.requireNonNull(mineral);
        this.mineral = mineral;
    }

    @Override
    public @NotNull Identifier getId() {
        return ElementSources.MINERAL;
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
    public boolean isAlreadyAdded(Element element) {
        return ATTACHMENTS.get(mineral) == element;
    }

    @Override
    public void onAdd(Element parent) {
        ElementSource.super.onAdd(parent);
        for (ElementSignatureEntry entry : mineral.getAllResultEntries()) {
            if (entry.element() == parent && entry.isPrimary()) {
                ATTACHMENTS.put(mineral, parent);
                return;
            }
        }
        throw new IllegalStateException("element '" + parent + "' is not a primary component of source ");
    }

    @Override
    public void register(PTRegistry registry, ElementIds<String> ids) {
        // TODO worldgen goes here
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
        return "MineralSource(mineral = " + mineral + ")";
    }
}
