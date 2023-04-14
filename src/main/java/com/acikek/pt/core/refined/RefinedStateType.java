package com.acikek.pt.core.refined;

import com.acikek.pt.sound.ModSoundGroups;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;

public enum RefinedStateType {

    METAL("Block of %s", "%s Ingot", "%s Nugget", Material.METAL, BlockSoundGroup.METAL),
    CHAMBER("%s Chamber", "%s Tank", "%s Cell", Material.GLASS, BlockSoundGroup.GLASS),
    BASIN("%s Basin", "Bottle of %s", "%s Vial", Material.METAL, ModSoundGroups.FLUID_BASIN);

    private final String blockFormat;
    private final String itemFormat;
    private final String miniItemFormat;
    private final Material material;
    private final BlockSoundGroup sounds;

    RefinedStateType(String blockFormat, String itemFormat, String miniItemFormat, Material material, BlockSoundGroup sounds) {
        this.blockFormat = blockFormat;
        this.itemFormat = itemFormat;
        this.miniItemFormat = miniItemFormat;
        this.material = material;
        this.sounds = sounds;
    }

    public String formatBlock(String name) {
        return blockFormat.formatted(name);
    }

    public String formatItem(String name) {
        return itemFormat.formatted(name);
    }

    public String formatMiniItem(String name) {
        return miniItemFormat.formatted(name);
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
