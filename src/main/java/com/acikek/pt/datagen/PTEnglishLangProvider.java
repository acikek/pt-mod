package com.acikek.pt.datagen;

import com.acikek.pt.api.PTApi;
import com.acikek.pt.core.element.Element;
import com.acikek.pt.core.refined.ElementRefinedState;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class PTEnglishLangProvider extends FabricLanguageProvider {

    protected PTEnglishLangProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    private static void generateForState(TranslationBuilder builder, Element element, ElementRefinedState state) {
        // TODO test
        if (state.hasRefinedFluid()) {
            builder.add(state.refinedFluid().getDefaultState().getBlockState().getBlock(), element.naming().englishName());
        }
        builder.add(state.refinedBlock(), element.getRefinedBlockName());
        builder.add(state.refinedItem(), element.getRefinedItemName());
        builder.add(state.miniRefinedItem(), element.getMiniRefinedItemName());
    }

    public static void generateForElement(TranslationBuilder builder, Element element) {
        builder.add(element.getNameKey(), element.naming().englishName());
        builder.add(element.getSymbolKey(), element.naming().symbol());
        element.forEachSource(source -> source.buildTranslations(builder, element));
        generateForState(builder, element, element.state());
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        PTApi.forEachElement(element -> generateForElement(translationBuilder, element));
    }
}
