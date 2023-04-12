package com.acikek.pt.core;

import com.acikek.pt.block.ElementBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;

public class Element {

    public ElementBlocks blocks;

    public Item raw;
    public Item item;
    public Item sliver;

    public static AbstractBlock.Settings getOreSettings(float strength, boolean isDeepslate) {
        return FabricBlockSettings.of(Material.STONE)
                .requiresTool()
                .strength(strength + (isDeepslate ? 1.5f : 0f), strength)
                .sounds(isDeepslate ? BlockSoundGroup.DEEPSLATE : BlockSoundGroup.STONE);
    }

    public Element(ElementBlocks blocks, Item raw, Item item, Item sliver) {
        this.blocks = blocks;
        this.raw = raw;
        this.item = item;
        this.sliver = sliver;
    }
}
