package com.acikek.pt.core.impl.source;

import com.acikek.pt.api.request.FeatureRequests;
import com.acikek.pt.core.api.content.ContentContext;
import com.acikek.pt.core.api.element.Element;
import com.acikek.pt.core.api.registry.ElementIds;
import com.acikek.pt.core.api.registry.PTRegistry;
import com.acikek.pt.core.api.content.PhasedContent;
import com.acikek.pt.core.api.source.ElementSources;
import com.acikek.pt.api.request.RequestTypes;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.mininglevel.v1.MiningLevelManager;
import net.minecraft.block.Block;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class OreSource extends UndergroundSource {

    private final PhasedContent<Block> ore;
    private final PhasedContent<Block> deepslateOre;
    private final PhasedContent<Item> rawItem;
    private final PhasedContent<Block> rawBlock;
    private final int miningLevel;

    private boolean created = false;

    public OreSource(Supplier<Block> ore, Supplier<Block> deepslateOre, Supplier<Item> rawItem, Supplier<Block> rawBlock, int miningLevel) {
        this.ore = PhasedContent.of(ore);
        this.deepslateOre = PhasedContent.of(deepslateOre);
        this.rawItem = PhasedContent.of(rawItem);
        this.rawBlock = PhasedContent.of(rawBlock);
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
        registry.registerBlock(ids.getSourceBlockId(), ore.create());
        registry.registerBlock(ids.getDeepslateSourceBlockId(), deepslateOre.create());
        registry.registerItem(ids.getRawSourceItemId(), rawItem.create());
        registry.registerBlock(ids.getRawSourceBlockId(), rawBlock.create());
        created = true;
    }

    @Override
    public void buildTranslations(FabricLanguageProvider.TranslationBuilder builder, Element parent) {
        String name = parent.display().englishName();
        builder.add(ore.require(), name + " Ore");
        builder.add(deepslateOre.require(), "Deepslate " + name + " Ore");
        builder.add(rawItem.require(), "Raw " + name);
        builder.add(rawBlock.require(), "Block of Raw " + name);
    }

    @Override
    public void buildLootTables(BlockLootTableGenerator generator, Element parent) {
        for (Block block : List.of(ore.require(), deepslateOre.require())) {
            generator.addDrop(block, b -> generator.oreDrops(b, rawItem.require()));
        }
        generator.addDrop(rawBlock.require());
    }

    @Override
    public void buildBlockTags(Function<TagKey<Block>, FabricTagProvider<Block>.FabricTagBuilder> provider, Element parent) {
        var ore = Registries.BLOCK.getId(this.ore.require());
        var deepslate = Registries.BLOCK.getId(deepslateOre.require());
        var raw = Registries.BLOCK.getId(rawBlock.require());
        provider.apply(parent.getConventionalBlockTag("%s_ores")).addOptional(ore).addOptional(deepslate);
        provider.apply(MiningLevelManager.getBlockTag(miningLevel)).addOptional(ore).addOptional(deepslate);
        provider.apply(parent.getConventionalBlockTag("raw_%s_blocks")).addOptional(raw);
        provider.apply(BlockTags.NEEDS_STONE_TOOL).addOptional(raw);
        provider.apply(BlockTags.PICKAXE_MINEABLE).addOptional(ore).addOptional(deepslate).addOptional(raw);
    }

    @Override
    public void buildItemTags(Function<TagKey<Item>, FabricTagProvider<Item>.FabricTagBuilder> provider, Element parent) {
        provider.apply(parent.getConventionalItemTag("raw_%s_ores"))
                .addOptional(Registries.ITEM.getId(rawItem.require()));
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
