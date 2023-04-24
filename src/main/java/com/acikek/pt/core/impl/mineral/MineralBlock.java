package com.acikek.pt.core.impl.mineral;

import com.acikek.pt.api.PTApi;
import com.acikek.pt.core.api.element.Element;
import com.acikek.pt.core.api.display.MineralDisplay;
import com.acikek.pt.core.api.mineral.Mineral;
import com.acikek.pt.core.api.registry.PTRegistry;
import com.acikek.pt.core.api.signature.ElementSignature;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class MineralBlock extends Block implements Mineral {

    private final MineralDisplay naming;
    private final Block cluster;
    public final Item rawMineral;
    private Supplier<List<ElementSignature>> resultSupplier;

    private List<ElementSignature> results;
    private Text tooltip;

    public MineralBlock(Settings settings, Block cluster, Item rawMineral, MineralDisplay naming, Supplier<List<ElementSignature>> resultSupplier) {
        super(settings);
        if (cluster != null && rawMineral == null) {
            throw new IllegalStateException("mineral clusters must have accompanying raw forms");
        }
        if (naming.rawFormName() == null && rawMineral != null) {
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
        return rawMineral;
    }

    @Override
    public void init() {
        results = resultSupplier.get();
        resultSupplier = null;
        tooltip = createTooltip().copy().formatted(Formatting.GRAY);
    }

    @Override
    public void injectSignature(Element element) {
        if (cluster != null) {
            PTApi.injectSignature(cluster, tooltip);
        }
        if (rawMineral != null) {
            PTApi.injectSignature(rawMineral, tooltip);
        }
    }

    @Override
    public void register(PTRegistry registry) {
        registry.registerBlock(naming.id(), this);
        if (cluster != null) {
            registry.registerBlock(naming.id() + "_cluster", cluster);
        }
        if (rawMineral != null) {
            registry.registerItem(naming.getRawFormId(), rawMineral);
        }
    }

    @Override
    public void buildTranslations(FabricLanguageProvider.TranslationBuilder builder, Element parent) {
        String name = display().englishName();
        builder.add(this, name);
        if (cluster != null) {
            builder.add(cluster, name + " Cluster");
        }
        if (rawMineral != null) {
            builder.add(rawMineral, name + " " + display().rawFormName());
        }
    }

    private void buildMineral(BlockLootTableGenerator generator) {
        generator.addDrop(this,
                BlockLootTableGenerator.dropsWithSilkTouch(this,
                        generator.applyExplosionDecay(this,
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
            generator.addDrop(this);
            return;
        }
        buildMineral(generator);
        if (cluster != null) {
            buildCluster(generator);
        }
    }

    @Override
    public void buildBlockTags(Function<TagKey<Block>, FabricTagProvider<Block>.FabricTagBuilder> provider, Element parent) {
        provider.apply(BlockTags.PICKAXE_MINEABLE).add(this);
        provider.apply(BlockTags.NEEDS_IRON_TOOL).add(this);
        provider.apply(getConventionalBlockTag("s")).add(this);
    }

    @Override
    public List<ElementSignature> signature() {
        return results;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        if (this.tooltip != null && options.isAdvanced()) {
            tooltip.add(this.tooltip);
        }
    }

    @Override
    public List<Block> getBlocks() {
        return cluster != null
                ? List.of(this, cluster)
                : Collections.singletonList(this);
    }

    @Override
    public List<Item> getItems() {
        return rawMineral != null
                ? Collections.singletonList(rawMineral)
                : Collections.emptyList();
    }
}
