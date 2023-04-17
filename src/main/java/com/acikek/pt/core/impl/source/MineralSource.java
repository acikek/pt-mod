package com.acikek.pt.core.impl.source;

import com.acikek.pt.core.element.Element;
import com.acikek.pt.core.mineral.Mineral;
import com.acikek.pt.core.mineral.MineralBlock;
import com.acikek.pt.core.registry.ElementIds;
import com.acikek.pt.core.registry.ElementRegistry;
import com.acikek.pt.core.source.ElementSource;
import com.acikek.pt.core.source.ElementSources;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
