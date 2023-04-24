package com.acikek.pt.api.datagen;

import com.acikek.pt.core.api.element.Element;
import com.acikek.pt.core.api.source.MaterialHolder;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
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

    default void buildItemModels(ItemModelGenerator generator, Element parent) {
        for (Item item : getItems()) {
            generator.register(item, Models.GENERATED);
        }
    }

    default void buildLootTables(BlockLootTableGenerator generator, Element parent) {
        // Empty
    }

    default void buildBlockTags(Function<TagKey<Block>, FabricTagProvider<Block>.FabricTagBuilder> provider, Element parent) {
        // Empty
    }

    default void buildItemTags(Function<TagKey<Item>, FabricTagProvider<Item>.FabricTagBuilder> provider, Element parent) {
        // Empty
    }

    default void buildFluidTags(Function<TagKey<Fluid>, FabricTagProvider<Fluid>.FabricTagBuilder> provider, Element parent) {
        // Empty
    }
}
