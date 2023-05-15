package com.acikek.pt.core.impl.source;

import com.acikek.pt.api.request.FeatureRequests;
import com.acikek.pt.api.request.RequestTypes;
import com.acikek.pt.core.api.content.ContentContext;
import com.acikek.pt.core.api.content.PhasedContent;
import com.acikek.pt.core.api.element.Element;
import com.acikek.pt.core.api.refined.ElementRefinedState;
import com.acikek.pt.core.api.registry.ElementIds;
import com.acikek.pt.core.api.registry.PTRegistry;
import com.acikek.pt.core.api.source.ElementSources;
import com.acikek.pt.core.impl.refined.BaseRefinedState;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.mininglevel.v1.MiningLevelManager;
import net.fabricmc.fabric.api.resource.conditions.v1.DefaultResourceConditions;
import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class OreSource extends UndergroundSource {

    private final PhasedContent<Block> ore;
    private final PhasedContent<Block> deepslateOre;
    private final PhasedContent<Item> rawItem;
    private final PhasedContent<Block> rawBlock;
    private final int miningLevel;

    private boolean created = false;
    private ElementRefinedState state;

    public OreSource(PhasedContent<Block> ore, PhasedContent<Block> deepslateOre, PhasedContent<Item> rawItem, PhasedContent<Block> rawBlock, int miningLevel) {
        this.ore = ore;
        this.deepslateOre = deepslateOre;
        this.rawItem = rawItem;
        this.rawBlock = rawBlock;
        this.miningLevel = miningLevel;
    }

    @Override
    public @NotNull Identifier getTypeId() {
        return ElementSources.ORE;
    }

    @Override
    public Item mineralResultItem() {
        return rawItem.get();
    }

    @Override
    public void register(PTRegistry registry, ElementIds<String> ids, ContentContext.Source context, FeatureRequests.Single features) {
        if (!features.contains(RequestTypes.CONTENT)) {
            return;
        }
        ore.create(ore -> registry.registerBlock(ids.getSourceBlockId(), ore));
        deepslateOre.create(ore -> registry.registerBlock(ids.getDeepslateSourceBlockId(), ore));
        if (rawItem != null) {
            rawItem.create(raw -> registry.registerItem(ids.getRawSourceItemId(), raw));
        }
        if (rawBlock != null) {
            rawBlock.create(raw -> registry.registerBlock(ids.getRawSourceBlockId(), raw));
        }
        created = true;
        state = context.state();
    }

    @Override
    public void buildTranslations(FabricLanguageProvider.TranslationBuilder builder, Element parent) {
        String name = parent.display().englishName();
        ore.require(ore -> builder.add(ore, name + "Ore"));
        deepslateOre.require(ore -> builder.add(ore, "Deepslate " + name + " Ore"));
        if (rawItem != null) {
            rawItem.require(raw -> builder.add(raw, "Raw " + name));
        }
        if (rawBlock != null) {
            rawBlock.require(raw -> builder.add(raw, "Block of Raw " + name));
        }
    }

    @Override
    public void buildLootTables(FabricBlockLootTableProvider provider, Element parent) {
        var generator = provider.withConditions(DefaultResourceConditions.itemsRegistered(ore.require()));
        for (var content : List.of(ore, deepslateOre)) {
            content.require(c -> generator.addDrop(c, b -> generator.oreDrops(b, rawItem.require())));
        }
        rawBlock.require(generator::addDrop);
    }

    @Override
    public void buildBlockTags(Function<TagKey<Block>, FabricTagProvider<Block>.FabricTagBuilder> provider, Element parent) {
        var miningLevel = provider.apply(MiningLevelManager.getBlockTag(this.miningLevel));
        var mineable = provider.apply(BlockTags.PICKAXE_MINEABLE);
        for (var content : List.of(ore, deepslateOre)) {
            content.require(c -> {
                var id =  Registries.BLOCK.getId(c);
                provider.apply(parent.getConventionalBlockTag("%s_ores")).addOptional(id);
                miningLevel.addOptional(id);
                mineable.addOptional(id);
            });
        }
        rawBlock.require(raw -> {
            var id = Registries.BLOCK.getId(raw);
            provider.apply(parent.getConventionalBlockTag("raw_%s_blocks")).addOptional(id);
            miningLevel.addOptional(id);
            mineable.addOptional(id);
            provider.apply(BlockTags.NEEDS_STONE_TOOL).addOptional(id);
        });
    }

    @Override
    public void buildItemTags(Function<TagKey<Item>, FabricTagProvider<Item>.FabricTagBuilder> provider, Element parent) {
        rawItem.require(raw -> provider.apply(parent.getConventionalItemTag("raw_%s_ores"))
                .addOptional(Registries.ITEM.getId(raw)));
    }

    @Override
    public void buildBlockModels(BlockStateModelGenerator generator, Element parent) {
        for (var content : List.of(ore, deepslateOre, rawBlock)) {
            content.require(generator::registerSimpleCubeAll);
        }
    }

    @Override
    public void buildItemModels(ItemModelGenerator generator, Element parent) {
        rawItem.require(item -> generator.register(item, Models.GENERATED));
    }

    @Override
    public List<Block> getBlocks() {
        return created
                ? List.of(ore.require(), deepslateOre.require(), rawBlock.require())
                : Collections.emptyList();
    }

    @Override
    public List<Item> getItems() {
        return created
                ? Collections.singletonList(rawItem.require())
                : Collections.emptyList();
    }
}
