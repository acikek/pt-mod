package com.acikek.pt.core.refined;

import com.acikek.pt.sound.ModSoundGroups;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;

public enum RefinedBlockType {

    METAL(Material.METAL, BlockSoundGroup.METAL),
    CHAMBER(Material.GLASS, BlockSoundGroup.GLASS),
    BASIN(Material.METAL, ModSoundGroups.FLUID_BASIN);

    private final Material material;
    private final BlockSoundGroup sounds;

    RefinedBlockType(Material material, BlockSoundGroup sounds) {
        this.material = material;
        this.sounds = sounds;
    }

    private float getStrength(float strength) {
        return switch (this) {
            case METAL -> strength;
            case CHAMBER -> 3.0f;
            case BASIN -> 5.0f;
        };
    }

    public AbstractBlock.Settings getBlockSettings(float strength) {
        return FabricBlockSettings.of(material)
                .requiresTool()
                .sounds(sounds)
                .strength(getStrength(strength));
    }
}
