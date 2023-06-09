package com.acikek.pt.core.api.refined;

import com.acikek.pt.api.datagen.PTDatagenApi;
import com.acikek.pt.api.datagen.provider.tag.PTTagProviders;
import com.acikek.pt.sound.ModSoundGroups;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.TexturedModel;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public enum RefinedStateType {

    METAL("Block of %s", "%s Ingot", "%s Nugget", null, Material.METAL, BlockSoundGroup.METAL),
    GAS("%s Tank", "Compressed %s", "%s Cell", 3.0f, Material.GLASS, BlockSoundGroup.GLASS),
    FLUID("%s Drum", "Bottle of %s", "%s Vial", 5.0f, Material.METAL, ModSoundGroups.FLUID_BASIN),
    POWDER("Sack of %s", "Bag of %s", "%s Pouch", 0.75f, Material.AGGREGATE, BlockSoundGroup.SAND),
    SYNTHESIZED("Pile of %s", "%s Bloom", "%s Pellet", 3.5f, Material.STONE, BlockSoundGroup.STONE), // TODO
    BILLET("Pile of %s", "%s Billet", "%s Pellet", 6.0f, Material.METAL, BlockSoundGroup.METAL),
    TRACE("%s Sample", "%s Trace", "%s Array", 2.5f, Material.GLASS, BlockSoundGroup.GLASS);

    private final String blockFormat, itemFormat, miniItemFormat;
    private final Float defaultStrength;
    private final Material material;
    private final BlockSoundGroup sounds;

    RefinedStateType(String blockFormat, String itemFormat, String miniItemFormat, Float defaultStrength, Material material, BlockSoundGroup sounds) {
        this.blockFormat = blockFormat;
        this.itemFormat = itemFormat;
        this.miniItemFormat = miniItemFormat;
        this.defaultStrength = defaultStrength;
        this.material = material;
        this.sounds = sounds;
    }

    public String getBlockName(String elementName) {
        return blockFormat.formatted(elementName);
    }

    public String getItemName(String elementName) {
        return itemFormat.formatted(elementName);
    }

    public String getMiniItemName(String elementName) {
        return miniItemFormat.formatted(elementName);
    }

    private AbstractBlock.Settings getSettings(@Nullable Float strength) {
        Float blockStrength = strength != null
                ? strength
                : defaultStrength;
        Objects.requireNonNull(blockStrength);
        var settings = FabricBlockSettings.of(material)
                .sounds(sounds)
                .strength(blockStrength);
        if (this != POWDER) {
            settings.requiresTool();
        }
        return settings;
    }

    public AbstractBlock.Settings getBaseSettings() {
        return getSettings(null);
    }

    // TODO make this look nicer
    public Block createBlock(@Nullable Float strength) {
        AbstractBlock.Settings settings = getSettings(strength);
        return this == GAS || this == POWDER
                ? new HorizontalFacingBlock(settings) {
                    @Override
                    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
                        builder.add(FACING);
                    }

                    @Nullable
                    @Override
                    public BlockState getPlacementState(ItemPlacementContext ctx) {
                        return getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
                    }
                }
                : new Block(settings);
    }

    public void buildRefinedBlockModel(BlockStateModelGenerator generator, Block block) {
        switch (this) {
            case METAL, SYNTHESIZED, TRACE -> generator.registerSimpleCubeAll(block);
            case GAS -> generator.registerNorthDefaultHorizontalRotated(block, TexturedModel.ORIENTABLE_WITH_BOTTOM);
            case POWDER -> generator.registerNorthDefaultHorizontalRotated(block, PTDatagenApi.POWDER_MODEL_FACTORY);
        }
    }

    public void buildRefinedBlockTags(PTTagProviders.BlockTagProvider provider, Block block) {
        if (this != POWDER) {
            provider.add(BlockTags.PICKAXE_MINEABLE, block);
            provider.add(this == TRACE ? BlockTags.NEEDS_STONE_TOOL : BlockTags.NEEDS_IRON_TOOL, block);
        }
    }
}
