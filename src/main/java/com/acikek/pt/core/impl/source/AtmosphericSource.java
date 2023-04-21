package com.acikek.pt.core.impl.source;

import com.acikek.pt.core.element.Element;
import com.acikek.pt.core.registry.ElementIds;
import com.acikek.pt.core.registry.PTRegistry;
import com.acikek.pt.core.source.ElementSource;
import com.acikek.pt.core.source.ElementSources;
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
    public @NotNull Identifier getId() {
        return ElementSources.ATMOSPHERIC;
    }

    @Override
    public Item mineralResultItem() {
        return null;
    }

    @Override
    public void register(PTRegistry registry, ElementIds<String> ids) {
        // Empty
    }

    @Override
    public void buildTranslations(FabricLanguageProvider.TranslationBuilder builder, Element parent) {
        // Empty
    }

    @Override
    public void buildLootTables(BlockLootTableGenerator generator, Element parent) {
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
