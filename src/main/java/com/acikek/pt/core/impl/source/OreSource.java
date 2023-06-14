package com.acikek.pt.core.impl.source;

import com.acikek.pt.api.datagen.PTDatagenApi;
import com.acikek.pt.api.datagen.provider.PTRecipeProvider;
import com.acikek.pt.api.datagen.provider.tag.PTTagProviders;
import com.acikek.pt.api.request.FeatureRequests;
import com.acikek.pt.api.request.RequestTypes;
import com.acikek.pt.core.api.content.element.ContentIdentifier;
import com.acikek.pt.core.api.content.phase.PhasedContent;
import com.acikek.pt.core.api.refined.RefinedStateData;
import com.acikek.pt.core.api.registry.PTRegistry;
import com.acikek.pt.core.api.source.ElementSources;
import com.acikek.pt.core.api.source.SourceData;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.mininglevel.v1.MiningLevelManager;
import net.fabricmc.fabric.api.resource.conditions.v1.DefaultResourceConditions;
import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.data.server.recipe.CookingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class OreSource extends UndergroundSource<SourceData.Ore> {

    private final PhasedContent<Block> ore;
    private final PhasedContent<Block> deepslateOre;
    private final PhasedContent<Item> rawItem;
    private final PhasedContent<Block> rawBlock;
    private final int miningLevel;

    public OreSource(Identifier id, PhasedContent<Block> ore, PhasedContent<Block> deepslateOre, PhasedContent<Item> rawItem, PhasedContent<Block> rawBlock, int miningLevel) {
        super(id);
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
    public Item mineralResultItem() {
        return rawItem.get();
    }

    @Override
    public void register(PTRegistry registry, FeatureRequests.Single features) {
        if (!features.contains(RequestTypes.CONTENT)) {
            return;
        }
        ore.create(ore -> registry.registerBlock(contentIds().getBlockId(), ore));
        deepslateOre.create(ore -> registry.registerBlock(contentIds().get("_deepslate"), ore));
        rawItem.create(raw -> registry.registerItem(contentIds().get("_raw"), raw));
        rawBlock.create(raw -> registry.registerBlock(contentIds().get("_raw_block"), raw));
    }

    @Override
    public void buildTranslations(FabricLanguageProvider.TranslationBuilder builder) {
        String name = parent().display().englishName();
        ore.require(ore -> builder.add(ore, name + " Ore"));
        deepslateOre.require(ore -> builder.add(ore, "Deepslate " + name + " Ore"));
        rawItem.require(raw -> builder.add(raw, "Raw " + name));
        rawBlock.require(raw -> builder.add(raw, "Block of Raw " + name));
        if (isMain()) {
            builder.add(parent().getConventionalBlockKey("%s_ores"), name + " Ores");
            builder.add(parent().getConventionalBlockKey("raw_%s_blocks"), "Raw " + name + " Blocks");
            builder.add(parent().getConventionalItemKey("raw_%s_ores"), "Raw " + name + " Ores");
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
                rawItem.require(item ->
                        PTDatagenApi.buildReversibleRecipes(
                                provider, contentIds().useIdentifier(),
                                item, "raw_item", block, "raw_block"
                        )
                )
        );

        if (context().state().getData() instanceof RefinedStateData.Base base && base.item() != null) {
            ore.require(block -> offerSmelting(provider, block, base.item(), "_ore"));
            deepslateOre.require(block -> offerSmelting(provider, block, base.item(), "_deepslate_ore"));
            rawItem.require(item -> offerSmelting(provider, item, base.item(), "_raw_ore"));
        }
    }

    @Override
    public void buildBlockTags(PTTagProviders.BlockTagProvider provider) {
        var miningLevel = MiningLevelManager.getBlockTag(this.miningLevel);
        for (var ore : List.of(ore, deepslateOre)) {
            ore.ifCreated((block, content) -> {
                provider.add(parent().getConventionalBlockTag("%s_ores"), block);
                if (content.isInternal()) {
                    provider.add(miningLevel, block);
                    provider.add(BlockTags.PICKAXE_MINEABLE, block);
                }
            });
        }
        rawBlock.ifCreated((raw, content) -> {
            provider.add(parent().getConventionalBlockTag("raw_%s_blocks"), raw);
            if (content.isInternal()) {
                provider.add(miningLevel, raw);
                provider.add(BlockTags.PICKAXE_MINEABLE, raw);
                provider.add(BlockTags.NEEDS_STONE_TOOL, raw);
            }
        });
    }

    @Override
    public void buildItemTags(PTTagProviders.ItemTagProvider provider) {
        rawItem.ifCreated(raw -> provider.add(parent().getConventionalItemTag("raw_%s_ores"), raw));
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
