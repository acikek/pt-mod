package com.acikek.pt.datagen;

import com.acikek.pt.core.PeriodicTable;
import com.acikek.pt.core.element.Element;
import com.acikek.pt.core.refined.ElementRefinedState;
import com.acikek.pt.core.source.ElementSource;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class PTEnglishLangProvider extends FabricLanguageProvider {

    protected PTEnglishLangProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    private static void generateForSource(TranslationBuilder builder, Element element, ElementSource source) {
        if (source.hasSourceBlock()) {
            builder.add(source.sourceBlock(), element.getSourceBlockName());
        }
        if (source.hasDeepslateSourceBlock()) {
            builder.add(source.deepslateSourceBlock(), element.getDeepslateSourceBlockName());
        }
        if (source.hasClusterSourceBlock()) {
            builder.add(source.clusterSourceBlock(), element.getClusterSourceBlockName());
        }
        if (source.hasRawSourceItem()) {
            builder.add(source.rawSourceItem(), element.getRawSourceItemName());
        }
        if (source.hasRawSourceBlock()) {
            builder.add(source.rawSourceBlock(), element.getRawSourceBlockName());
        }
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
        if (element.hasSource()) {
            generateForSource(builder, element, element.source());
        }
        generateForState(builder, element, element.state());
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        for (Element element : PeriodicTable.INSTANCE.getElements()) {
            generateForElement(translationBuilder, element);
        }
    }
}
