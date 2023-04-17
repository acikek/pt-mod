package com.acikek.pt.core.refined;

import com.acikek.pt.sound.ModSoundGroups;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;

public enum RefinedStateTypes implements RefinedStateType {

    METAL("Block of %s", "%s Ingot", "%s Nugget", Material.METAL, BlockSoundGroup.METAL),
    CHAMBER("%s Chamber", "%s Tank", "%s Cell", Material.GLASS, BlockSoundGroup.GLASS),
    BASIN("%s Basin", "Bottle of %s", "%s Vial", Material.METAL, ModSoundGroups.FLUID_BASIN);

    private final String blockFormat;
    private final String itemFormat;
    private final String miniItemFormat;
    private final Material material;
    private final BlockSoundGroup sounds;

    RefinedStateTypes(String blockFormat, String itemFormat, String miniItemFormat, Material material, BlockSoundGroup sounds) {
        this.blockFormat = blockFormat;
        this.itemFormat = itemFormat;
        this.miniItemFormat = miniItemFormat;
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

    private float getStrength(float strength) {
        return switch (this) {
            case METAL -> strength;
            case CHAMBER -> 3.0f;
            case BASIN -> 5.0f;
        };
    }

    @Override
    public AbstractBlock.Settings getBlockSettings(float strength) {
        return FabricBlockSettings.of(material)
                .requiresTool()
                .sounds(sounds)
                .strength(getStrength(strength));
    }
}
