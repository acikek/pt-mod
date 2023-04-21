package com.acikek.pt.api.impl.datagen;

import com.acikek.pt.block.ModBlocks;
import com.acikek.pt.core.element.Element;
import com.acikek.pt.core.refined.ElementRefinedState;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.*;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class PTDatagenImpl {

    //public static final Model POWDER_BLOCK_MODEL = new Model(Optional.of(PT.id("powder_block")))

    private static void buildTranslationsForState(FabricLanguageProvider.TranslationBuilder builder, Element element, ElementRefinedState state) {
        // TODO test
        if (state.hasRefinedFluid()) {
            builder.add(state.refinedFluid().getDefaultState().getBlockState().getBlock(), element.display().englishName());
        }
        builder.add(state.refinedBlock(), element.getRefinedBlockName());
        builder.add(state.refinedItem(), element.getRefinedItemName());
        builder.add(state.miniRefinedItem(), element.getMiniRefinedItemName());
    }

    public static void buildTranslationsForElement(FabricLanguageProvider.TranslationBuilder builder, Element element) {
        builder.add(element.getNameKey(), element.display().englishName());
        builder.add(element.getSymbolKey(), element.display().symbol());
        element.forEachSource(source -> source.buildTranslations(builder, element));
        buildTranslationsForState(builder, element, element.state());
    }

    public static void buildBlocksModelsForElement(BlockStateModelGenerator generator, Element element) {
        element.state().buildBlockModel(generator);
        element.forEachSource(source -> {
            for (Block block : source.getBlocks()) {
                generator.registerSimpleCubeAll(block);
            }
        });
    }

    public static void buildItemModelsForElement(ItemModelGenerator itemModelGenerator, Element element) {
        for (Item item : element.getItems()) {
            itemModelGenerator.register(item, Models.GENERATED);
        }
    }

    public static void buildLootTablesForElement(BlockLootTableGenerator generator, Element element) {
        element.forEachSource(source -> source.buildLootTables(generator, element));
    }

    public static void buildBlockTagsForElement(Function<TagKey<Block>, FabricTagProvider<Block>.FabricTagBuilder> provider, Element element) {
        /*ElementRefinedState state = element.state();
        TagKey<Block> mineable = state.getType().blockMineableTag();
        if (mineable != null) {
            provider.apply(mineable).add(state.refinedBlock());
        }
        element.forEachSource(source -> source.buildAdditionalBlockTags(provider, element));*/
    }

    public static TextureMap getPowderTextureMap(Block powderBlock) {
        Identifier top = TextureMap.getSubId(powderBlock, "_top");
        return new TextureMap()
                .put(TextureKey.SIDE, TextureMap.getSubId(powderBlock, "_side"))
                .put(TextureKey.FRONT, TextureMap.getSubId(powderBlock, "_front"))
                .put(TextureKey.TOP, top)
                .put(TextureKey.PARTICLE, top)
                .put(TextureKey.BOTTOM, TextureMap.getSubId(ModBlocks.EMPTY_SACK, "_bottom"));
    }
}
