package com.acikek.pt.core.impl.source;

import com.acikek.pt.core.element.Element;
import com.acikek.pt.core.mineral.Mineral;
import com.acikek.pt.core.mineral.MineralBlock;
import com.acikek.pt.core.registry.ElementIds;
import com.acikek.pt.core.registry.ElementRegistry;
import com.acikek.pt.core.source.ElementSource;
import com.acikek.pt.core.source.ElementSources;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class MineralSource implements ElementSource {

    private final MineralBlock mineral;
    private final Block cluster;
    private final Item rawMineral;

    public MineralSource(MineralBlock mineral, Block cluster, Item rawMineral) {
        Objects.requireNonNull(mineral);
        if (cluster != null && rawMineral == null) {
            throw new IllegalStateException("mineral clusters must have accompanying raw forms");
        }
        this.mineral = mineral;
        this.cluster = cluster;
        this.rawMineral = rawMineral;
    }


    @Override
    public @NotNull Identifier getId() {
        return ElementSources.MINERAL;
    }

    @Override
    public Mineral mineral() {
        return mineral;
    }

    @Override
    public Item mineralResultItem() {
        return rawMineral;
    }

    @Override
    public void register(ElementRegistry registry, ElementIds<String> ids) {
        registry.registerBlock(ids.getSourceBlockId(), mineral);
        if (cluster != null) {
            registry.registerBlock(ids.getClusterSourceBlockId(), cluster);
        }
        if (rawMineral != null) {
            registry.registerItem(ids.getRawSourceItemId(), rawMineral);
        }
    }

    @Override
    public void buildTranslations(FabricLanguageProvider.TranslationBuilder builder, Element parent) {
        String name = mineral.naming().englishName();
        builder.add(mineral, name);
        if (cluster != null) {
            builder.add(cluster, name + " Cluster");
        }
        if (rawMineral != null) {
            builder.add(rawMineral, name + " " + mineral.naming().rawFormName());
        }
    }

    private void buildMineral(BlockLootTableGenerator generator) {
        generator.addDrop(mineral,
                BlockLootTableGenerator.dropsWithSilkTouch(mineral,
                generator.applyExplosionDecay(mineral,
                        ((LeafEntry.Builder<?>) ItemEntry.builder(rawMineral)
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 3.0f))))
                                .apply(ApplyBonusLootFunction.uniformBonusCount(Enchantments.FORTUNE))
                ))
        );
    }

    private void buildCluster(BlockLootTableGenerator generator) {
        generator.addDrop(cluster,
                BlockLootTableGenerator.dropsWithSilkTouch(cluster,
                ((LeafEntry.Builder<?>) ItemEntry.builder(rawMineral)
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2.0f))))
                        .apply(ApplyBonusLootFunction.oreDrops(Enchantments.FORTUNE))
                        .conditionally(MatchToolLootCondition.builder(ItemPredicate.Builder.create().tag(ItemTags.CLUSTER_MAX_HARVESTABLES)))
                        .alternatively(generator.applyExplosionDecay(cluster,
                                ItemEntry.builder(Items.AMETHYST_SHARD)
                                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f))))
                        ))
        );
    }

    @Override
    public void buildLootTables(BlockLootTableGenerator generator, Element parent) {
        if (rawMineral == null) {
            generator.addDrop(mineral);
            return;
        }
        buildMineral(generator);
        if (cluster != null) {
            buildCluster(generator);
        }
    }

    @Override
    public void buildAdditionalBlockTags(Function<TagKey<Block>, FabricTagProvider<Block>.FabricTagBuilder> provider, Element parent) {
        provider.apply(BlockTags.NEEDS_IRON_TOOL).add(mineral);
    }

    @Override
    public List<Block> getBlocks() {
        return cluster != null
                ? List.of(mineral, cluster)
                : Collections.singletonList(mineral);
    }

    @Override
    public List<Item> getItems() {
        return rawMineral != null
                ? Collections.singletonList(rawMineral)
                : Collections.emptyList();
    }
}
