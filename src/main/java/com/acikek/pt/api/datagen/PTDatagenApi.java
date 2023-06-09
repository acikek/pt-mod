package com.acikek.pt.api.datagen;

import com.acikek.pt.api.datagen.provider.PTRecipeProvider;
import com.acikek.pt.api.datagen.provider.tag.PTTagProviders;
import com.acikek.pt.api.impl.datagen.PTDatagenImpl;
import com.acikek.pt.core.api.AbstractPeriodicTable;
import com.acikek.pt.core.api.registry.ElementIds;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.*;
import net.fabricmc.fabric.api.resource.conditions.v1.DefaultResourceConditions;
import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.data.client.TexturedModel;
import net.minecraft.data.server.recipe.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

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

    public static void buildReversibleRecipes(PTRecipeProvider provider, ItemConvertible base, Identifier baseId, RecipeCategory baseCategory, ItemConvertible compact, Identifier compactId, RecipeCategory compactCategory) {
        PTDatagenImpl.buildReversibleRecipes(provider, base, baseId, baseCategory, compact, compactId, compactCategory);
    }

    public static void buildReversibleRecipes(PTRecipeProvider provider, ElementIds<Identifier> ids, ItemConvertible base, String baseName, RecipeCategory baseCategory, ItemConvertible compact, String compactName, RecipeCategory compactCategory) {
        PTDatagenImpl.buildReversibleRecipes(provider, ids, base, baseName, baseCategory, compact, compactName, compactCategory);
    }

    public static void buildReversibleRecipes(PTRecipeProvider provider, Item item, Identifier itemId, Block block, Identifier blockId) {
        buildReversibleRecipes(provider, item, itemId, RecipeCategory.MISC, block, blockId, RecipeCategory.BUILDING_BLOCKS);
    }

    public static void buildReversibleRecipes(PTRecipeProvider provider, ElementIds<Identifier> ids, Item item, String itemName, Block block, String blockName) {
        buildReversibleRecipes(provider, ids, item, itemName, RecipeCategory.MISC, block, blockName, RecipeCategory.BUILDING_BLOCKS);
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

    public static void buildBlockTags(PTTagProviders.BlockTagProvider provider, AbstractPeriodicTable table) {
        PTDatagenImpl.delegate(DatagenDelegator::buildBlockTags, provider, table);
    }

    public static FabricTagProvider.BlockTagProvider createBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> lookup, AbstractPeriodicTable table) {
        return new PTTagProviders.BlockTagProvider(output, lookup) {
            @Override
            protected void configure(RegistryWrapper.WrapperLookup arg) {
                buildBlockTags(this, table);
            }
        };
    }

    public static void buildItemTags(PTTagProviders.ItemTagProvider provider, AbstractPeriodicTable table) {
        PTDatagenImpl.delegate(DatagenDelegator::buildItemTags, provider, table);
    }

    public static FabricTagProvider.ItemTagProvider createItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> lookup, AbstractPeriodicTable table) {
        return new PTTagProviders.ItemTagProvider(output, lookup) {
            @Override
            protected void configure(RegistryWrapper.WrapperLookup arg) {
                buildItemTags(this, table);
            }
        };
    }

    public static void buildFluidTags(PTTagProviders.FluidTagProvider provider, AbstractPeriodicTable table) {
        PTDatagenImpl.delegate(DatagenDelegator::buildFluidTags, provider, table);
    }

    public static FabricTagProvider.FluidTagProvider createFluidTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> lookup, AbstractPeriodicTable table) {
        return new PTTagProviders.FluidTagProvider(output, lookup) {
            @Override
            protected void configure(RegistryWrapper.WrapperLookup arg) {
                buildFluidTags(this, table);
            }
        };
    }
}
