package com.acikek.pt.core.element;

import com.acikek.pt.core.mineral.MineralBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AmethystClusterBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;

public class ElementalObjects {

    private static final AbstractBlock.Settings ORE_SETTINGS = FabricBlockSettings.of(Material.STONE)
            .requiresTool()
            .strength(3.0f);

    private static final AbstractBlock.Settings DEEPSLATE_ORE_SETTINGS = FabricBlockSettings.of(Material.STONE)
            .requiresTool()
            .sounds(BlockSoundGroup.DEEPSLATE)
            .strength(4.5f, 3.0f);

    public static final AbstractBlock.Settings MINERAL_SETTINGS = FabricBlockSettings.of(Material.STONE)
            .requiresTool()
            .sounds(BlockSoundGroup.TUFF)
            .strength(3.0f);

    private static final AbstractBlock.Settings CLUSTER_SETTINGS = FabricBlockSettings.of(Material.AMETHYST)
            .sounds(BlockSoundGroup.AMETHYST_CLUSTER)
            .nonOpaque()
            .strength(2.0f);

    private static final AbstractBlock.Settings RAW_BLOCK_SETTINGS = FabricBlockSettings.of(Material.STONE)
            .requiresTool()
            .strength(5.0f, 6.0f);

    public static Block createOreBlock() {
        return new Block(FabricBlockSettings.copyOf(ORE_SETTINGS));
    }

    public static Block createDeepslateOreBlock() {
        return new Block(FabricBlockSettings.copyOf(DEEPSLATE_ORE_SETTINGS));
    }

    public static Block createClusterBlock() {
        return new AmethystClusterBlock(7, 3, FabricBlockSettings.copyOf(CLUSTER_SETTINGS));
    }

    public static Block createRawSourceBlock() {
        return new Block(FabricBlockSettings.copyOf(RAW_BLOCK_SETTINGS));
    }

    public static Item createItem() {
        return new Item(new FabricItemSettings());
    }
}
