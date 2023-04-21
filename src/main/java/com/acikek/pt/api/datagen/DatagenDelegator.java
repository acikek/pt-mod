package com.acikek.pt.api.datagen;

import com.acikek.pt.core.element.Element;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.registry.tag.TagKey;

import java.util.function.Function;

public interface DatagenDelegator {

    default void buildTranslations(FabricLanguageProvider.TranslationBuilder builder, Element parent) {
        // Empty
    }

    default void buildLootTables(BlockLootTableGenerator generator, Element parent) {
        // Empty
    }

    default void buildAdditionalBlockTags(Function<TagKey<Block>, FabricTagProvider<Block>.FabricTagBuilder> provider, Element parent) {
        // Empty
    }
}
