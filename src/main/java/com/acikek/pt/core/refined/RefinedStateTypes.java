package com.acikek.pt.core.refined;

import com.acikek.pt.sound.ModSoundGroups;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.Material;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.TexturedModel;
import net.minecraft.sound.BlockSoundGroup;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public enum RefinedStateTypes implements RefinedStateType {

    METAL("Block of %s", "%s Ingot", "%s Nugget", null, Material.METAL, BlockSoundGroup.METAL),
    CHAMBER("%s Chamber", "%s Tank", "%s Cell", 3.0f, Material.GLASS, BlockSoundGroup.GLASS),
    BASIN("%s Basin", "Bottle of %s", "%s Vial", 5.0f, Material.METAL, ModSoundGroups.FLUID_BASIN),
    SACK("%s Bundle", "%s Sack", "Bag of %s", 1.5f, Material.AGGREGATE, BlockSoundGroup.SAND),
    BLOOM("%s Pile", "%s Bloom", "%s Pellet", 4.5f, Material.STONE, BlockSoundGroup.STONE), // TODO
    TRACE("%s Sample", "%s Trace", "%s Array", 2.5f, Material.GLASS, BlockSoundGroup.GLASS);

    private final String blockFormat;
    private final String itemFormat;
    private final String miniItemFormat;
    private final Float defaultStrength;
    private final Material material;
    private final BlockSoundGroup sounds;

    RefinedStateTypes(String blockFormat, String itemFormat, String miniItemFormat, Float defaultStrength, Material material, BlockSoundGroup sounds) {
        this.blockFormat = blockFormat;
        this.itemFormat = itemFormat;
        this.miniItemFormat = miniItemFormat;
        this.defaultStrength = defaultStrength;
        this.material = material;
        this.sounds = sounds;
    }

    @Override
    public String getBlockName(String elementName) {
        return blockFormat.formatted(elementName);
    }

    @Override
    public String getItemName(String elementName) {
        return itemFormat.formatted(elementName);
    }

    @Override
    public String getMiniItemName(String elementName) {
        return miniItemFormat.formatted(elementName);
    }

    @Override
    public Block createBlock(@Nullable Float strength) {
        Float blockStrength = strength != null
                ? strength
                : defaultStrength;
        Objects.requireNonNull(blockStrength);
        AbstractBlock.Settings settings = FabricBlockSettings.of(material)
                .requiresTool()
                .sounds(sounds)
                .strength(blockStrength);
        return this == CHAMBER
                ? new HorizontalFacingBlock(settings) {}
                : new Block(settings);
    }

    @Override
    public void buildBlockModel(BlockStateModelGenerator generator, Block block) {
        switch (this) {
            case METAL, SACK, BLOOM, TRACE -> generator.registerSimpleCubeAll(block);
            case CHAMBER -> generator.registerRotatable(block);
        }
    }
}
