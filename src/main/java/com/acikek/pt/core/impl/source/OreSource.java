package com.acikek.pt.core.impl.source;

import com.acikek.pt.api.datagen.PTRecipeProvider;
import com.acikek.pt.api.request.FeatureRequests;
import com.acikek.pt.api.request.RequestTypes;
import com.acikek.pt.core.api.content.ContentContext;
import com.acikek.pt.core.api.content.ContentIdentifier;
import com.acikek.pt.core.api.content.PhasedContent;
import com.acikek.pt.core.api.refined.RefinedStateData;
import com.acikek.pt.core.api.registry.PTRegistry;
import com.acikek.pt.core.api.source.ElementSources;
import com.acikek.pt.core.api.source.SourceData;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.mininglevel.v1.MiningLevelManager;
import net.fabricmc.fabric.api.resource.conditions.v1.DefaultResourceConditions;
import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.data.server.recipe.CookingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class OreSource extends UndergroundSource<SourceData.Ore> {

    private final PhasedContent<Block> ore;
    private final PhasedContent<Block> deepslateOre;
    private final PhasedContent<Item> rawItem;
    private final PhasedContent<Block> rawBlock;
    private final int miningLevel;

    private ContentContext.Source context;

    public OreSource(PhasedContent<Block> ore, PhasedContent<Block> deepslateOre, PhasedContent<Item> rawItem, PhasedContent<Block> rawBlock, int miningLevel) {
        this.ore = ore;
        this.deepslateOre = deepslateOre;
        this.rawItem = rawItem;
        this.rawBlock = rawBlock;
        this.miningLevel = miningLevel;
    }

    @Override
    public @NotNull ContentIdentifier typeId() {
        return ElementSources.ORE;
    }

    @Override
    public ContentContext.Source context() {
        return context;
    }

    @Override
    public void setContext(ContentContext.Source context) {
        super.setContext(context);
        this.context = context;
    }

    @Override
    public Item mineralResultItem() {
        return rawItem.get();
    }

    @Override
    public void register(PTRegistry registry, FeatureRequests.Single features) {
        if (!features.contains(RequestTypes.CONTENT)) {
            return;
        }
        ore.create(ore -> registry.registerBlock(contentIds().getSourceBlockId(), ore));
        deepslateOre.create(ore -> registry.registerBlock(contentIds().getDeepslateSourceBlockId(), ore));
        rawItem.create(raw -> registry.registerItem(contentIds().getRawSourceItemId(), raw));
        rawBlock.create(raw -> registry.registerBlock(contentIds().getRawSourceBlockId(), raw));
    }

    @Override
    public void buildTranslations(FabricLanguageProvider.TranslationBuilder builder) {
        String name = parent().display().englishName();
        ore.require(ore -> builder.add(ore, name + " Ore"));
        deepslateOre.require(ore -> builder.add(ore, "Deepslate " + name + " Ore"));
        rawItem.require(raw -> builder.add(raw, "Raw " + name));
        rawBlock.require(raw -> builder.add(raw, "Block of Raw " + name));
        if (!hasBuiltPass()) {
            builder.add(parent().getConventionalTagId("%s_ores"), name + " Ores");
            builder.add(parent().getConventionalTagId("raw_%s_blocks"), "Raw " + name + " Blocks");
            builder.add(parent().getConventionalTagId("raw_%s_ores"), "Raw " + name + " Ores");
        }
    }

    @Override
    public void buildLootTables(FabricBlockLootTableProvider provider) {
        for (var oreBlock : List.of(ore, deepslateOre)) {
            var drop = rawItem.isCreated()
                    ? rawItem.require()
                    : context().state().getData() instanceof RefinedStateData.Base base
                            ? base.item()
                            : null;
            if (drop != null) {
                oreBlock.require(block -> {
                    var generator = provider.withConditions(DefaultResourceConditions.itemsRegistered(block, drop));
                    generator.addDrop(block, b -> generator.oreDrops(b, drop));
                });
            }
        }
        rawBlock.require(block -> provider.withConditions(DefaultResourceConditions.itemsRegistered(block)).addDrop(block));
    }

    private void offerSmelting(PTRecipeProvider provider, ItemConvertible ore, Item result, String prefix) {
        var exporter = provider.withConditions(DefaultResourceConditions.itemsRegistered(ore, result));
        for (var serializer : List.of(RecipeSerializer.SMELTING, RecipeSerializer.BLASTING)) {
            // Accounts for floating point precision errors
            float exp = miningLevel == 3 ? 0.9f : miningLevel * 0.3f;
            String type = serializer == RecipeSerializer.SMELTING ? "_smelting" : "_blasting";
            CookingRecipeJsonBuilder.create(Ingredient.ofItems(ore), RecipeCategory.MISC, result, exp, 200, serializer)
                    .criterion(RecipeProvider.hasItem(ore), RecipeProvider.conditionsFromItem(ore))
                    .offerTo(exporter, contentIds().useIdentifier().get(type + prefix + "_to_refined"));
        }
    }

    @Override
    public void buildRecipes(PTRecipeProvider provider) {
        rawBlock.require(block ->
            rawItem.require(item -> {
                var exporter = provider.withConditions(DefaultResourceConditions.itemsRegistered(block, item));
                ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, block)
                        .criterion(RecipeProvider.hasItem(item), RecipeProvider.conditionsFromItem(item))
                        .pattern("RRR").pattern("RRR").pattern("RRR")
                        .input('R', item)
                        .offerTo(exporter, contentIds().useIdentifier().get("_raw_ore_to_block"));
                ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, item, 9)
                        .criterion(RecipeProvider.hasItem(block), RecipeProvider.conditionsFromItem(item))
                        .input(block)
                        .offerTo(exporter, contentIds().useIdentifier().get("_raw_block_to_ore"));
            })
        );

        if (context().state().getData() instanceof RefinedStateData.Base base && base.item() != null) {
            ore.require(block -> offerSmelting(provider, block, base.item(), "_ore"));
            deepslateOre.require(block -> offerSmelting(provider, block, base.item(), "_deepslate_ore"));
            rawItem.require(item -> offerSmelting(provider, item, base.item(), "_raw_ore"));
        }
    }

    @Override
    public void buildBlockTags(Function<TagKey<Block>, FabricTagProvider<Block>.FabricTagBuilder> provider) {
        var miningLevel = provider.apply(MiningLevelManager.getBlockTag(this.miningLevel));
        var mineable = provider.apply(BlockTags.PICKAXE_MINEABLE);
        for (var content : List.of(ore, deepslateOre)) {
            content.require(c -> {
                var id =  Registries.BLOCK.getId(c);
                provider.apply(parent().getConventionalBlockTag("%s_ores")).addOptional(id);
                miningLevel.addOptional(id);
                mineable.addOptional(id);
            });
        }
        rawBlock.require(raw -> {
            var id = Registries.BLOCK.getId(raw);
            provider.apply(parent().getConventionalBlockTag("raw_%s_blocks")).addOptional(id);
            miningLevel.addOptional(id);
            mineable.addOptional(id);
            provider.apply(BlockTags.NEEDS_STONE_TOOL).addOptional(id);
        });
    }

    @Override
    public void buildItemTags(Function<TagKey<Item>, FabricTagProvider<Item>.FabricTagBuilder> provider) {
        rawItem.require(raw -> provider.apply(parent().getConventionalItemTag("raw_%s_ores"))
                .addOptional(Registries.ITEM.getId(raw)));
    }

    @Override
    public void buildBlockModels(BlockStateModelGenerator generator) {
        for (var content : PhasedContent.filterByCreation(ore, deepslateOre, rawBlock)) {
            content.require(generator::registerSimpleCubeAll);
        }
    }

    @Override
    public void buildItemModels(ItemModelGenerator generator) {
        rawItem.require(item -> generator.register(item, Models.GENERATED));
    }

    @Override
    public List<Block> getBlocks() {
        return PhasedContent.getByCreation(ore, deepslateOre, rawBlock);
    }

    @Override
    public List<Item> getItems() {
        return rawItem.isCreated()
                ? Collections.singletonList(rawItem.require())
                : Collections.emptyList();
    }

    @Override
    public SourceData.Ore getData() {
        return new SourceData.Ore(ore.get(), deepslateOre.get(), rawItem.get(), rawBlock.get());
    }
}
