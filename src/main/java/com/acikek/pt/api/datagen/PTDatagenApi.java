package com.acikek.pt.api.datagen;

import com.acikek.pt.api.impl.datagen.PTDatagenImpl;
import com.acikek.pt.core.element.Element;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.data.client.TexturedModel;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
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

    public static void buildBlocksModelsForElement(BlockStateModelGenerator generator, Element element) {
        PTDatagenImpl.buildBlocksModelsForElement(generator, element);
    }

    public static void buildItemModelsForElement(ItemModelGenerator generator, Element element) {
        PTDatagenImpl.buildItemModelsForElement(generator, element);
    }

    public static void buildLootTablesForElement(BlockLootTableGenerator generator, Element element) {
        PTDatagenImpl.buildLootTablesForElement(generator, element);
    }

    public static void buildBlockTagsForElement(Function<TagKey<Block>, FabricTagProvider<Block>.FabricTagBuilder> provider, Element element) {

    }
}
