package com.acikek.pt.core.impl.mineral;

import com.acikek.pt.api.PTApi;
import com.acikek.pt.api.request.FeatureRequests;
import com.acikek.pt.api.request.RequestTypes;
import com.acikek.pt.core.api.content.PhasedContent;
import com.acikek.pt.core.api.display.MineralDisplay;
import com.acikek.pt.core.api.element.Element;
import com.acikek.pt.core.api.mineral.Mineral;
import com.acikek.pt.core.api.registry.PTRegistry;
import com.acikek.pt.core.api.signature.ElementSignature;
import com.acikek.pt.core.api.signature.SignatureHolder;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class MineralImpl implements Mineral {

    private final MineralDisplay naming;
    private final PhasedContent<Block> block;
    private final PhasedContent<Block> cluster;
    public final PhasedContent<Item> rawMineral;
    private Supplier<List<ElementSignature>> resultSupplier;

    private List<ElementSignature> results;
    private Text tooltip;

    public MineralImpl(Supplier<Block> block, Supplier<Block> cluster, Supplier<Item> rawMineral, MineralDisplay naming, Supplier<List<ElementSignature>> resultSupplier) {
        this.block = PhasedContent.of(block);
        if (cluster != null && rawMineral == null) {
            throw new IllegalStateException("mineral clusters must have accompanying raw forms");
        }
        if (naming.rawFormName() == null && rawMineral != null) {
            throw new IllegalStateException("raw mineral must have a unique species name");
        }
        this.cluster = cluster != null ? PhasedContent.of(cluster) : null;
        this.rawMineral = rawMineral != null ? PhasedContent.of(rawMineral) : null;
        this.naming = naming;
        this.resultSupplier = resultSupplier;
    }

    @Override
    public @NotNull MineralDisplay display() {
        return naming;
    }

    @Override
    public Item mineralResultItem() {
        return rawMineral.get();
    }

    @Override
    public void init() {
        results = resultSupplier.get();
        resultSupplier = null;
        tooltip = createTooltip().copy().formatted(Formatting.GRAY);
    }

    @Override
    public void injectSignature(SignatureHolder holder) {
        if (block.isCreated()) {
            PTApi.injectSignature(block.require(), tooltip);
        }
        if (cluster != null && cluster.isCreated()) {
            PTApi.injectSignature(cluster.require(), tooltip);
        }
        if (rawMineral != null && rawMineral.isCreated()) {
            PTApi.injectSignature(rawMineral.require(), tooltip);
        }
    }

    @Override
    public void register(PTRegistry registry, FeatureRequests.Single features) {
        if (!features.contains(RequestTypes.CONTENT)) {
            return;
        }
        registry.registerBlock(naming.id(), block.create());
        if (cluster != null) {
            registry.registerBlock(naming.id() + "_cluster", cluster.create());
        }
        if (rawMineral != null) {
            registry.registerItem(naming.getRawFormId(), rawMineral.create());
        }
    }

    @Override
    public void buildTranslations(FabricLanguageProvider.TranslationBuilder builder, Element parent) {
        String name = display().englishName();
        builder.add(block.require(), name);
        if (cluster != null) {
            builder.add(cluster.require(), name + " Cluster");
        }
        if (rawMineral != null) {
            builder.add(rawMineral.require(), name + " " + display().rawFormName());
        }
    }

    private void buildMineral(BlockLootTableGenerator generator) {
        generator.addDrop(block.require(),
                BlockLootTableGenerator.dropsWithSilkTouch(block.require(),
                        generator.applyExplosionDecay(block.require(),
                                ((LeafEntry.Builder<?>) ItemEntry.builder(rawMineral.require())
                                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 3.0f))))
                                        .apply(ApplyBonusLootFunction.uniformBonusCount(Enchantments.FORTUNE))
                        ))
        );
    }

    private void buildCluster(BlockLootTableGenerator generator) {
        generator.addDrop(cluster.require(),
                BlockLootTableGenerator.dropsWithSilkTouch(cluster.require(),
                        ((LeafEntry.Builder<?>) ItemEntry.builder(rawMineral.require())
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2.0f))))
                                .apply(ApplyBonusLootFunction.oreDrops(Enchantments.FORTUNE))
                                .conditionally(MatchToolLootCondition.builder(ItemPredicate.Builder.create().tag(ItemTags.CLUSTER_MAX_HARVESTABLES)))
                                .alternatively(generator.applyExplosionDecay(cluster.require(),
                                        ItemEntry.builder(rawMineral.require())
                                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f))))
                                ))
        );
    }

    @Override
    public void buildLootTables(BlockLootTableGenerator generator, Element parent) {
        if (rawMineral == null) {
            generator.addDrop(block.require());
            return;
        }
        buildMineral(generator);
        if (cluster != null) {
            buildCluster(generator);
        }
    }

    @Override
    public void buildBlockTags(Function<TagKey<Block>, FabricTagProvider<Block>.FabricTagBuilder> provider, Element parent) {
        var id = Registries.BLOCK.getId(block.require());
        provider.apply(BlockTags.PICKAXE_MINEABLE).addOptional(id);
        provider.apply(BlockTags.NEEDS_IRON_TOOL).addOptional(id);
        provider.apply(getConventionalBlockTag("%ss")).addOptional(id);
        if (cluster != null) {
            provider.apply(getConventionalBlockTag("%s_clusters")).addOptional(Registries.BLOCK.getId(cluster.require()));
        }
    }

    @Override
    public void buildItemTags(Function<TagKey<Item>, FabricTagProvider<Item>.FabricTagBuilder> provider, Element parent) {
        if (rawMineral != null) {
            for (String formatter : List.of("raw_%ss", "%s_crystals")) {
                provider.apply(getConventionalItemTag(formatter)).addOptional(Registries.ITEM.getId(rawMineral.require()));
            }
        }
    }

    @Override
    public List<ElementSignature> signature() {
        return results;
    }

    @Override
    public List<Block> getBlocks() {
        if (!block.isCreated()) {
            return Collections.emptyList();
        }
        return cluster != null
                ? List.of(block.require(), cluster.require())
                : Collections.singletonList(block.require());
    }

    @Override
    public List<Item> getItems() {
        if (!block.isCreated()) {
            return Collections.emptyList();
        }
        return rawMineral != null
                ? Collections.singletonList(rawMineral.require())
                : Collections.emptyList();
    }
}
