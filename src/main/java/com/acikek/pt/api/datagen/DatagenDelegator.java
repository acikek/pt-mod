package com.acikek.pt.api.datagen;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;

import java.util.function.Consumer;
import java.util.function.Function;

public interface DatagenDelegator {

    default void buildTranslations(FabricLanguageProvider.TranslationBuilder builder) {
        // Empty
    }

    default void buildBlockModels(BlockStateModelGenerator generator) {
        // Empty
    }

    default void buildItemModels(ItemModelGenerator generator) {
        // Empty
    }

    default void buildLootTables(FabricBlockLootTableProvider provider) {
        // Empty
    }

    default void buildRecipes(PTRecipeProvider provider) {
        // Empty
    }

    default void buildBlockTags(Function<TagKey<Block>, FabricTagProvider<Block>.FabricTagBuilder> provider) {
        // Empty
    }

    default void buildItemTags(Function<TagKey<Item>, FabricTagProvider<Item>.FabricTagBuilder> provider) {
        // Empty
    }

    default void buildFluidTags(Function<TagKey<Fluid>, FabricTagProvider<Fluid>.FabricTagBuilder> provider) {
        // Empty
    }
}
