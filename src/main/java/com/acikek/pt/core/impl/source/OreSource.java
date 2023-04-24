package com.acikek.pt.core.impl.source;

import com.acikek.pt.core.api.element.Element;
import com.acikek.pt.core.api.registry.ElementIds;
import com.acikek.pt.core.api.registry.PTRegistry;
import com.acikek.pt.core.api.source.ElementSources;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.mininglevel.v1.MiningLevelManager;
import net.minecraft.block.Block;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

public class OreSource extends UndergroundSource {

    private final Block ore;
    private final Block deepslateOre;
    private final Item rawItem;
    private final Block rawBlock;
    private final int miningLevel;

    public OreSource(Block ore, Block deepslateOre, Item rawItem, Block rawBlock, int miningLevel) {
        Stream.of(ore, deepslateOre, rawItem, rawBlock).forEach(Objects::requireNonNull);
        this.ore = ore;
        this.deepslateOre = deepslateOre;
        this.rawItem = rawItem;
        this.rawBlock = rawBlock;
        this.miningLevel = miningLevel;
    }

    @Override
    public @NotNull Identifier getId() {
        return ElementSources.ORE;
    }

    @Override
    public Item mineralResultItem() {
        return rawItem;
    }

    @Override
    public void register(PTRegistry registry, ElementIds<String> ids) {
        registry.registerBlock(ids.getSourceBlockId(), ore);
        registry.registerBlock(ids.getDeepslateSourceBlockId(), deepslateOre);
        registry.registerItem(ids.getRawSourceItemId(), rawItem);
        registry.registerBlock(ids.getRawSourceBlockId(), rawBlock);
    }

    @Override
    public void buildTranslations(FabricLanguageProvider.TranslationBuilder builder, Element parent) {
        String name = parent.display().englishName();
        builder.add(ore, name + " Ore");
        builder.add(deepslateOre, "Deepslate " + name + " Ore");
        builder.add(rawItem, "Raw " + name);
        builder.add(rawBlock, "Block of Raw " + name);
    }

    @Override
    public void buildLootTables(BlockLootTableGenerator generator, Element parent) {

    }

    @Override
    public void buildBlockTags(Function<TagKey<Block>, FabricTagProvider<Block>.FabricTagBuilder> provider, Element parent) {
        provider.apply(parent.getConventionalBlockTag("%s_ores")).add(ore, deepslateOre);
        provider.apply(MiningLevelManager.getBlockTag(miningLevel)).add(ore, deepslateOre);
        provider.apply(parent.getConventionalBlockTag("raw_%s_blocks")).add(rawBlock);
        provider.apply(BlockTags.NEEDS_STONE_TOOL).add(rawBlock);
        provider.apply(BlockTags.PICKAXE_MINEABLE).add(ore, deepslateOre, rawBlock);
    }

    @Override
    public void buildItemTags(Function<TagKey<Item>, FabricTagProvider<Item>.FabricTagBuilder> provider, Element parent) {
        provider.apply(parent.getConventionalItemTag("raw_%s_ores")).add(rawItem);
    }

    @Override
    public List<Block> getBlocks() {
        return List.of(ore, deepslateOre, rawBlock);
    }

    @Override
    public List<Item> getItems() {
        return Collections.singletonList(rawItem);
    }
}
