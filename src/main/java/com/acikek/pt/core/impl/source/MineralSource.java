package com.acikek.pt.core.impl.source;

import com.acikek.pt.core.api.element.Element;
import com.acikek.pt.core.api.mineral.Mineral;
import com.acikek.pt.core.api.registry.ElementIds;
import com.acikek.pt.core.api.registry.PTRegistry;
import com.acikek.pt.core.api.signature.ElementSignatureEntry;
import com.acikek.pt.core.api.source.ElementSources;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class MineralSource extends UndergroundSource {

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
        for (ElementSignatureEntry entry : mineral.getAllResultEntries()) {
            if (entry.element() == parent && entry.isPrimary()) {
                ATTACHMENTS.put(mineral, parent);
                return;
            }
        }
        throw new IllegalStateException("element '" + parent + "' is not a primary component of source '" + this + "'");
    }

    @Override
    public void register(PTRegistry registry, ElementIds<String> ids) {
        // TODO worldgen goes here
    }

    @Override
    public void injectSignature(Element element) {
        mineral.injectSignature(element);
    }

    @Override
    public void buildTranslations(FabricLanguageProvider.TranslationBuilder builder, Element parent) {
        mineral.buildTranslations(builder, parent);
    }

    @Override
    public void buildLootTables(BlockLootTableGenerator generator, Element parent) {
        mineral.buildLootTables(generator, parent);
    }

    @Override
    public void buildBlockTags(Function<TagKey<Block>, FabricTagProvider<Block>.FabricTagBuilder> provider, Element parent) {
        mineral.buildBlockTags(provider, parent);
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
}
