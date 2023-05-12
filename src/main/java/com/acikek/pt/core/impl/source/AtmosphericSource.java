package com.acikek.pt.core.impl.source;

import com.acikek.pt.api.request.FeatureRequests;
import com.acikek.pt.core.api.content.ContentContext;
import com.acikek.pt.core.api.element.Element;
import com.acikek.pt.core.api.registry.ElementIds;
import com.acikek.pt.core.api.registry.PTRegistry;
import com.acikek.pt.core.api.source.ElementSource;
import com.acikek.pt.core.api.source.ElementSources;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.block.Block;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.Range;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class AtmosphericSource implements ElementSource {

    private final Range<Integer> range;

    public AtmosphericSource(Range<Integer> range) {
        this.range = range;
    }

    @Override
    public @NotNull Identifier getTypeId() {
        return ElementSources.ATMOSPHERIC;
    }

    @Override
    public void register(PTRegistry registry, ElementIds<String> ids, ContentContext.Source context, FeatureRequests.Single features) {
        // Empty
    }

    @Override
    public Item mineralResultItem() {
        return null;
    }

    @Override
    public void buildTranslations(FabricLanguageProvider.TranslationBuilder builder, Element parent) {
        // Empty
    }

    @Override
    public void buildLootTables(FabricBlockLootTableProvider provider, Element parent) {
        // Empty
    }

    @Override
    public List<Block> getBlocks() {
        return Collections.emptyList();
    }

    @Override
    public List<Item> getItems() {
        return Collections.emptyList();
    }
}
