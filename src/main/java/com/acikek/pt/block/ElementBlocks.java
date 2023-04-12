package com.acikek.pt.block;

import com.acikek.pt.PT;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ElementBlocks {

    public enum ElementalType {

        METAL(Material.METAL, BlockSoundGroup.METAL),
        // TODO fluidic chamber sounds
        CHAMBER(Material.METAL, BlockSoundGroup.METAL),
        ARRAY(Material.GLASS, BlockSoundGroup.GLASS);

        public final Material material;
        public final BlockSoundGroup sounds;

        ElementalType(Material material, BlockSoundGroup sounds) {
            this.material = material;
            this.sounds = sounds;
        }
    }

    public static Block registerBlock(String name, Block block) {
        Identifier id = PT.id(name);
        Registry.register(Registries.BLOCK, id, block);
        Registry.register(Registries.ITEM, id, new BlockItem(block, new FabricItemSettings()));
        return block;
    }

    // TODO rework this when deserializing
    public static Block createSourceBlock(String elementName, float strength, String formula) {
        String name = elementName + "_source";
        return registerBlock(name, new MineralBlock(FabricBlockSettings.of(Material.STONE).requiresTool().strength(strength), formula));
    }

    public static Block createElementalBlock(String elementName, ElementalType type, float strength, int lightLevel) {
        String name = elementName + "_block";
        AbstractBlock.Settings settings = FabricBlockSettings.of(type.material)
                .requiresTool()
                .strength(strength)
                .sounds(type.sounds)
                .luminance(lightLevel);
        return registerBlock(name, new Block(settings));
    }

    public Block source;
    public Block elemental;

    public ElementBlocks(Block source, Block elemental) {
        this.source = source;
        this.elemental = elemental;
    }

    public static class Ore extends ElementBlocks {

        public Block deepslateSource;
        public Block raw;

        public Ore(Block source, Block elemental, Block deepslateSource, Block raw) {
            super(source, elemental);
            this.deepslateSource = deepslateSource;
            this.raw = raw;
        }
    }
}
