package com.acikek.pt.api.datagen;

import com.acikek.pt.api.impl.datagen.PTDatagenImpl;
import com.acikek.pt.core.api.AbstractPeriodicTable;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.data.client.TexturedModel;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;

import java.util.function.Consumer;
import java.util.function.Function;

public class PTDatagenApi {

    public static final TexturedModel.Factory POWDER_MODEL_FACTORY = TexturedModel.makeFactory(
            PTDatagenImpl::getPowderTextureMap,
            Models.ORIENTABLE_WITH_BOTTOM
    );

    public static void buildEnglishTranslations(FabricLanguageProvider.TranslationBuilder builder, AbstractPeriodicTable table) {
        PTDatagenImpl.delegate(DatagenDelegator::buildTranslations, builder, table);
    }

    public static void buildBlockModels(BlockStateModelGenerator generator, AbstractPeriodicTable table) {
        PTDatagenImpl.delegate(DatagenDelegator::buildBlockModels, generator, table);
    }

    public static void buildItemModels(ItemModelGenerator generator, AbstractPeriodicTable table) {
        PTDatagenImpl.delegate(DatagenDelegator::buildItemModels, generator, table);
    }

    public static void buildLootTables(FabricBlockLootTableProvider provider, AbstractPeriodicTable table) {
        PTDatagenImpl.delegate(DatagenDelegator::buildLootTables, provider, table);
    }

    public static void buildRecipes(Consumer<RecipeJsonProvider> exporter, AbstractPeriodicTable table) {
        PTDatagenImpl.delegate(DatagenDelegator::buildRecipes, exporter, table);
    }

    public static void buildBlockTags(Function<TagKey<Block>, FabricTagProvider<Block>.FabricTagBuilder> provider, AbstractPeriodicTable table) {
        PTDatagenImpl.delegate(DatagenDelegator::buildBlockTags, provider, table);
    }

    public static void buildItemTags(Function<TagKey<Item>, FabricTagProvider<Item>.FabricTagBuilder> provider, AbstractPeriodicTable table) {
        PTDatagenImpl.delegate(DatagenDelegator::buildItemTags, provider, table);
    }

    public static void buildFluidTags(Function<TagKey<Fluid>, FabricTagProvider<Fluid>.FabricTagBuilder> provider, AbstractPeriodicTable table) {
        PTDatagenImpl.delegate(DatagenDelegator::buildFluidTags, provider, table);
    }
}
