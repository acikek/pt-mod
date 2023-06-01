package com.acikek.pt.core.impl.mineral;

import com.acikek.pt.api.PTApi;
import com.acikek.pt.api.request.FeatureRequests;
import com.acikek.pt.api.request.RequestTypes;
import com.acikek.pt.core.api.content.PhasedContent;
import com.acikek.pt.core.api.display.MineralDisplay;
import com.acikek.pt.core.api.mineral.DefaultMineralData;
import com.acikek.pt.core.api.mineral.Mineral;
import com.acikek.pt.core.api.registry.PTRegistry;
import com.acikek.pt.core.api.signature.ElementSignature;
import com.acikek.pt.core.api.signature.SignatureHolder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.DefaultResourceConditions;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
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
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class MineralImpl implements Mineral<DefaultMineralData> {

    private final MineralDisplay naming;
    private final PhasedContent<Block> block;
    private final PhasedContent<Block> cluster;
    public final PhasedContent<Item> rawMineral;
    private Supplier<List<ElementSignature>> resultSupplier;

    private List<ElementSignature> results;
    private Text tooltip;

    public MineralImpl(PhasedContent<Block> block, PhasedContent<Block> cluster, PhasedContent<Item> rawMineral, MineralDisplay naming, Supplier<List<ElementSignature>> resultSupplier) {
        Stream.of(naming, resultSupplier).forEach(Objects::requireNonNull);
        this.block = block;
        if (cluster.canExist() && !rawMineral.canExist()) {
            throw new IllegalStateException("mineral clusters must have accompanying raw forms");
        }
        if (naming.rawFormName() == null && rawMineral.canExist()) {
            throw new IllegalStateException("raw mineral must have a unique species name");
        }
        this.cluster = cluster;
        this.rawMineral = rawMineral;
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
        for (var block : PhasedContent.getByCreation(block, cluster)) {
            PTApi.injectSignature(block, tooltip);
        }
        rawMineral.ifCreated(r -> PTApi.injectSignature(r, tooltip));
    }

    @Override
    public void register(PTRegistry registry, FeatureRequests.Single features) {
        if (!features.contains(RequestTypes.CONTENT)) {
            return;
        }
        block.create(block -> registry.registerBlock(naming.id(), block));
        cluster.create(block -> registry.registerBlock(naming.id() + "_cluster", block));
        rawMineral.create(item -> registry.registerItem(naming.getRawFormId(), item));
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void initClient() {
        cluster.ifCreated(block -> BlockRenderLayerMap.INSTANCE.putBlock(cluster.require(), RenderLayer.getCutout()));
    }

    @Override
    public void buildBlockModels(BlockStateModelGenerator generator) {
        block.require(generator::registerSimpleCubeAll);
        cluster.require(generator::registerAmethyst);
    }

    @Override
    public void buildItemModels(ItemModelGenerator generator) {
        cluster.require(block -> generator.register(block.asItem(), Models.GENERATED));
        rawMineral.require(item -> generator.register(item, Models.GENERATED));
    }

    @Override
    public void buildTranslations(FabricLanguageProvider.TranslationBuilder builder) {
        String name = display().englishName();
        block.require(b -> builder.add(b, name));
        cluster.require(block -> builder.add(block, name + " Cluster"));
        rawMineral.require(item -> builder.add(item, name + " " + display().rawFormName()));
        builder.add(getConventionalBlockKey("%ss"), name);
        if (cluster.canExist()) {
            builder.add(getConventionalBlockKey("%s_clusters"), name + " Clusters");
        }
        if (rawMineral.canExist()) {
            for (var formatter : List.of("raw_%ss", "%s_crystals")) {
                builder.add(getConventionalItemKey(formatter), name + " Crystals");
            }
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
    public void buildLootTables(FabricBlockLootTableProvider provider) {
        block.require(block -> {
            var generator = provider.withConditions(DefaultResourceConditions.itemsRegistered(block));
            if (!rawMineral.canExist()) {
                generator.addDrop(block);
                return;
            }
            buildMineral(generator);
            if (cluster.canExist()) {
                buildCluster(generator);
            }
        });
    }

    @Override
    public void buildBlockTags(Function<TagKey<Block>, FabricTagProvider<Block>.FabricTagBuilder> provider) {
        block.ifCreated(b -> {
            var id = Registries.BLOCK.getId(b);
            provider.apply(getConventionalBlockTag("%ss")).addOptional(id);
            if (block.isInternal()) {
                provider.apply(BlockTags.PICKAXE_MINEABLE).addOptional(id);
                provider.apply(BlockTags.NEEDS_IRON_TOOL).addOptional(id);
            }
        });
        cluster.ifCreated(c -> {
            var id = Registries.BLOCK.getId(c);
            provider.apply(getConventionalBlockTag("%s_clusters")).addOptional(id);
            if (cluster.isInternal()) {
                provider.apply(BlockTags.PICKAXE_MINEABLE).addOptional(id);
            }
        });
    }

    @Override
    public void buildItemTags(Function<TagKey<Item>, FabricTagProvider<Item>.FabricTagBuilder> provider) {
        rawMineral.ifCreated(raw -> {
            for (String formatter : List.of("raw_%ss", "%s_crystals")) {
                provider.apply(getConventionalItemTag(formatter)).addOptional(Registries.ITEM.getId(raw));
            }
        });
    }

    @Override
    public List<ElementSignature> signature() {
        return results;
    }

    @Override
    public List<Block> getBlocks() {
        return PhasedContent.getByCreation(block, cluster);
    }

    @Override
    public List<Item> getItems() {
        return block.isCreated() && rawMineral.isCreated()
                ? Collections.singletonList(rawMineral.require())
                : Collections.emptyList();
    }

    @Override
    public DefaultMineralData getData() {
        return new DefaultMineralData(block.get(), cluster.get(), rawMineral.get());
    }
}
