package com.acikek.pt.api.datagen;

import com.acikek.pt.api.impl.datagen.PTDatagenImpl;
import com.acikek.pt.core.api.AbstractPeriodicTable;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.*;
import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.data.client.TexturedModel;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;

import java.util.concurrent.CompletableFuture;
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

    public static FabricLanguageProvider createLanguageProvider(FabricDataOutput output, AbstractPeriodicTable table, String existingLangFile) {
        return PTDatagenImpl.createLanguageProvider(output, table, existingLangFile);
    }

    public static FabricLanguageProvider createLanguageProvider(FabricDataOutput output, AbstractPeriodicTable table) {
        return createLanguageProvider(output, table, null);
    }

    public static void buildBlockModels(BlockStateModelGenerator generator, AbstractPeriodicTable table) {
        PTDatagenImpl.delegate(DatagenDelegator::buildBlockModels, generator, table);
    }

    public static void buildItemModels(ItemModelGenerator generator, AbstractPeriodicTable table) {
        PTDatagenImpl.delegate(DatagenDelegator::buildItemModels, generator, table);
    }

    public static FabricModelProvider createModelProvider(FabricDataOutput output, AbstractPeriodicTable table) {
        return new FabricModelProvider(output) {
            @Override
            public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
                buildBlockModels(blockStateModelGenerator, table);
            }

            @Override
            public void generateItemModels(ItemModelGenerator itemModelGenerator) {
                buildItemModels(itemModelGenerator, table);
            }
        };
    }

    public static void buildLootTables(FabricBlockLootTableProvider provider, AbstractPeriodicTable table) {
        PTDatagenImpl.delegate(DatagenDelegator::buildLootTables, provider, table);
    }

    public static FabricBlockLootTableProvider createLootTableProvider(FabricDataOutput output, AbstractPeriodicTable table) {
        return new FabricBlockLootTableProvider(output) {
            @Override
            public void generate() {
                buildLootTables(this, table);
            }
        };
    }

    public static void buildRecipes(PTRecipeProvider provider, AbstractPeriodicTable table) {
        PTDatagenImpl.delegate(DatagenDelegator::buildRecipes, provider, table);
    }

    public static FabricRecipeProvider createRecipeProvider(FabricDataOutput output, AbstractPeriodicTable table) {
        return new PTRecipeProvider(output) {
            @Override
            public void generate(Consumer<RecipeJsonProvider> exporter) {
                super.generate(exporter);
                buildRecipes(this, table);
            }
        };
    }

    public static void buildBlockTags(Function<TagKey<Block>, FabricTagProvider<Block>.FabricTagBuilder> provider, AbstractPeriodicTable table) {
        PTDatagenImpl.delegate(DatagenDelegator::buildBlockTags, provider, table);
    }

    public static FabricTagProvider.BlockTagProvider createBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> lookup, AbstractPeriodicTable table) {
        return new FabricTagProvider.BlockTagProvider(output, lookup) {
            @Override
            protected void configure(RegistryWrapper.WrapperLookup arg) {
                buildBlockTags(this::getOrCreateTagBuilder, table);
            }
        };
    }

    public static void buildItemTags(Function<TagKey<Item>, FabricTagProvider<Item>.FabricTagBuilder> provider, AbstractPeriodicTable table) {
        PTDatagenImpl.delegate(DatagenDelegator::buildItemTags, provider, table);
    }

    public static FabricTagProvider.ItemTagProvider createItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> lookup, AbstractPeriodicTable table) {
        return new FabricTagProvider.ItemTagProvider(output, lookup) {
            @Override
            protected void configure(RegistryWrapper.WrapperLookup arg) {
                buildItemTags(this::getOrCreateTagBuilder, table);
            }
        };
    }

    public static void buildFluidTags(Function<TagKey<Fluid>, FabricTagProvider<Fluid>.FabricTagBuilder> provider, AbstractPeriodicTable table) {
        PTDatagenImpl.delegate(DatagenDelegator::buildFluidTags, provider, table);
    }

    public static FabricTagProvider.FluidTagProvider createFluidTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> lookup, AbstractPeriodicTable table) {
        return new FabricTagProvider.FluidTagProvider(output, lookup) {
            @Override
            protected void configure(RegistryWrapper.WrapperLookup arg) {
                buildFluidTags(this::getOrCreateTagBuilder, table);
            }
        };
    }
}
