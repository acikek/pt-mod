package com.acikek.pt.core.impl.source;

import com.acikek.pt.core.element.Element;
import com.acikek.pt.core.registry.ElementIds;
import com.acikek.pt.core.registry.PTRegistry;
import com.acikek.pt.core.source.ElementSource;
import com.acikek.pt.core.source.ElementSources;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.block.Block;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class OreSource extends UndergroundSource {

    public Block ore;
    public Block deepslateOre;
    public Item rawItem;
    public Block rawBlock;

    public OreSource(Block ore, Block deepslateOre, Item rawItem, Block rawBlock) {
        Stream.of(ore, deepslateOre, rawItem, rawBlock).forEach(Objects::requireNonNull);
        this.ore = ore;
        this.deepslateOre = deepslateOre;
        this.rawItem = rawItem;
        this.rawBlock = rawBlock;
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
    public List<Block> getBlocks() {
        return List.of(ore, deepslateOre, rawBlock);
    }

    @Override
    public List<Item> getItems() {
        return Collections.singletonList(rawItem);
    }
}
