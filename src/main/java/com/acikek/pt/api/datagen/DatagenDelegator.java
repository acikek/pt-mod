package com.acikek.pt.api.datagen;

import com.acikek.pt.core.api.element.Element;
import com.acikek.pt.core.api.source.MaterialHolder;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.registry.tag.TagKey;

import java.util.function.Function;

public interface DatagenDelegator extends MaterialHolder {

    default void buildTranslations(FabricLanguageProvider.TranslationBuilder builder, Element parent) {
        // Empty
    }

    default void buildBlockModels(BlockStateModelGenerator generator, Element parent) {
        for (Block block : getBlocks()) {
            generator.registerSimpleCubeAll(block);
        }
    }

    default void buildLootTables(BlockLootTableGenerator generator, Element parent) {
        // Empty
    }

    default void buildBlockTags(Function<TagKey<Block>, FabricTagProvider<Block>.FabricTagBuilder> provider, Element parent) {
        // Empty
    }

    default void buildItemTags(Function<TagKey<Block>, FabricTagProvider<Block>.FabricTagBuilder> provider, Element parent) {
        // Empty
    }
}
