package com.acikek.pt.api.datagen;

import com.acikek.pt.api.impl.datagen.PTDatagenImpl;
import com.acikek.pt.core.api.AbstractPeriodicTable;
import com.acikek.pt.core.api.element.Element;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.data.client.TexturedModel;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;

import java.util.function.Function;

public class PTDatagenApi {

    public static final TexturedModel.Factory POWDER_MODEL_FACTORY = TexturedModel.makeFactory(
            PTDatagenImpl::getPowderTextureMap,
            Models.ORIENTABLE_WITH_BOTTOM
    );

    public static void buildEnglishTranslationsForElement(FabricLanguageProvider.TranslationBuilder builder, Element element) {
        PTDatagenImpl.buildTranslationsForElement(builder, element);
    }

    public static void buildEnglishTranslations(FabricLanguageProvider.TranslationBuilder builder, AbstractPeriodicTable table) {
        table.forEachElement(element -> buildEnglishTranslationsForElement(builder, element));
    }

    public static void buildBlocksModelsForElement(BlockStateModelGenerator generator, Element element) {
        PTDatagenImpl.buildBlocksModelsForElement(generator, element);
    }

    public static void buildBlockModels(BlockStateModelGenerator generator, AbstractPeriodicTable table) {
        table.forEachElement(element -> buildBlocksModelsForElement(generator, element));
    }

    public static void buildItemModelsForElement(ItemModelGenerator generator, Element element) {
        PTDatagenImpl.buildItemModelsForElement(generator, element);
    }

    public static void buildItemModels(ItemModelGenerator generator, AbstractPeriodicTable table) {
        table.forEachElement(element -> buildItemModelsForElement(generator, element));
    }

    public static void buildLootTablesForElement(FabricBlockLootTableProvider provider, Element element) {
        PTDatagenImpl.buildLootTablesForElement(provider, element);
    }

    public static void buildLootTables(FabricBlockLootTableProvider provider, AbstractPeriodicTable table) {
        table.forEachElement(element -> buildLootTablesForElement(provider, element));
    }

    public static void buildBlockTagsForElement(Function<TagKey<Block>, FabricTagProvider<Block>.FabricTagBuilder> provider, Element element) {
        PTDatagenImpl.buildBlockTagsForElement(provider, element);
    }

    public static void buildBlockTags(Function<TagKey<Block>, FabricTagProvider<Block>.FabricTagBuilder> provider, AbstractPeriodicTable table) {
        table.forEachElement(element -> buildBlockTagsForElement(provider, element));
    }

    public static void buildItemTagsForElement(Function<TagKey<Item>, FabricTagProvider<Item>.FabricTagBuilder> provider, Element element) {
        PTDatagenImpl.buildItemTagsForElement(provider, element);
    }

    public static void buildItemTags(Function<TagKey<Item>, FabricTagProvider<Item>.FabricTagBuilder> provider, AbstractPeriodicTable table) {
        table.forEachElement(element -> buildItemTagsForElement(provider, element));
    }

    public static void buildFluidTagsForElement(Function<TagKey<Fluid>, FabricTagProvider<Fluid>.FabricTagBuilder> provider, Element element) {
        PTDatagenImpl.buildFluidTagsForElement(provider, element);
    }

    public static void buildFluidTags(Function<TagKey<Fluid>, FabricTagProvider<Fluid>.FabricTagBuilder> provider, AbstractPeriodicTable table) {
        table.forEachElement(element -> buildFluidTagsForElement(provider, element));
    }
}
