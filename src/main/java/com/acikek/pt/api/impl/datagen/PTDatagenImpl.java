package com.acikek.pt.api.impl.datagen;

import com.acikek.pt.api.datagen.DatagenDelegator;
import com.acikek.pt.api.datagen.PTDatagenApi;
import com.acikek.pt.api.datagen.provider.PTRecipeProvider;
import com.acikek.pt.block.ModBlocks;
import com.acikek.pt.core.api.AbstractPeriodicTable;
import com.acikek.pt.core.api.PeriodicTable;
import com.acikek.pt.core.api.element.Element;
import com.acikek.pt.core.api.registry.ElementIds;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.DefaultResourceConditions;
import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.TextureKey;
import net.minecraft.data.client.TextureMap;
import net.minecraft.data.server.recipe.*;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.util.TriConsumer;

import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class PTDatagenImpl {

    public static <T> void delegate(BiConsumer<DatagenDelegator, T> fn, T context, AbstractPeriodicTable table) {
        table.forEachMineral(mineral -> fn.accept(mineral, context));
        table.forEachElement(element -> fn.accept(element, context));
    }

    public static FabricLanguageProvider createLanguageProvider(FabricDataOutput output, AbstractPeriodicTable table, String existingLangFile) {
        return new FabricLanguageProvider(output) {
            @Override
            public void generateTranslations(TranslationBuilder translationBuilder) {
                PTDatagenApi.buildEnglishTranslations(translationBuilder, PeriodicTable.INSTANCE);
                if (existingLangFile == null) {
                    return;
                }
                try {
                    var existing = output.getModContainer().findPath(existingLangFile);
                    if (existing.isPresent()) {
                        translationBuilder.add(existing.get());
                    }
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    public static Identifier getReversibleId(ElementIds<Identifier> ids, String first, String second) {
        return ids.get("_" + first + "_to_" + second);
    }

    public static void buildReversibleRecipes(PTRecipeProvider provider, ElementIds<Identifier> ids, ItemConvertible base, String baseName, RecipeCategory baseCategory, ItemConvertible compact, String compactName, RecipeCategory compactCategory) {
        buildReversibleRecipes(provider, base, getReversibleId(ids, compactName, baseName), baseCategory, compact, getReversibleId(ids, baseName, compactName), compactCategory);
    }

    public static void buildReversibleRecipes(PTRecipeProvider provider, ItemConvertible base, Identifier baseId, RecipeCategory baseCategory, ItemConvertible compact, Identifier compactId, RecipeCategory compactCategory) {
        var exporter = provider.withConditions(DefaultResourceConditions.itemsRegistered(base, compact));
        ShapelessRecipeJsonBuilder.create(baseCategory, base, 9)
                .criterion(RecipeProvider.hasItem(compact), RecipeProvider.conditionsFromItem(compact))
                .input(compact)
                .offerTo(exporter, baseId);
        ShapedRecipeJsonBuilder.create(compactCategory, compact)
                .criterion(RecipeProvider.hasItem(base), RecipeProvider.conditionsFromItem(base))
                .pattern("###").pattern("###").pattern("###")
                .input('#', compact)
                .offerTo(exporter, compactId);
    }

    public static TextureMap getPowderTextureMap(Block powderBlock) {
        Identifier top = TextureMap.getSubId(powderBlock, "_top");
        return new TextureMap()
                .put(TextureKey.SIDE, TextureMap.getSubId(powderBlock, "_side"))
                .put(TextureKey.FRONT, TextureMap.getSubId(powderBlock, "_front"))
                .put(TextureKey.TOP, top)
                .put(TextureKey.PARTICLE, top)
                .put(TextureKey.BOTTOM, TextureMap.getSubId(ModBlocks.EMPTY_SACK, "_bottom"));
    }
}
