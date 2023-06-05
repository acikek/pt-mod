package com.acikek.pt.api.datagen;

import com.acikek.pt.api.datagen.provider.PTRecipeProvider;
import com.acikek.pt.api.datagen.provider.tag.PTTagProviders;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;

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

    default void buildBlockTags(PTTagProviders.BlockTagProvider provider) {
        // Empty
    }

    default void buildItemTags(PTTagProviders.ItemTagProvider provider) {
        // Empty
    }

    default void buildFluidTags(PTTagProviders.FluidTagProvider provider) {
        // Empty
    }
}
