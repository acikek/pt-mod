package com.acikek.pt.core.refined;

import com.acikek.pt.api.datagen.PTDatagenApi;
import com.acikek.pt.block.ModBlocks;
import com.acikek.pt.core.element.Element;
import com.acikek.pt.sound.ModSoundGroups;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.data.client.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Function;

public enum RefinedStateTypes implements RefinedStateType {

    METAL("Block of %s", "%s Ingot", "%s Nugget", null, Material.METAL, BlockSoundGroup.METAL),
    GAS("%s Tank", "Compressed %s", "%s Cell", 3.0f, Material.GLASS, BlockSoundGroup.GLASS),
    FLUID("%s Drum", "Bottle of %s", "%s Vial", 5.0f, Material.METAL, ModSoundGroups.FLUID_BASIN),
    POWDER("Sack of %s", "Bag of %s", "%s Pouch", 1.5f, Material.AGGREGATE, BlockSoundGroup.SAND),
    SYNTHESIZED("Pile of %s", "%s Bloom", "%s Pellet", 3.5f, Material.STONE, BlockSoundGroup.STONE), // TODO
    BILLET("Pile of %s", "%s Billet", "%s Pellet", 6.0f, Material.METAL, BlockSoundGroup.METAL),
    TRACE("%s Sample", "%s Trace", "%s Array", 2.5f, Material.GLASS, BlockSoundGroup.GLASS);

    private final String blockFormat, itemFormat, miniItemFormat;
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
    @Override
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

    @Override
    public void buildBlockModel(BlockStateModelGenerator generator, Block block) {
        switch (this) {
            case METAL, SYNTHESIZED, TRACE -> generator.registerSimpleCubeAll(block);
            case GAS -> generator.registerNorthDefaultHorizontalRotated(block, TexturedModel.ORIENTABLE_WITH_BOTTOM);
            case POWDER -> generator.registerNorthDefaultHorizontalRotated(block, PTDatagenApi.POWDER_MODEL_FACTORY);
        }
    }

    @Override
    public void buildBlockTags(Function<TagKey<Block>, FabricTagProvider<Block>.FabricTagBuilder> provider, Block block) {
        if (this != POWDER) {
            provider.apply(BlockTags.PICKAXE_MINEABLE).add(block);
        }
    }
}
