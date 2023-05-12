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

public class MineralBuilder {

    public static final AbstractBlock.Settings MINERAL_SETTINGS = FabricBlockSettings.of(Material.STONE)
            .requiresTool()
            .sounds(BlockSoundGroup.TUFF)
            .strength(3.0f);

    private Supplier<Block> block;
    private Supplier<Block> cluster;
    private boolean hasCluster = false;
    private Supplier<Item> rawMineral;
    private boolean hasRawMineral = false;
    private MineralDisplay naming;
    private Supplier<List<ElementSignature>> signature;

    public MineralBuilder block(Supplier<Block> block) {
        this.block = block;
        return this;
    }

    public MineralBuilder naming(MineralDisplay naming) {
        this.naming = naming;
        return this;
    }

    public MineralBuilder naming(Object naming) {
        return this.naming(MineralDisplay.forObject(naming));
    }

    public MineralBuilder signature(Supplier<List<ElementSignature>> signature) {
        this.signature = signature;
        return this;
    }

    public MineralBuilder addCluster(Supplier<Block> cluster) {
        this.cluster = cluster;
        return this;
    }

    public MineralBuilder addCluster() {
        hasCluster = true;
        return this;
    }

    public MineralBuilder addRawMineral(Supplier<Item> rawMineral) {
        this.rawMineral = rawMineral;
        return this;
    }

    public MineralBuilder addRawMineral() {
        hasRawMineral = true;
        return this;
    }

    public MineralImpl build() {
        Stream.of(naming, signature).forEach(Objects::requireNonNull);
        return new MineralImpl(
                block != null ? block : () -> new Block(MINERAL_SETTINGS),
                cluster != null ? cluster : hasCluster ? ElementalObjects::createClusterBlock : null,
                rawMineral != null ? rawMineral : hasRawMineral ? ElementalObjects::createItem : null,
                naming, signature
        );
    }
}
