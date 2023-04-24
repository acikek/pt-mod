package com.acikek.pt.core.impl.mineral;

import com.acikek.pt.core.api.element.ElementalObjects;
import com.acikek.pt.core.api.display.MineralDisplay;
import com.acikek.pt.core.api.signature.ElementSignature;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class MineralBlockBuilder {

    public static final AbstractBlock.Settings MINERAL_SETTINGS = FabricBlockSettings.of(Material.STONE)
            .requiresTool()
            .sounds(BlockSoundGroup.TUFF)
            .strength(3.0f);

    private AbstractBlock.Settings settings;
    private Block cluster;
    private boolean hasCluster = false;
    private Item rawMineral;
    private boolean hasRawMineral = false;
    private MineralDisplay naming;
    private Supplier<List<ElementSignature>> signature;

    public MineralBlockBuilder settings(AbstractBlock.Settings settings) {
        this.settings = settings;
        return this;
    }

    public MineralBlockBuilder naming(MineralDisplay naming) {
        this.naming = naming;
        return this;
    }

    public MineralBlockBuilder naming(Object naming) {
        return this.naming(MineralDisplay.forObject(naming));
    }

    public MineralBlockBuilder signature(Supplier<List<ElementSignature>> signature) {
        this.signature = signature;
        return this;
    }

    public MineralBlockBuilder addCluster(Block cluster) {
        this.cluster = cluster;
        return this;
    }

    public MineralBlockBuilder addCluster() {
        hasCluster = true;
        return this;
    }

    public MineralBlockBuilder addRawMineral(Item rawMineral) {
        this.rawMineral = rawMineral;
        return this;
    }

    public MineralBlockBuilder addRawMineral() {
        hasRawMineral = true;
        return this;
    }

    public MineralBlock build() {
        Stream.of(naming, signature).forEach(Objects::requireNonNull);
        return new MineralBlock(
                settings != null ? settings : MINERAL_SETTINGS,
                cluster != null ? cluster : hasCluster ? ElementalObjects.createClusterBlock() : null,
                rawMineral != null ? rawMineral : hasRawMineral ? ElementalObjects.createItem() : null,
                naming, signature
        );
    }
}
