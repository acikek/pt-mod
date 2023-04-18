package com.acikek.pt.api.impl.datagen;

import com.acikek.pt.core.element.Element;
import com.acikek.pt.core.refined.ElementRefinedState;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.item.Item;

public class PTDatagenImpl {

    private static void buildTranslationsForState(FabricLanguageProvider.TranslationBuilder builder, Element element, ElementRefinedState state) {
        // TODO test
        if (state.hasRefinedFluid()) {
            builder.add(state.refinedFluid().getDefaultState().getBlockState().getBlock(), element.naming().englishName());
        }
        builder.add(state.refinedBlock(), element.getRefinedBlockName());
        builder.add(state.refinedItem(), element.getRefinedItemName());
        builder.add(state.miniRefinedItem(), element.getMiniRefinedItemName());
    }

    public static void buildTranslationsForElement(FabricLanguageProvider.TranslationBuilder builder, Element element) {
        builder.add(element.getNameKey(), element.naming().englishName());
        builder.add(element.getSymbolKey(), element.naming().symbol());
        element.forEachSource(source -> source.buildTranslations(builder, element));
        buildTranslationsForState(builder, element, element.state());
    }

    public static void generateBlocksModelsForElement(BlockStateModelGenerator generator, Element element) {
        element.state().buildBlockModel(generator);
        element.forEachSource(source -> {
            for (Block block : source.getBlocks()) {
                generator.registerSimpleCubeAll(block);
            }
        });
    }

    public static void generateItemModelsForElement(ItemModelGenerator itemModelGenerator, Element element) {
        for (Item item : element.getItems()) {
            itemModelGenerator.register(item, Models.GENERATED);
        }
    }
}
