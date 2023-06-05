package com.acikek.pt.core.impl.mineral;

import com.acikek.pt.core.api.content.phase.PhasedContent;
import com.acikek.pt.core.api.element.ElementalObjects;
import com.acikek.pt.core.api.display.MineralDisplay;
import com.acikek.pt.core.api.signature.CompoundSignature;
import com.acikek.pt.core.api.signature.SignatureComponent;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;

import java.util.List;
import java.util.function.Supplier;

public class MineralBuilder {

    public static final AbstractBlock.Settings MINERAL_SETTINGS = FabricBlockSettings.of(Material.STONE)
            .requiresTool()
            .sounds(BlockSoundGroup.TUFF)
            .strength(3.0f);

    private PhasedContent<Block> block = PhasedContent.none();
    private boolean hasBlock = false;
    private PhasedContent<Block> cluster = PhasedContent.none();
    private boolean hasCluster = false;
    private PhasedContent<Item> rawMineral = PhasedContent.none();
    private boolean hasRawMineral = false;
    private MineralDisplay naming;
    private Supplier<CompoundSignature> signature;

    public MineralBuilder block(Object block) {
        this.block = PhasedContent.from(block, Block.class);
        return this;
    }

    public MineralBuilder addBlock() {
        hasBlock = true;
        return this;
    }

    public MineralBuilder naming(MineralDisplay naming) {
        this.naming = naming;
        return this;
    }

    public MineralBuilder naming(Object naming) {
        return this.naming(MineralDisplay.forObject(naming));
    }

    public MineralBuilder signature(Supplier<CompoundSignature> signature) {
        this.signature = signature;
        return this;
    }

    public MineralBuilder cluster(Object cluster) {
        this.cluster = PhasedContent.from(cluster, Block.class);
        return this;
    }

    public MineralBuilder addCluster() {
        hasCluster = true;
        return this;
    }

    public MineralBuilder awMineral(Object rawMineral) {
        this.rawMineral = PhasedContent.from(rawMineral, Item.class);
        return this;
    }

    public MineralBuilder addRawMineral() {
        hasRawMineral = true;
        return this;
    }

    public MineralImpl build() {
        return new MineralImpl(
                hasBlock && !block.canExist() ? PhasedContent.of(() -> new Block(MINERAL_SETTINGS)) : block,
                hasCluster && !cluster.canExist() ? PhasedContent.of(ElementalObjects::createClusterBlock) : cluster,
                hasRawMineral && !rawMineral.canExist() ? PhasedContent.of(ElementalObjects::createItem) : rawMineral,
                naming, signature
        );
    }
}
